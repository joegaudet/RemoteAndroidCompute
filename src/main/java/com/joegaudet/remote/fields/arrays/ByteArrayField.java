package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class ByteArrayField extends AbstractPrimativeField<byte[]>{


	public ByteArrayField(RemoteObject parent) {
		super(parent);
		value = new byte[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length + super.size();
	}
	
	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		buffer.put(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		this.value = new byte[length];
		buffer.get(value);
	}
	
}
