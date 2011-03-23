package com.joegaudet.remote.compute;

import java.util.HashMap;
import java.util.Map;

import com.joegaudet.remote.RemoteObject;

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
	
}
