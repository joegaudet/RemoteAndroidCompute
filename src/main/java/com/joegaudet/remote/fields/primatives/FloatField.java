package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class FloatField extends AbstractPrimativeField<Float>{

	private float value;

	public FloatField(RemoteObject parent) {
		super(parent);
	}
	
	@Override
	public int size() {
		return 4 + super.size();
	}

	@Override
	public Float get() {
		return new Float(value);
	}

	@Override
	public void set(Float value) {
		super.set(value);
		this.value = value.floatValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putFloat(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getFloat();
	}
	
}
