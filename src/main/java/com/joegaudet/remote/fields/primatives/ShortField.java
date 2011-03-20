package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class ShortField extends AbstractPrimativeField<Short> {

	private short value;

	public ShortField(RemoteObject parent) {
		super(parent);
	}

	@Override
	public int size() {
		return 2 + super.size();
	}

	@Override
	public Short get() {
		return new Short(value);
	}

	@Override
	public void set(Short value) {
		super.set(value);
		this.value = value.shortValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putShort(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getShort();
	}

}
