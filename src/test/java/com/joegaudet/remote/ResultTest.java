package com.joegaudet.remote;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.joegaudet.remote.compute.Result;

public class ResultTest {

	@Test
	public void testSerializeDeserializePrimative() throws IllegalAccessException, IOException, ClassNotFoundException{
		
		Result result = new Result(2.0);
		ByteBuffer buffer = result.toBuffer();
		
		// buffer size
		buffer.getInt();
		
		Result result2 = new Result(buffer);
		assertEquals(new Double(2.0), result2.getResult());
	}
	
	@Test
	public void testSerializeDeserializeRemote() throws IllegalAccessException, IOException, ClassNotFoundException{
		TestRemoteObject testRemoteObject = new TestRemoteObject();
//		testRemoteObject.initializeRemoteObject();
		
		Result result = new Result(testRemoteObject);
		ByteBuffer buffer = result.toBuffer();
		
		// buffer size
		buffer.getInt();
		
		Result result2 = new Result(buffer);
		testRemoteObject.assertObjectEquals(result2.get(TestRemoteObject.class));
	}
	
}
