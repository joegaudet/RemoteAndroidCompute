package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.joegaudet.remote.compute.ArgumentArray;

import example.DoubleMatrix;


public class ArgumentListTest {

	@Test
	public void serializePrimativeArugmentList() throws Exception {
		Object[] array = {1, 1.0, true};
		ArgumentArray argArray = new ArgumentArray(array);
		ByteBuffer buffer = ByteBuffer.allocate(argArray.size());
		argArray.toBuffer(buffer);
		buffer.rewind();
		
		ArgumentArray argArray2 = new ArgumentArray(buffer);
		Object[] array2 = argArray2.getArray();
		
		for(int i = 0; i < array.length; i++){
			assertEquals(array[i], array2[i]);
		}
	}
	
	
	@Test
	public void serializeSimpleArgument() throws Exception {
		DoubleMatrix identity = DoubleMatrix.getIdentity(5);
		Object[] array = {identity};
		ArgumentArray argArray = new ArgumentArray(array);
		ByteBuffer buffer = ByteBuffer.allocate(argArray.size());
		argArray.toBuffer(buffer);
		buffer.rewind();
		
		ArgumentArray argArray2 = new ArgumentArray(buffer);
		Object[] array2 = argArray2.getArray();
		
		for(int i = 0; i < array.length; i++){
			assertEquals(array[i], array2[i]);
		}
	}
}
