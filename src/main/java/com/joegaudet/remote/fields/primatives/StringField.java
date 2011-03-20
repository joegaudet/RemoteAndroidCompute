package com.joegaudet.remote.fields.primatives;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class StringField extends AbstractPrimativeField<String> {

	private String value = "";

	public StringField(RemoteObject parent) {
		super(parent);
	}

	@Override
	public int size() {
		return 4 + value.getBytes().length + super.size();
	}

	@Override
	public String get() {
		return value;
	}

	@Override
	public void set(String value) {
		super.set(value);
		this.value = value;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length());
		buffer.put(value.getBytes());
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		byte[] stringBuffer = new byte[length];
		buffer.get(stringBuffer);
		value = new String(stringBuffer);
	}

}
