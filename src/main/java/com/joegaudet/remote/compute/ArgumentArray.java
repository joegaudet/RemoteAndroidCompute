package com.joegaudet.remote.compute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.serialize.RemoteObjectDeserializer;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;

public class ArgumentArray {

	private Object[] array;

	public ArgumentArray() {

	}

	public ArgumentArray(Object[] array) {
		this.array = array;
	}

	public ArgumentArray(ByteBuffer buffer) {
		toArray(buffer);
	}

	public int size() {
		int size = 4;

		for (Object object : getArray()) {
			size += sizeOf(Type.typeForClass(object.getClass()), object);
		}
		return size;
	}

	public void toBuffer(ByteBuffer buffer) {
		buffer.putInt(getArray().length);
		for (Object object : getArray()) {
			putObject(Type.typeForClass(object.getClass()), object, buffer);
		}
	}

	public void toArray(ByteBuffer buffer) {
		array = new Object[buffer.getInt()];
		for(int i = 0; i < array.length; i++){
			array[i] = getObject(buffer);
		}
	}

	private Object getObject(ByteBuffer buffer) {
		Object retval = null;
		switch (Type.getTypeForByte(buffer.get())) {
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
				try {
					retval = new RemoteObjectDeserializer().readObjectFromByteBuffer(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
		}
		
		return retval;
	}

	private void putObject(Type type, Object object, ByteBuffer buffer) {

		buffer.put(type.byteForType());

		switch (type) {
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
//				((RemoteObject) object).serialize(buffer);
				new RemoteObjectSerializer().serialize((RemoteObject) object, buffer);
				break;
		}
	}

	private int sizeOf(Type type, Object object) {
		int retval = 1;
		switch (type) {
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

	public void setArray(Object[] array) {
		this.array = array;
	}

	public Object[] getArray() {
		return array;
	}

}
