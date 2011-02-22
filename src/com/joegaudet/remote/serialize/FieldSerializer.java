package com.joegaudet.remote.serialize;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

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
		
		serializers.put(Boolean[].class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.put((byte) (((Boolean)object).booleanValue() ? 1 : 0));
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
				Byte[] byteArray = (Byte[]) object;
				objectBuffer.putInt(byteArray.length);
				for(int i = 0; i < byteArray.length; i++){
					objectBuffer.put(byteArray[i].byteValue());
				}
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				Byte[] arr = new Byte[objectBuffer.getInt()];
				for(int i = 0; i < arr.length; i++){
					arr[i] = new Byte(objectBuffer.get());
				}
			}
		});
		
		serializers.put(Character.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putChar(((Character)object).charValue());
			}

			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, (char) objectBuffer.get());
			}
		});
		
		serializers.put(char.class, new FieldSerializer() {
			@Override
			protected void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer) {
				objectBuffer.putChar(((Character)object).charValue());
			}
			
			@Override
			protected void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException {
				field.set(object, (char) objectBuffer.get());
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
		FieldSerializer fieldSerializer = serializers.get(klass);
		if(fieldSerializer != null){
			fieldSerializer.serializeObjectToBuffer(object, objectBuffer);
		}
		else {
			throw new SerializerNotFoundException();
		}
	}
	
	public static void deserializeFieldTo(Field field, Object object, ByteBuffer objectBuffer) throws SerializerNotFoundException {
		FieldSerializer fieldSerializer = serializers.get(field.getType());
		if(fieldSerializer != null){
			objectBuffer.putInt(field.getName().hashCode());
			try {
				field.setAccessible(true);
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

	protected abstract void deSerializeObjectFrom(Field field, Object object, ByteBuffer objectBuffer) throws IllegalAccessException, IllegalArgumentException;
	
	protected abstract void serializeObjectToBuffer(Object object, ByteBuffer objectBuffer);

}
