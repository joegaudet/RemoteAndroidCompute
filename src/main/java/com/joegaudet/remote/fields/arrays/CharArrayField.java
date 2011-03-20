package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class CharArrayField extends AbstractPrimativeField<char[]>{


	public CharArrayField(RemoteObject parent) {
		super(parent);
		value = new char[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length * 2 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(char c : value)
			buffer.putChar(c);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		char[] newValue = new char[length];
		this.value = newValue;
		for(int i = 0; i < value.length; i++){
			this.value[i] = buffer.getChar();
		}
	}
	
}
