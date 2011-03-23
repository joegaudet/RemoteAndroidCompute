package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.junit.Before;
import org.junit.Test;

import com.joegaudet.remote.serialize.RemoteObjectDeserializer;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;
import com.joegaudet.remote.visitors.ObjectSizeVisitor;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;

public class RemoteObjectSerializationTest {

	private TestRemoteObject testRemoteObject;

	@Before
	public void createFixtures() throws Exception {
		testRemoteObject = new TestRemoteObject();
		assertInit();
	}

	@Test
	public void serializationDeserializationPrimatives() throws Exception {
		testRemoteObject.initializeRemoteObject();
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
		object.assertObjectEquals(testRemoteObject);
	}
	
	@Test
	public void serializationDeserialization_oneWayReferenceObject() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		testRemoteObjectWithRefrence.initializeRemoteObject();
		testRemoteObject.initializeRemoteObject();
		
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
		
		object.assertObjectEquals(testRemoteObjectWithRefrence);
		
		object.getRemoteObject().assertObjectEquals(testRemoteObject);
		
		assertNull(object.getRemoteObject2());
		
	}


	@Test
	public void serializationDeserialization_two_oneWayReferenceObject() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		testRemoteObjectWithRefrence.initializeRemoteObject();
		testRemoteObject.initializeRemoteObject();
		
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
		object.assertObjectEquals(testRemoteObjectWithRefrence);
		
		object.getRemoteObject().assertObjectEquals(testRemoteObject);
		
		object.getRemoteObject2().assertObjectEquals(testRemoteObject);
		
	}
	


	@Test
	public void serializationDeserialization_oneCircularRerence() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		testRemoteObjectWithRefrence.initializeRemoteObject();
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
		
		object.assertObjectEquals(testRemoteObjectWithRefrence);
		
		object.getRemoteObject().assertObjectEquals(testRemoteObjectWithRefrence);
		
		assertNull(object.getRemoteObject2());
	}
	
	@Test
	public void serializationDeserialization_longChainCircularRerence() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence2 = new TestRemoteObjectWithObjectReference();
		testRemoteObjectWithRefrence.initializeRemoteObject();
		testRemoteObjectWithRefrence2.initializeRemoteObject();
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
		object.assertObjectEquals(testRemoteObjectWithRefrence);
		
		object.getRemoteObject().assertObjectEquals(testRemoteObjectWithRefrence2);
		((TestRemoteObjectWithObjectReference)object.getRemoteObject()).getRemoteObject().assertObjectEquals(testRemoteObjectWithRefrence);
	}
	
	// -- Sizing tests

	@Test
	public void primativeSizes() throws Exception {
		int size = testRemoteObject.initializeRemoteObject();
		assertEquals("Sizes don't match", size, new SchemaSizeVisitor(testRemoteObject).computeSchemaSize());
	}

	@Test
	public void oneWayReferenceSize_butNoLinkedObject_returnsProperSize() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithRefrence = new TestRemoteObjectWithObjectReference();
		int expectedSize = testRemoteObjectWithRefrence.initializeRemoteObject();
		// given that the object field was not set, it clean
		// so it does not contribute to the size
		assertEquals("Sizes don't match", expectedSize, new SchemaSizeVisitor(testRemoteObjectWithRefrence).computeSchemaSize());
	}

	@Test
	public void oneWayReference_withLinkedObject_returnsProperSize() throws Exception {
		TestRemoteObjectWithObjectReference testRemoteObjectWithReference = new TestRemoteObjectWithObjectReference();
		
		int expectedSize = testRemoteObjectWithReference.initializeRemoteObject();
		int testRemoteObjectSize = testRemoteObject.initializeRemoteObject(); // size of the object

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
		int expectedSize = testRemoteObjectWithRefrence.initializeRemoteObject();

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

}
