package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class BooleanArrayField extends AbstractPrimativeField<boolean[]>{


	public BooleanArrayField(RemoteObject parent) {
		super(parent);
		value = new boolean[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length + super.size();
	}


	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(int i = 0; i < value.length; i++){
			buffer.put((byte) (value[i] ? 1 : 0));
		}
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		byte[] temp = new byte[length];
		buffer.get(temp);
		boolean[] newValue = new boolean[length];
		this.value = newValue;
		for(int i = 0; i < temp.length; i++){
			newValue[i] = temp[i] == 1 ? true : false;
		}
	}
	
}
