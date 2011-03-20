package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class BooleanField extends AbstractPrimativeField<Boolean>{

	private boolean value = false;

	public BooleanField(RemoteObject parent) {
		super(parent);
	}
	
	@Override
	public int size() {
		return 1 + super.size();
	}

	@Override
	public Boolean get() {
		return new Boolean(value);
	}

	@Override
	public void set(Boolean value) {
		super.set(value);
		this.value = value.booleanValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.put((byte) (value ? 1 : 0));
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.get() == 1;
	}
	
}
