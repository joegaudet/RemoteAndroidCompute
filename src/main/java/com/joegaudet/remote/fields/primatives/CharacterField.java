package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class CharacterField extends AbstractPrimativeField<Character>{

	private char value;

	public CharacterField(RemoteObject parent) {
		super(parent);
	}

	
	@Override
	public int size() {
		return 2 + super.size();
	}

	@Override
	public Character get() {
		return new Character(value);
	}

	@Override
	public void set(Character value) {
		super.set(value);
		this.value = value.charValue();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putChar(value);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		value = buffer.getChar();
	}
	
}
