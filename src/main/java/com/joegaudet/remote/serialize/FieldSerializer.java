package com.joegaudet.remote.serialize;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.RemoteObjectSerializer;

public abstract class FieldSerializer {

	private static Map<Class<?>, FieldSerializer> serializers;

	static{
		serializers = new HashMap<Class<?>, FieldSerializer>();
		
		serializers.put(Boolean.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.put((byte) (((Boolean)object).booleanValue() ? 1 : 0));
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Boolean(objectBuffer.get() == 1));
			}

		});
		
		serializers.put(boolean.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.put((byte) (((Boolean)object).booleanValue() ? 1 : 0));
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Boolean(objectBuffer.get() == 1));
			}
			
		});
		
		serializers.put(Boolean[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Boolean[] arr = (Boolean[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.put((byte) (arr[i].booleanValue() ? 1 : 0));
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Boolean[] arr = new Boolean[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Boolean(objectBuffer.get() == 1);
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(boolean[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				boolean[] arr = (boolean[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.put((byte) (arr[i] ? 1 : 0));
				}
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				boolean[] arr = new boolean[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.get() == 1;
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Byte.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.put(((Byte)object).byteValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Byte(objectBuffer.get()));
			}

		});
		
		serializers.put(byte.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.put(((Byte)object).byteValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Byte(objectBuffer.get()));
			}
			
		});
		
		serializers.put(byte[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				byte[] arr = (byte[])object;
				objectBuffer.putInt(arr.length);
				objectBuffer.put(arr);
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				byte[] arr = new byte[objectBuffer.getInt()];
				objectBuffer.get(arr);
				field.set(object, arr);
			}
		});
		
		serializers.put(Byte[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Byte[] arr = (Byte[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.put(arr[i].byteValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Byte[] arr = new Byte[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Byte(objectBuffer.get());
				}
				field.set(object,arr);
			}
		});
		
		serializers.put(Character.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putChar(((Character)object).charValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Character(objectBuffer.getChar()));
			}
		});
		
		serializers.put(char.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putChar(((Character)object).charValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Character(objectBuffer.getChar()));
			}
		});
		
		serializers.put(Character[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Character[] arr = (Character[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putChar(arr[i].charValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Character[] arr = new Character[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Character(objectBuffer.getChar());
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(char[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				char[] arr = (char[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putChar(arr[i]);
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				char[] arr = new char[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.getChar();
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Integer.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putInt(((Integer)object).intValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Integer(objectBuffer.getInt()));
			}
		});
		
		serializers.put(int.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putInt(((Integer)object).intValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Integer(objectBuffer.getInt()));
			}
		});
		
		serializers.put(Integer[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Integer[] arr = (Integer[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putInt(arr[i].intValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Integer[] arr = new Integer[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Integer(objectBuffer.getInt());
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(int[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				int[] arr = (int[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putInt(arr[i]);
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				int[] arr = new int[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.getInt();
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Float.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putFloat(((Float)object).floatValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Float(objectBuffer.getFloat()));
			}
		});
		
		serializers.put(float.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putFloat(((Float)object).floatValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Float(objectBuffer.getFloat()));
			}
		});
		
		serializers.put(Float[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Float[] arr = (Float[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putFloat(arr[i].floatValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Float[] arr = new Float[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Float(objectBuffer.getFloat());
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(float[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				float[] arr = (float[]) object;
				objectBuffer.putInt(arr.length);
				for(int i = 0; i < arr.length; i++){
					objectBuffer.putFloat(arr[i]);
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				float[] arr = new float[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.getFloat();
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Long.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putLong(((Long)object).longValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, objectBuffer.getLong());
			}
		});
		
		serializers.put(long.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putLong(((Long)object).longValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, objectBuffer.getLong());
			}
		});
		
		serializers.put(long[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				long[] longArray = (long[]) object;
				objectBuffer.putInt(longArray.length);
				for(int i = 0; i < longArray.length; i++){
					objectBuffer.putLong(longArray[i]);
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				long[] arr = new long[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.getLong();
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Long[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Long[] longArray = (Long[]) object;
				objectBuffer.putInt(longArray.length);
				for(int i = 0; i < longArray.length; i++){
					objectBuffer.putLong(longArray[i].longValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Long[] arr = new Long[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Long(objectBuffer.getLong());
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Double.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putDouble(((Double)object).doubleValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Double(objectBuffer.getDouble()));
			}
		});
			
		serializers.put(double.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putDouble(((Double)object).doubleValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, new Double(objectBuffer.getDouble()));
			}
		});
		
		serializers.put(double[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				double[] doubleArray = (double[]) object;
				objectBuffer.putInt(doubleArray.length);
				for(int i = 0; i < doubleArray.length; i++){
					objectBuffer.putDouble(doubleArray[i]);
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				double[] arr = new double[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = objectBuffer.getDouble();
				}
				field.set(object, arr);
			}
		});
		
		serializers.put(Double[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				Double[] doubleArray = (Double[]) object;
				objectBuffer.putInt(doubleArray.length);
				for(int i = 0; i < doubleArray.length; i++){
					objectBuffer.putDouble(doubleArray[i].doubleValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalArgumentException, IllegalAccessException {
				Double[] arr = new Double[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Double(objectBuffer.getDouble());
				}
				field.set(object, arr);				
			}
		});
	}

	public static void serializeTo(Object object, ByteBuffer objectBuffer) throws SerializerNotFoundException{
		Class<? extends Object> klass = object.getClass();
		if(RemoteObject.class.isAssignableFrom(klass)){
			objectBuffer.putInt(object.hashCode());
			RemoteObjectSerializer.pushObject((RemoteObject) object);
		}
		else {
			FieldSerializer fieldSerializer = serializers.get(klass);
			if(fieldSerializer != null){
				fieldSerializer.serializeObjectToBuffer(object, objectBuffer);
			}
			else {
				throw new SerializerNotFoundException();
			}
		}
	}
	
	public static void deserializeFieldTo(Field field, Object object, ByteBuffer objectBuffer) throws SerializerNotFoundException, IOException {
		Class<?> type = field.getType();
		
		// if it's an object we need to check if we've already 
		// found it
		if(RemoteObject.class.isAssignableFrom(type)){
			System.out.println("Remote Object: ignoring till later");
			int hashCode = objectBuffer.getInt();
			RemoteObjectSerializer.addObjectLinkResolution(new ObjectLinkResolution(object, field, hashCode));
			System.out.println("ObjectBuffer: " + objectBuffer.remaining());
		}
		else {
			FieldSerializer fieldSerializer = serializers.get(type);
			if(fieldSerializer != null){
				try {
					fieldSerializer.deSerializeObjectFrom(field, object, objectBuffer);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			else {
				throw new SerializerNotFoundException();
			}
		}
	}

	protected abstract void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException;
	
	protected abstract void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer);

}
