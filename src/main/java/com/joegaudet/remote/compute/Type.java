package com.joegaudet.remote.compute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.serialize.RemoteObjectDeserializer;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;

public enum Type {


	
	BYTE, BOOLEAN, SHORT, CHAR, INTEGER, FLOAT, LONG, DOUBLE, REMOTE_OBJECT;

	private static Map<Class<?>, Type> typeMap = new HashMap<Class<?>, Type>();

	static {
		typeMap.put(Byte.class, Type.BYTE);
		typeMap.put(Boolean.class, Type.BOOLEAN);
		typeMap.put(Short.class, Type.SHORT);
		typeMap.put(Character.class, Type.CHAR);
		typeMap.put(Integer.class, Type.INTEGER);
		typeMap.put(Float.class, Type.FLOAT);
		typeMap.put(Long.class, Type.LONG);
		typeMap.put(Double.class, Type.DOUBLE);
	}
	
	public byte byteForType() {
		return (byte) this.ordinal();
	}

	public static Type typeOf(Object object){
		return typeForClass(object.getClass());
	}
	
	public static Type typeForClass(Class<? extends Object> klass) {
		Type type = typeMap.get(klass);
		if (type == null && RemoteObject.class.isAssignableFrom(klass)) {
			type = REMOTE_OBJECT;
		}
		return type;
	}

	public static Type getTypeForByte(byte b) {
		return values()[b];
	}
	
	public int sizeOf(Object object) {
		int retval = 1;
		switch (this) {
			case BYTE:
			case BOOLEAN:
				retval += 1;
				break;
			case SHORT:
			case CHAR:
				retval += 2;
				break;
			case FLOAT:
			case INTEGER:
				retval += 4;
				break;
			case DOUBLE:
			case LONG:
				retval += 8;
				break;
			case REMOTE_OBJECT:
				retval += 4 + new SchemaSizeVisitor((RemoteObject) object).computeSchemaSize();
				break;
		}
		return retval;
	}
	
	public void putObject(Object object, ByteBuffer buffer) {
		buffer.put(byteForType());

		switch (this) {
			case BYTE:
				buffer.put(((Byte) object).byteValue());
				break;
			case BOOLEAN:
				buffer.put((byte) (((Boolean) object).booleanValue() ? 1 : 0));
				break;
			case SHORT:
				buffer.putShort(((Short) object).shortValue());
				break;
			case CHAR:
				buffer.putChar(((Character) object).charValue());
				break;
			case FLOAT:
				buffer.putFloat(((Float) object).floatValue());
				break;
			case INTEGER:
				buffer.putInt(((Integer) object).intValue());
				break;
			case DOUBLE:
				buffer.putDouble(((Double) object).doubleValue());
				break;
			case LONG:
				buffer.putLong(((Long) object).longValue());
				break;
			case REMOTE_OBJECT:
				new RemoteObjectSerializer().serialize((RemoteObject) object, buffer);
				break;
		}
	}

	public Object getObject(ByteBuffer buffer) throws IOException, ClassNotFoundException {
		Object retval = null;
		switch (this) {
			case BYTE:
				retval = new Byte(buffer.get());
				break;
			case BOOLEAN:
				retval = new Boolean(buffer.get() == 1);
				break;
			case SHORT:
				retval = new Short(buffer.getShort());
				break;
			case CHAR:
				retval = new Character(buffer.getChar());
				break;
			case FLOAT:
				retval = new Float(buffer.getFloat());
				break;
			case INTEGER:
				retval = new Integer(buffer.getInt());
				break;
			case DOUBLE:
				retval = new Double(buffer.getDouble());
				break;
			case LONG:
				retval = new Long(buffer.getLong());
				break;
			case REMOTE_OBJECT:
				retval = new RemoteObjectDeserializer().readObjectFromByteBuffer(buffer);
				break;
		}
		return retval;
	}
	
}
