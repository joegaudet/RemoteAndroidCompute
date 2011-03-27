package com.joegaudet.remote.compute;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.joegaudet.remote.serialize.RemoteObjectDeserializer;

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
			size += Type.typeForClass(object.getClass()).sizeOf(object);
		}
		return size;
	}

	public void toBuffer(ByteBuffer buffer) {
		buffer.putInt(getArray().length);
		for (Object object : getArray()) {
			Type.typeOf(object).putObject(object, buffer);
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

	public void setArray(Object[] array) {
		this.array = array;
	}

	public Object[] getArray() {
		return array;
	}

}
