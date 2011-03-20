package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class ByteField extends AbstractPrimativeField<Byte>{

	private byte value;

	public ByteField(RemoteObject parent) {
		super(parent);
	}
	
	@Override
	public int size() {
		return 1 + super.size();
	}

	@Override
	public Byte get() {
		return new Byte(value);
	}

	@Override
	public void set(Byte value) {
		super.set(value);
		this.value = value.byteValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.put(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.get();
	}
	
}
