package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class IntegerField extends AbstractPrimativeField<Integer>{

	private int value;

	public IntegerField(RemoteObject parent) {
		super(parent);
	}

	@Override
	public int size() {
		return 4 + super.size();
	}

	@Override
	public Integer get() {
		return new Integer(value);
	}

	@Override
	public void set(Integer value) {
		super.set(value);
		this.value = value.intValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getInt();
	}
	
}
