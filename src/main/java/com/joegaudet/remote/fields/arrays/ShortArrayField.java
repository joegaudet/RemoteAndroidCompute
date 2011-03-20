package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class ShortArrayField extends AbstractPrimativeField<short[]>{


	public ShortArrayField(RemoteObject parent) {
		super(parent);
		value = new short[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length * 2 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(Short item : value)
			buffer.putShort(item);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		short[] newValue = new short[length];
		this.value = newValue;
		for(Short i = 0; i < value.length; i++){
			this.value[i] = buffer.getShort();
		}
	}
	
}
