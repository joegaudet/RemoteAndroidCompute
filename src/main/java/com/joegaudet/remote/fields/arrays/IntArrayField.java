package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class IntArrayField extends AbstractPrimativeField<int[]>{


	public IntArrayField(RemoteObject parent) {
		super(parent);
		value = new int[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length * 4 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(int item : value)
			buffer.putInt(item);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		int[] newValue = new int[length];
		this.value = newValue;
		for(int i = 0; i < value.length; i++){
			this.value[i] = buffer.getInt();
		}
	}
	
}
