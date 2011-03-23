package com.joegaudet.remote.fields.arrays2d;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.AbstractPrimativeField;

public class Double2DArrayField extends AbstractPrimativeField<double[][]> {

	public Double2DArrayField(RemoteObject parent) {
		super(parent);
		value = new double[0][0];
	}

	public double get(int i, int j){
		return value[i][j];
	}

	public void set(int i, int j, double value){
		this.value[i][j] = value;
	}
	
	@Override
	public int size() {
		return 8 + value.length * value[0].length * 8 + super.size();
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		int n = value.length;
		int m = value[0].length;
		
		buffer.putInt(n);
		buffer.putInt(m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				buffer.putDouble(value[i][j]);
			}
		}
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		int n = buffer.getInt();
		int m = buffer.getInt();
		double[][] newValue = new double[n][m];
		this.value = newValue;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				value[i][j] = buffer.getDouble();
			}
		}
	}

	public int getN() {
		return value.length;
	}

	public int getM() {
		return value[0].length;
	}

}
