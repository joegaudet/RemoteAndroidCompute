package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class LongField extends AbstractPrimativeField<Long> {

	private long value;

	public LongField(RemoteObject parent) {
		super(parent);
	}

	@Override
	public int size() {
		return 8 + super.size();
	}

	@Override
	public Long get() {
		return new Long(value);
	}

	@Override
	public void set(Long value) {
		super.set(value);
		this.value = value.longValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putLong(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getLong();
	}

}
