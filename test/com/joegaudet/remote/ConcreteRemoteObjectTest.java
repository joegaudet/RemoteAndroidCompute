package com.joegaudet.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Random;

import junit.framework.TestCase;

import com.joegaudet.util.TestHelper;

public class ConcreteRemoteObjectTest extends TestCase {

	private TestRemoteObject testRemoteObject;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testRemoteObject = new TestRemoteObject(new Random());
	}

	public void testComputeSchema() throws Exception {
		int expectedSize = 60 + 14 * 4 + (4 + TestRemoteObject.class.getName().length());
		assertEquals(expectedSize,testRemoteObject.computeSchemaSize());
		populateArraysObject(expectedSize);
	}
	
	public void testSerializeDeSerializeEquality() throws Exception {
		int expectedSize = 60 + 14 * 4 + (4 + TestRemoteObject.class.getName().length());
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		populateArraysObject(expectedSize);
		
		FileChannel channel = new FileOutputStream(new File("test.obj")).getChannel();
		RemoteObjectSerializer.writeFullRemoteObject(testRemoteObject, channel);
		channel.close();
		
		channel = new FileInputStream(new File("test.obj")).getChannel();
		TestRemoteObject readRemoteObject = (TestRemoteObject) RemoteObjectSerializer.readRemoteObject(channel);
		channel.close();
		
		assertTrue(testRemoteObject.equals(readRemoteObject));
	}
	
	public void testSerializeDeSerializeEquality_recursiveObjects() throws Exception {
		int expectedSize = 60 + 14 * 4 + (4 + TestRemoteObject.class.getName().length());
		TestRemoteObject testRemoteObject2 = new TestRemoteObject(new Random());
		testRemoteObject.setAnotherObject(testRemoteObject2);
		assertEquals(testRemoteObject.computeSchemaSize(), expectedSize * 2 + 8);
		populateArraysObject(expectedSize * 2 + 8);

		
		FileChannel channel = new FileOutputStream(new File("test.obj")).getChannel();
		RemoteObjectSerializer.writeFullRemoteObject(testRemoteObject, channel);
		channel.close();
		
		channel = new FileInputStream(new File("test.obj")).getChannel();
		TestRemoteObject readRemoteObject = (TestRemoteObject) RemoteObjectSerializer.readRemoteObject(channel);
		channel.close();
		
		assertTrue(testRemoteObject.equals(readRemoteObject));
	}

	private void populateArraysObject(int expectedSize) throws IllegalAccessException {
		// btyes
		byte[] byteArray = TestHelper.getAnonymousByteArray();
		testRemoteObject.setByteArray(byteArray);
		expectedSize += byteArray.length + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Byte[] byteArray2 = TestHelper.getAnonymousByteObjectArray();
		testRemoteObject.setByteArray2(byteArray2);
		expectedSize += byteArray2.length + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// booleans
		boolean[] booleanArray = TestHelper.getAnonymousBooleanArray();
		testRemoteObject.setBoolArray(booleanArray);
		expectedSize += booleanArray.length * 1 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);

		Boolean[] booleanArray2 = TestHelper.getAnonymousBooleanObjectArray();
		testRemoteObject.setBoolArray2(booleanArray2);
		expectedSize += booleanArray2.length * 1 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// char arrays
		char[] charArray = TestHelper.getAnonymousCharArray();
		testRemoteObject.setCharArray(charArray);
		expectedSize += charArray.length * 2 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Character[] characterArray = TestHelper.getAnonymousCharacterArray();
		testRemoteObject.setCharacterArray(characterArray);
		expectedSize += characterArray.length * 2 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// int arrays
		int[] intArray = TestHelper.getAnonymousIntArray();
		testRemoteObject.setIntArray(intArray);
		expectedSize += intArray.length * 4 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Integer[] integerArray = TestHelper.getAnonymousIntegerArray();
		testRemoteObject.setIntegerArray2(integerArray);
		expectedSize += integerArray.length * 4 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// float arrays
		float[] floatArray = TestHelper.getAnonymousFloatArray();
		testRemoteObject.setFloatArray(floatArray);
		expectedSize += floatArray.length * 4 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Float[] floatArray2 = TestHelper.getAnonymousFloatObjectArray();
		testRemoteObject.setFloatArray2(floatArray2);
		expectedSize += floatArray2.length * 4 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// long arrays
		long[] longArray = TestHelper.getAnonymousLongArray();
		testRemoteObject.setLongArray(longArray);
		expectedSize += longArray.length * 8 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Long[] longArray2 = TestHelper.getAnonymousLongObjectArray();
		testRemoteObject.setLongArray2(longArray2);
		expectedSize += longArray2.length * 8 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		// long arrays
		double[] doubleArray = TestHelper.getAnonymousDoubleArray();
		testRemoteObject.setDoubleArray(doubleArray);
		expectedSize += doubleArray.length * 8 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);
		
		Double[] doubleArray2 = TestHelper.getAnonymousDoubleObjectArray();
		testRemoteObject.setDoubleArray2(doubleArray2);
		expectedSize += doubleArray2.length * 8 + 8;
		assertEquals(testRemoteObject.computeSchemaSize(),expectedSize);

	}
	
	public void testEquals() throws Exception {
		populateArrays(testRemoteObject);
		assertTrue(testRemoteObject.equals(testRemoteObject));
		TestRemoteObject obj = new TestRemoteObject(new Random());
		populateArrays(obj);
		assertFalse(testRemoteObject.equals(obj));
	}

	private void populateArrays(TestRemoteObject object) {
		object.setByteArray(TestHelper.getAnonymousByteArray());
		object.setByteArray2(TestHelper.getAnonymousByteObjectArray());
		
		object.setCharArray(TestHelper.getAnonymousCharArray());
		object.setCharacterArray(TestHelper.getAnonymousCharacterArray());
		
		object.setIntArray(TestHelper.getAnonymousIntArray());
		object.setIntegerArray2(TestHelper.getAnonymousIntegerArray());
		
		object.setFloatArray(TestHelper.getAnonymousFloatArray());
		object.setFloatArray2(TestHelper.getAnonymousFloatObjectArray());
		
		object.setLongArray(TestHelper.getAnonymousLongArray());
		object.setLongArray2(TestHelper.getAnonymousLongObjectArray());
	}

}
