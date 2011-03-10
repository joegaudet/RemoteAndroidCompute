package com.joegaudet.remote;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.joegaudet.list.FArrayList;
import com.joegaudet.list.Filter;
import com.joegaudet.remote.serialize.FieldSerializer;
import com.joegaudet.remote.serialize.ObjectLinkResolution;
import com.joegaudet.remote.serialize.ObjectResolutionException;
import com.joegaudet.remote.serialize.SerializerNotFoundException;

public class RemoteObjectSerializer {

	private static ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

	private static Set<Integer> storedHashCodes = new HashSet<Integer>();
	private static Set<Integer> sizedHashCodes = new HashSet<Integer>();
	private static Map<Integer, Object> readObjects = new HashMap<Integer, Object>();
	private static Set<ObjectLinkResolution> resolutions = new HashSet<ObjectLinkResolution>();

	public static void resetStoredHashCodes() {
		storedHashCodes = new HashSet<Integer>();
		readObjects = new HashMap<Integer, Object>();
		sizedHashCodes = new HashSet<Integer>();
	}

	public static void writeFullRemoteObject(RemoteObject remoteObject, WritableByteChannel channel) throws SerializerNotFoundException, IOException {
		ByteBuffer objectBuffer = objectToByteBuffer(remoteObject);
		if (objectBuffer != null)
			channel.write(objectBuffer);
	}

	private static Stack<RemoteObject> objects = new Stack<RemoteObject>();

	public static void pushObject(RemoteObject object) {
		objects.push(object);
	}

	public static ByteBuffer objectToByteBuffer(RemoteObject remoteObject) throws SerializerNotFoundException {
		storedHashCodes.add(remoteObject.hashCode());
		ByteBuffer objectBuffer = null;
		try {
			int payloadSize = computeSchemaSize(remoteObject);
			objectBuffer = ByteBuffer.allocate(payloadSize + 4); // plus size
																	// for the
																	// payload
			objectBuffer.putInt(payloadSize);
			serializeObject(remoteObject, objectBuffer, computeSchemaSizeNoRefs(remoteObject));

			// clean up any objects found traversing the graph
			// this should also recursively add more as we proceed
			while (objects.size() != 0) {
				RemoteObject nextObject = objects.pop();
				int hashCodes = nextObject.hashCode();
				if (!storedHashCodes.contains(hashCodes)) {
					storedHashCodes.add(hashCodes);
					serializeObject(nextObject, objectBuffer, computeSchemaSizeNoRefs(nextObject));
				}
			}

			objectBuffer.rewind();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return objectBuffer;
	}

	private static void serializeObject(RemoteObject remoteObject, ByteBuffer objectBuffer, int computeSchemaSize) throws IllegalAccessException, SerializerNotFoundException {
		objectBuffer.putInt(computeSchemaSize);
		String name = ((Class<? extends RemoteObject>) remoteObject.getClass()).getName();
		objectBuffer.putInt(remoteObject.hashCode());
		objectBuffer.putInt(name.length());
		objectBuffer.put(name.getBytes());

		for (Field field : ((Class<? extends RemoteObject>) remoteObject.getClass()).getDeclaredFields()) {
			field.setAccessible(true);
			Object object = field.get(remoteObject);
			if (object != null) {
				objectBuffer.putInt(field.getName().hashCode());
				FieldSerializer.serializeTo(object, objectBuffer);
			}
		}

		storedHashCodes.add(remoteObject.hashCode());
	}

	public static Object readRemoteObject(ReadableByteChannel channel) throws IOException, ClassNotFoundException, SerializerNotFoundException, ObjectResolutionException {
		lengthBuffer.rewind();
		channel.read(lengthBuffer);
		lengthBuffer.rewind();
		int payloadLength = lengthBuffer.getInt();

		ByteBuffer objectBuffer = ByteBuffer.allocate(payloadLength);
		channel.read(objectBuffer);
		objectBuffer.rewind();

		Object object = null;

		while (objectBuffer.hasRemaining()) {
			// get this object size
			int thisObjectSize = objectBuffer.getInt();

			int thisObjectHashCode = objectBuffer.getInt();
			// get this classname size
			int thisClassNameSize = objectBuffer.getInt();

			// adjust object size
			thisObjectSize -= (thisClassNameSize + 4 + 4 + 4);

			byte[] classNameBuffer = new byte[thisClassNameSize];
			objectBuffer.get(classNameBuffer);
			String string = new String(classNameBuffer);
			Class<?> klass = Class.forName(string);

			byte[] thisObjectArr = new byte[thisObjectSize];
			objectBuffer.get(thisObjectArr);
			ByteBuffer thisObjectBuffer = ByteBuffer.wrap(thisObjectArr);
			thisObjectBuffer.rewind();

			try {
				Object thisObject = klass.newInstance();
				while (thisObjectBuffer.hasRemaining()) {
					readField(thisObject, thisObjectBuffer);
				}
				// if we are on the first object
				if (object == null) {
					object = thisObject;
				}
				readObjects.put(thisObjectHashCode, thisObject);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		for (ObjectLinkResolution resolution : resolutions) {
			resolution.resolve(readObjects);
		}

		return object;
	}

	private static void readField(Object object, ByteBuffer objectBuffer) throws SerializerNotFoundException, IOException {
		Field field = getFieldByHashCode(objectBuffer.getInt(), object);
		if (field != null) {
			// System.out.println("Handing Field: " + field.getName());
			field.setAccessible(true);
			FieldSerializer.deserializeFieldTo(field, object, objectBuffer);
		}
	}

	protected static Field getFieldByHashCode(final int hashCode, Object object) {
		return new FArrayList<Field>(object.getClass().getDeclaredFields()).find(new Filter<Field>() {
			@Override
			public boolean apply(Field element) {
				return element.getName().hashCode() == hashCode;
			}
		});
	}

	// TODO: come up with a better implementation of this
	public static int computeSchemaSize(RemoteObject remoteObject) throws IllegalArgumentException, IllegalAccessException {
		return computeSchemaSize(remoteObject, true);
	}

	public static int computeSchemaSizeNoRefs(RemoteObject remoteObject) throws IllegalArgumentException, IllegalAccessException {
		return computeSchemaSize(remoteObject, false);
	}

	public static int computeSchemaSize(RemoteObject remoteObject, boolean includeReferences) throws IllegalArgumentException, IllegalAccessException {
		sizedHashCodes.add(remoteObject.hashCode());

		Class<? extends RemoteObject> remoteClass = remoteObject.getClass();
		int size = 12; // first string length + schema length + hashCode

		size += remoteClass.getName().getBytes().length;

		for (Field field : remoteClass.getDeclaredFields()) {
			Class<?> klass = field.getType();
			field.setAccessible(true);
			Object object = field.get(remoteObject);

			if (object != null) {
				size += 4; // HashCode for the field

				if (isAssignableFromAny(klass, Float.class, Integer.class, float.class, int.class)) {
					size += 4;
				}
				else if (isAssignableFromAny(klass, Double.class, Long.class, double.class, long.class)) {
					size += 8;
				}
				else if (isAssignableFromAny(klass, Byte.class, byte.class, Boolean.class, boolean.class)) {
					size += 1;
				}
				else if (isAssignableFromAny(klass, Character.class, char.class)) {
					size += 2;
				}
				else {

					// autoboxing array
					if (isAssignableFromAny(klass, Byte[].class, Boolean[].class)) {
						size += 4 + ((Object[]) object).length;
					}
					else if (isAssignableFromAny(klass, Character[].class)) {
						size += 4 + ((Object[]) object).length * 2;
					}
					else if (isAssignableFromAny(klass, Integer[].class, Float[].class)) {
						size += 4 + ((Object[]) object).length * 4;
					}
					else if (isAssignableFromAny(klass, Double[].class, Long[].class)) {
						size += 4 + ((Object[]) object).length * 8;
					}

					// primative arrays
					else if (isAssignableFromAny(klass, byte[].class)) {
						size += 4 + ((byte[]) object).length;
					}
					else if (isAssignableFromAny(klass, boolean[].class)) {
						size += 4 + ((boolean[]) object).length;
					}
					else if (isAssignableFromAny(klass, char[].class)) {
						size += 4 + ((char[]) object).length * 2;
					}
					else if (isAssignableFromAny(klass, int[].class)) {
						size += 4 + ((int[]) object).length * 4;
					}
					else if (isAssignableFromAny(klass, float[].class)) {
						size += 4 + ((float[]) object).length * 4;
					}
					else if (isAssignableFromAny(klass, long[].class)) {
						size += 4 + ((long[]) object).length * 8;
					}
					else if (isAssignableFromAny(klass, double[].class)) {
						size += 4 + ((double[]) object).length * 8;
					}

					// strings
					else if (isAssignableFromAny(klass, String.class)) {
						size += 4 + ((String) object).length();
					}

					// recurse
					else if (isAssignableFromAny(RemoteObject.class, klass)) {
						size += 4; // objectHashCode ref

						// if it is a circular ref, or we've already sized this
						// object
						if (includeReferences && !(object == remoteObject || sizedHashCodes.contains(object.hashCode()))) {
							// size of the ref + size of the object
							size += RemoteObjectSerializer.computeSchemaSize((RemoteObject) object);
						}
					}
				}
			}
		}
		return size;
	}

	private static boolean isAssignableFromAny(Class<?> klass, Class<?>... klasses) {
		boolean retval = false;
		for (Class<?> aKlass : klasses) {
			retval |= klass.isAssignableFrom(aKlass);
		}
		return retval;
	}

	public static void addObjectLinkResolution(ObjectLinkResolution objectLinkResolution) {
		resolutions.add(objectLinkResolution);
	}

}
