package com.joegaudet.remote;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import com.joegaudet.list.FArrayList;
import com.joegaudet.list.Filter;
import com.joegaudet.remote.serialize.FieldSerializer;
import com.joegaudet.remote.serialize.SerializerNotFoundException;

public class RemoteObjectSerializer {

	private static ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

	public static void writeFullRemoteObject(RemoteObject remoteObject, WritableByteChannel channel) throws SerializerNotFoundException, IOException {
		Class<? extends RemoteObject> remoteClass = remoteObject.getClass();
		Field[] declaredFields = remoteClass.getDeclaredFields();
		try {
			int computeSchemaSize = computeSchemaSize(remoteObject);

			ByteBuffer objectBuffer = ByteBuffer.allocate(computeSchemaSize);

			objectBuffer.putInt(computeSchemaSize);
			String name = remoteClass.getName();
			objectBuffer.putInt(name.length());
			objectBuffer.put(name.getBytes());
			
			for (Field field : declaredFields) {
				field.setAccessible(true);
				Object object = field.get(remoteObject);
				if (object != null) {
					objectBuffer.putInt(field.getName().hashCode());
					FieldSerializer.serializeTo(object, objectBuffer);
				}
			}
			objectBuffer.rewind();
			channel.write(objectBuffer);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object readRemoteObject(ReadableByteChannel channel) throws IOException, ClassNotFoundException, SerializerNotFoundException {
		lengthBuffer.rewind();
		channel.read(lengthBuffer);
		lengthBuffer.rewind();
		int length = lengthBuffer.getInt();

		ByteBuffer objectBuffer = ByteBuffer.allocate(length - 4);
		channel.read(objectBuffer);
		objectBuffer.rewind();

		byte[] classNameBuffer = new byte[objectBuffer.getInt()];
		objectBuffer.get(classNameBuffer);
		String className = new String(classNameBuffer);
		
		Class<?> klass = Class.forName(className);
		Object object = null;
		try {
			object = klass.newInstance();
			while (objectBuffer.hasRemaining()) {
				readField(object, objectBuffer);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}

	private static void readField(Object object, ByteBuffer objectBuffer) throws SerializerNotFoundException {
		Field field = getFieldByHashCode(objectBuffer.getInt(), object);
		if (field != null) {
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
		Class<? extends RemoteObject> remoteClass = remoteObject.getClass();
		int size = 8; // first string length + schema length
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
					else if(isAssignableFromAny(klass, Character[].class)){
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
					else if (isAssignableFromAny(klass, RemoteObject.class)) {
						size += 4 + RemoteObjectSerializer.computeSchemaSize((RemoteObject) object);
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

}
