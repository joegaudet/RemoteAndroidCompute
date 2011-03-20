package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class LongArrayField extends AbstractPrimativeField<long[]>{


	public LongArrayField(RemoteObject parent) {
		super(parent);
		value = new long[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length * 8 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(long item : value)
			buffer.putLong(item);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		long[] newValue = new long[length];
		this.value = newValue;
		for(int i = 0; i < value.length; i++){
			this.value[i] = buffer.getLong();
		}
	}
	
}
