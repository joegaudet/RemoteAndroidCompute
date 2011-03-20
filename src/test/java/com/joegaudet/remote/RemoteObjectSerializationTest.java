package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.joegaudet.remote.serialize.RemoteObjectDeserializer;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;
import com.joegaudet.remote.visitors.ObjectSizeVisitor;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;
import com.joegaudet.util.TestHelper;

public class RemoteObjectSerializationTest {

	private TestRemoteObject testRemoteObject;

	@Before
	public void createFixtures() throws Exception {
		testRemoteObject = new TestRemoteObject();
		assertInit();
	}

	@Test
	public void serializationDeserializationPrimatives() throws Exception {
		initializeRemoteObject(testRemoteObject, 0);
		File file = new File("test.ser");
		file.deleteOnExit();
		RemoteObjectSerializer serializer = new RemoteObjectSerializer();

		FileChannel channel = new FileOutputStream(file).getChannel();
		int schemaSize = new SchemaSizeVisitor(testRemoteObject).computeSchemaSize();
		assertEquals(schemaSize, new ObjectSizeVisitor(testRemoteObject).computeObjectSize());
		
		serializer.writeObjectToChannel(testRemoteObject, channel);
		channel.close();
		
		assertEquals(file.length(), schemaSize + 4);

		FileChannel channel2 = new FileInputStream(file).getChannel();
		TestRemoteObject object = new RemoteObjectDeserializer().readObjectFromChannel(channel2, TestRemoteObject.class);
		assertNotNull(object);
		assertTestRemoteObjects(object, testRemoteObject);
	}
	
	@Test
	public void serializationDeserialization_oneWayReferenceObject() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		initializeRemoteObject(testRemoteObjectWithRefrence, 0);
		initializeRemoteObject(testRemoteObject, 0);
		
		testRemoteObjectWithRefrence.setRemoteObject(testRemoteObject);
//		testRemoteObjectWithRefrence.setRemoteObject2(testRemoteObject);
		
		File file = new File("test.ser");
//		file.deleteOnExit();
		RemoteObjectSerializer serializer = new RemoteObjectSerializer();
		
		FileChannel channel = new FileOutputStream(file).getChannel();
		int schemaSize = new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize();
		
		serializer.writeObjectToChannel(testRemoteObjectWithRefrence, channel);
		assertEquals(file.length(), schemaSize + 4);
		
		
		channel = new FileInputStream(file).getChannel();
		TestRemoteObjectWithObjectReference object = new RemoteObjectDeserializer().readObjectFromChannel(channel, TestRemoteObjectWithObjectReference.class);
		assertNotNull(object);
		assertTestRemoteObjects(object, testRemoteObjectWithRefrence);
		
		assertTestRemoteObjects(object.getRemoteObject(), testRemoteObject);
		
		assertNull(object.getRemoteObject2());
		
	}


	@Test
	public void serializationDeserialization_two_oneWayReferenceObject() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		initializeRemoteObject(testRemoteObjectWithRefrence, 0);
		initializeRemoteObject(testRemoteObject, 0);
		
		testRemoteObjectWithRefrence.setRemoteObject(testRemoteObject);
		testRemoteObjectWithRefrence.setRemoteObject2(testRemoteObject);
		
		File file = new File("test.ser");
//		file.deleteOnExit();
		RemoteObjectSerializer serializer = new RemoteObjectSerializer();
		
		FileChannel channel = new FileOutputStream(file).getChannel();
		int schemaSize = new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize();
		
		serializer.writeObjectToChannel(testRemoteObjectWithRefrence, channel);
		assertEquals(file.length(), schemaSize + 4);
		
		
		channel = new FileInputStream(file).getChannel();
		TestRemoteObjectWithObjectReference object = new RemoteObjectDeserializer().readObjectFromChannel(channel, TestRemoteObjectWithObjectReference.class);
		assertNotNull(object);
		assertTestRemoteObjects(object, testRemoteObjectWithRefrence);
		
		assertTestRemoteObjects(object.getRemoteObject(), testRemoteObject);
		
		assertTestRemoteObjects(object.getRemoteObject2(), testRemoteObject);
		
	}
	


	@Test
	public void serializationDeserialization_oneCircularRerence() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		initializeRemoteObject(testRemoteObjectWithRefrence, 0);
//		initializeRemoteObject(testRemoteObject, 0);
		
		testRemoteObjectWithRefrence.setRemoteObject(testRemoteObjectWithRefrence);
//		testRemoteObjectWithRefrence.setRemoteObject2(testRemoteObjectWithRefrence);
		
		File file = new File("test.ser");
//		file.deleteOnExit();
		RemoteObjectSerializer serializer = new RemoteObjectSerializer();
		
		FileChannel channel = new FileOutputStream(file).getChannel();
		int schemaSize = new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize();
		
		serializer.writeObjectToChannel(testRemoteObjectWithRefrence, channel);
		assertEquals(file.length(), schemaSize + 4);
		
		
		channel = new FileInputStream(file).getChannel();
		TestRemoteObjectWithObjectReference object = new RemoteObjectDeserializer().readObjectFromChannel(channel, TestRemoteObjectWithObjectReference.class);
		assertNotNull(object);
		assertTestRemoteObjects(object, testRemoteObjectWithRefrence);
		
		assertTestRemoteObjects(object.getRemoteObject(), testRemoteObjectWithRefrence);
		
		assertNull(object.getRemoteObject2());
	}
	
	@Test
	public void serializationDeserialization_longChainCircularRerence() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence2 = new TestRemoteObjectWithObjectReference();
		initializeRemoteObject(testRemoteObjectWithRefrence, 0);
		initializeRemoteObject(testRemoteObjectWithRefrence2, 0);
//		initializeRemoteObject(testRemoteObject, 0);
		
		testRemoteObjectWithRefrence.setRemoteObject(testRemoteObjectWithRefrence2);
		testRemoteObjectWithRefrence2.setRemoteObject(testRemoteObjectWithRefrence);
		
		File file = new File("test.ser");
//		file.deleteOnExit();
		RemoteObjectSerializer serializer = new RemoteObjectSerializer();
		
		FileChannel channel = new FileOutputStream(file).getChannel();
		int schemaSize = new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize();
		
		serializer.writeObjectToChannel(testRemoteObjectWithRefrence, channel);
		assertEquals(file.length(), schemaSize + 4);
		
		channel = new FileInputStream(file).getChannel();
		TestRemoteObjectWithObjectReference object = new RemoteObjectDeserializer().readObjectFromChannel(channel, TestRemoteObjectWithObjectReference.class);
		assertNotNull(object);
		assertTestRemoteObjects(object, testRemoteObjectWithRefrence);
		
		assertTestRemoteObjects(object.getRemoteObject(), testRemoteObjectWithRefrence2);
		assertTestRemoteObjects(((TestRemoteObjectWithObjectReference)object.getRemoteObject()).getRemoteObject(), testRemoteObjectWithRefrence);
	}
	
	// -- Sizing tests

	@Test
	public void primativeSizes() throws Exception {
		int size = initializeRemoteObject(testRemoteObject, 0);
		assertEquals("Sizes don't match", size, new SchemaSizeVisitor(testRemoteObject).computeSchemaSize());
	}

	@Test
	public void oneWayReferenceSize_butNoLinkedObject_returnsProperSize() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		int expectedSize = initializeRemoteObject(testRemoteObjectWithRefrence, 0);
		// given that the object field was not set, it clean
		// so it does not contribute to the size
		assertEquals("Sizes don't match", expectedSize, new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize());
	}

	@Test
	public void oneWayReference_withLinkedObject_returnsProperSize() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithReference = new TestRemoteObjectWithObjectReference();
		
		int expectedSize = initializeRemoteObject(testRemoteObjectWithReference, 0);
		int testRemoteObjectSize = initializeRemoteObject(testRemoteObject, 0); // size of the object

		assertEquals("Sizes don't match", expectedSize, new SchemaSizeVisitor(testRemoteObjectWithReference).computeSchemaSize());
		
		testRemoteObjectWithReference.setRemoteObject(testRemoteObject);
		
//		System.out.println("Expected Size: " + expectedSize);
//		System.out.println("Test Remote Size: " + testRemoteObjectSize);
//		System.out.println("Entry Point: " + testRemoteObjectWithReference.hashCode());
		
		assertEquals("Sizes don't match", expectedSize + testRemoteObjectSize + 8, new SchemaSizeVisitor(testRemoteObjectWithReference).computeSchemaSize());

		testRemoteObjectWithReference.setRemoteObject2(testRemoteObject);
		assertEquals("Sizes don't match", expectedSize + testRemoteObjectSize + 16, new SchemaSizeVisitor(testRemoteObjectWithReference).computeSchemaSize());
	}

	@Test
	public void circularReference_withLinkedObject_returnsProperSize() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		int expectedSize = initializeRemoteObject(testRemoteObjectWithRefrence, 0);

		// given that the object field was not set, it clean
		// so it does not contribute to the size
//		expectedSize += 8 + new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize();
		expectedSize += 8;
		testRemoteObjectWithRefrence.setRemoteObject(testRemoteObjectWithRefrence);

		// new size should be size + hashCode for the dirty field
		// with testRemoteObject
		assertEquals("Sizes don't match", expectedSize, new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize());

		// since we've already encountered this object, the size should only
		// go up by the hashcode for the field, and the hashcode for the object
		testRemoteObjectWithRefrence.setRemoteObject2(testRemoteObjectWithRefrence);
		expectedSize += 8;
		assertEquals("Sizes don't match", expectedSize, new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize());
	}

	private int initializeRemoteObject(TestRemoteObject remoteObject, int expectedSize) throws IllegalAccessException {

		expectedSize = header(remoteObject, expectedSize);

		remoteObject.setByteField(TestHelper.getAnonymousByte());
		expectedSize += 5;

		remoteObject.setBooleanField(TestHelper.getAnonymousBoolean());
		expectedSize += 5;

		remoteObject.setShortField(TestHelper.getAnonymousShort());
		expectedSize += 6;

		remoteObject.setCharField(TestHelper.getAnonymousCharacter());
		expectedSize += 6;

		remoteObject.setIntField(TestHelper.getAnonymousInt());
		expectedSize += 8;

		remoteObject.setFloatField(TestHelper.getAnonymousFloat());
		expectedSize += 8;

		remoteObject.setLongField(TestHelper.getAnonymousLong());
		expectedSize += 12;

		remoteObject.setDoubleField(TestHelper.getAnonymousDouble());
		expectedSize += 12;

		String string = TestHelper.getAnonymousString("Test String");
		remoteObject.setStringField(string);
		expectedSize += 8 + string.length();
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		
		// bytes
		byte[] byteArray = TestHelper.getAnonymousByteArray();
		remoteObject.setByteArrayField(byteArray);
		expectedSize += byteArray.length + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// booleans
		boolean[] booleanArray = TestHelper.getAnonymousBooleanArray();
		remoteObject.setBooleanArrayField(booleanArray);
		expectedSize += booleanArray.length + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// char arrays
		char[] charArray = TestHelper.getAnonymousCharArray();
		remoteObject.setCharArrayField(charArray);
		expectedSize += charArray.length * 2 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// short arrays
		short[] shortArray = TestHelper.getAnonymousShortArray();
		remoteObject.setShortArrayField(shortArray);
		expectedSize += shortArray.length * 2 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// int arrays
		int[] intArray = TestHelper.getAnonymousIntArray();
		remoteObject.setIntArrayField(intArray);
		expectedSize += intArray.length * 4 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// float arrays
		float[] floatArray = TestHelper.getAnonymousFloatArray();
		remoteObject.setFloatArrayField(floatArray);
		expectedSize += floatArray.length * 4 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// long arrays
		long[] longArray = TestHelper.getAnonymousLongArray();
		remoteObject.setLongArrayField(longArray);
		expectedSize += longArray.length * 8 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		// long arrays
		double[] doubleArray = TestHelper.getAnonymousDoubleArray();
		remoteObject.setDoubleArrayField(doubleArray);
		expectedSize += doubleArray.length * 8 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(remoteObject).computeObjectSize());

		return expectedSize;
	}

	private int header(TestRemoteObject testRemoteObject, int expectedSize) {
		expectedSize += 4; // object size
		expectedSize += 4; // class name size
		expectedSize += 4; // remote object hash code
		expectedSize += testRemoteObject.getClass().getName().length();
		return expectedSize;
	}

	/**
	 * Verifying that the fields are properly inited
	 */
	private void assertInit() {
		assertNotNull(testRemoteObject.getStringField());
		assertNotNull(testRemoteObject.getBooleanArrayField());
		assertNotNull(testRemoteObject.getBooleanField());
		assertNotNull(testRemoteObject.getByteArrayField());
		assertNotNull(testRemoteObject.getByteField());
		assertNotNull(testRemoteObject.getCharArrayField());
		assertNotNull(testRemoteObject.getCharField());
		assertNotNull(testRemoteObject.getDoubleArrayField());
		assertNotNull(testRemoteObject.getDoubleField());
		assertNotNull(testRemoteObject.getFloatArrayField());
		assertNotNull(testRemoteObject.getFloatField());
		assertNotNull(testRemoteObject.getIntArrayField());
		assertNotNull(testRemoteObject.getIntField());
		assertNotNull(testRemoteObject.getLongArrayField());
		assertNotNull(testRemoteObject.getLongField());
		assertNotNull(testRemoteObject.getShortArrayField());
		assertNotNull(testRemoteObject.getShortField());
	}

	
	private void assertTestRemoteObjects(TestRemoteObject object, TestRemoteObject object2) {
		// primatives fields
		assertEquals(object.getBooleanField(), object2.getBooleanField());
		assertEquals(object.getByteField(), object2.getByteField());
		assertEquals(object.getCharField(), object2.getCharField());
		assertEquals(object.getDoubleField(), object2.getDoubleField(), 0);
		assertEquals(object.getFloatField(), object2.getFloatField(), 0);
		assertEquals(object.getIntField(), object2.getIntField());
		assertEquals(object.getLongField(), object2.getLongField());
		assertEquals(object.getShortField(), object2.getShortField());
		assertEquals(object.getStringField(), object2.getStringField());

		// primative arrays

		assertTrue(Arrays.equals(object.getBooleanArrayField(), object2.getBooleanArrayField()));
		assertTrue(Arrays.equals(object.getByteArrayField(), object2.getByteArrayField()));
		assertTrue(Arrays.equals(object.getCharArrayField(), object2.getCharArrayField()));
		assertTrue(Arrays.equals(object.getDoubleArrayField(), object2.getDoubleArrayField()));
		assertTrue(Arrays.equals(object.getFloatArrayField(), object2.getFloatArrayField()));
		assertTrue(Arrays.equals(object.getIntArrayField(), object2.getIntArrayField()));
		assertTrue(Arrays.equals(object.getLongArrayField(), object2.getLongArrayField()));
		assertTrue(Arrays.equals(object.getShortArrayField(), object2.getShortArrayField()));
	}
}
