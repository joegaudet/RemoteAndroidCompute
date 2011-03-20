package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class DoubleField extends AbstractPrimativeField<Double>{

	private double value;

	public DoubleField(RemoteObject parent) {
		super(parent);
	}

	
	@Override
	public int size() {
		return 8 + super.size();
	}

	@Override
	public Double get() {
		return new Double(value);
	}

	@Override
	public void set(Double value) {
		super.set(value);
		this.value = value.doubleValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putDouble(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getDouble();
	}
	
}
