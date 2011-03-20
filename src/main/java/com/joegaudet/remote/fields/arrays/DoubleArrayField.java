package com.joegaudet.remote.fields.arrays;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class DoubleArrayField extends AbstractPrimativeField<double[]>{


	public DoubleArrayField(RemoteObject parent) {
		super(parent);
		value = new double[0];
	}
	
	@Override
	public int size() {
		return 4 + value.length * 8 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(value.length);
		for(double item : value)
			buffer.putDouble(item);
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int length = buffer.getInt();
		double[] newValue = new double[length];
		this.value = newValue;
		for(int i = 0; i < value.length; i++){
			this.value[i] = buffer.getDouble();
		}
	}
	
}
