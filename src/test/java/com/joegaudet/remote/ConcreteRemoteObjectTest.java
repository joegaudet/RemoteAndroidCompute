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

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		RemoteObjectSerializer.resetStoredHashCodes();
		new File("test.obj").delete();
	}
	
	public void testComputeSchema() throws Exception {
		int expectedSize = 60 + 14 * 4 + (8 + TestRemoteObject.class.getName().length());
		assertEquals(expectedSize,testRemoteObject.computeSchemaSize());
		populateArraysObject(expectedSize);
	}
	
	public void testSerializeDeSerializeEquality() throws Exception {
		int expectedSize = 60 + 14 * 4 + (8 + TestRemoteObject.class.getName().length());
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
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
		int expectedSize = 60 + 14 * 4 + (8 + TestRemoteObject.class.getName().length());
		TestRemoteObject testRemoteObject2 = new TestRemoteObject(new Random());
		System.out.println("TestOne: " + testRemoteObject.hashCode() + " " + testRemoteObject.computeSchemaSizeNoRefs());
		System.out.println("TestTwo: " + testRemoteObject2.hashCode()); 
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize = testRemoteObject.computeSchemaSize() + 8 + testRemoteObject2.computeSchemaSize();
		
		testRemoteObject.setAnotherObject(testRemoteObject2);
		RemoteObjectSerializer.resetStoredHashCodes();
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		RemoteObjectSerializer.resetStoredHashCodes();

//		populateArraysObject(expectedSize);
		
		FileChannel channel = new FileOutputStream(new File("test.obj")).getChannel();
		RemoteObjectSerializer.writeFullRemoteObject(testRemoteObject, channel);
		channel.close();
		
		channel = new FileInputStream(new File("test.obj")).getChannel();
		TestRemoteObject readRemoteObject = (TestRemoteObject) RemoteObjectSerializer.readRemoteObject(channel);
		channel.close();
		
		assertTrue(testRemoteObject.equals(readRemoteObject));
		System.out.println(readRemoteObject.getAnotherObject());
		assertTrue(testRemoteObject.getAnotherObject().equals(readRemoteObject.getAnotherObject()));
	}
	
	public void testSerializeDeSerializeEquality_circularGuard() throws Exception {
		System.out.println("THIS TEST");
		int expectedSize = 60 + 14 * 4 + (8 + TestRemoteObject.class.getName().length());
		testRemoteObject.setAnotherObject(testRemoteObject);
		expectedSize += 8; // add the size of the new object
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		populateArraysObject(expectedSize );
		
		
		FileChannel channel = new FileOutputStream(new File("test.obj")).getChannel();
		RemoteObjectSerializer.writeFullRemoteObject(testRemoteObject, channel);
		channel.close();
		
		channel = new FileInputStream(new File("test.obj")).getChannel();
		TestRemoteObject readRemoteObject = (TestRemoteObject) RemoteObjectSerializer.readRemoteObject(channel);
		channel.close();
		
//		assertTrue(testRemoteObject.equals(readRemoteObject));
		
		assert(testRemoteObject == testRemoteObject.getAnotherObject());
		assert(readRemoteObject == readRemoteObject.getAnotherObject());
		System.out.println("END THIS TEST");
		
	}

	private void populateArraysObject(int expectedSize) throws IllegalAccessException {
		// btyes
		byte[] byteArray = TestHelper.getAnonymousByteArray();
		testRemoteObject.setByteArray(byteArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += byteArray.length + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Byte[] byteArray2 = TestHelper.getAnonymousByteObjectArray();
		RemoteObjectSerializer.resetStoredHashCodes();
		testRemoteObject.setByteArray2(byteArray2);
		expectedSize += byteArray2.length + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// booleans
		boolean[] booleanArray = TestHelper.getAnonymousBooleanArray();
		RemoteObjectSerializer.resetStoredHashCodes();
		testRemoteObject.setBoolArray(booleanArray);
		expectedSize += booleanArray.length * 1 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());

		Boolean[] booleanArray2 = TestHelper.getAnonymousBooleanObjectArray();
		RemoteObjectSerializer.resetStoredHashCodes();
		testRemoteObject.setBoolArray2(booleanArray2);
		expectedSize += booleanArray2.length * 1 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// char arrays
		char[] charArray = TestHelper.getAnonymousCharArray();
		testRemoteObject.setCharArray(charArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += charArray.length * 2 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Character[] characterArray = TestHelper.getAnonymousCharacterArray();
		testRemoteObject.setCharacterArray(characterArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += characterArray.length * 2 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// int arrays
		int[] intArray = TestHelper.getAnonymousIntArray();
		testRemoteObject.setIntArray(intArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += intArray.length * 4 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Integer[] integerArray = TestHelper.getAnonymousIntegerArray();
		testRemoteObject.setIntegerArray2(integerArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += integerArray.length * 4 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// float arrays
		float[] floatArray = TestHelper.getAnonymousFloatArray();
		testRemoteObject.setFloatArray(floatArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += floatArray.length * 4 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Float[] floatArray2 = TestHelper.getAnonymousFloatObjectArray();
		testRemoteObject.setFloatArray2(floatArray2);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += floatArray2.length * 4 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// long arrays
		long[] longArray = TestHelper.getAnonymousLongArray();
		testRemoteObject.setLongArray(longArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += longArray.length * 8 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Long[] longArray2 = TestHelper.getAnonymousLongObjectArray();
		testRemoteObject.setLongArray2(longArray2);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += longArray2.length * 8 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		// long arrays
		double[] doubleArray = TestHelper.getAnonymousDoubleArray();
		testRemoteObject.setDoubleArray(doubleArray);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += doubleArray.length * 8 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());
		
		Double[] doubleArray2 = TestHelper.getAnonymousDoubleObjectArray();
		testRemoteObject.setDoubleArray2(doubleArray2);
		RemoteObjectSerializer.resetStoredHashCodes();
		expectedSize += doubleArray2.length * 8 + 8;
		assertEquals(expectedSize, testRemoteObject.computeSchemaSize());

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
