package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.joegaudet.remote.compute.RemoteMethodInvocation;


public class RemoteMethodInvocationTest {

	@Test
	public void testInvokeDoesInvoke() throws Exception {
		TestRemoteObjectWithMethod remoteObjectWithMethod = new TestRemoteObjectWithMethod();
		TestRemoteObject remoteObject2 = new TestRemoteObject();
		remoteObjectWithMethod.initializeRemoteObject();
		remoteObject2.initializeRemoteObject();
		
		Method method = TestRemoteObjectWithMethod.class.getMethod("doubleProduct", TestRemoteObject.class);
		
		RemoteMethodInvocation rmi = new RemoteMethodInvocation(method, remoteObjectWithMethod, remoteObject2);
		
		Double result = rmi.invoke().get(Double.class);
		
		assertEquals(result, new Double(remoteObject2.getDoubleField() * remoteObjectWithMethod.getDoubleField()));
		
	}
	

	@Test
	public void testSerializeDeserializeInvokeDoesInvoke() throws Exception {
		TestRemoteObjectWithMethod remoteObjectWithMethod = new TestRemoteObjectWithMethod();
		TestRemoteObject remoteObject2 = new TestRemoteObject();
		remoteObjectWithMethod.initializeRemoteObject();
		remoteObject2.initializeRemoteObject();
		
		Method method = TestRemoteObjectWithMethod.class.getMethod("doubleProduct", TestRemoteObject.class);
		
		RemoteMethodInvocation rmi = new RemoteMethodInvocation(method, remoteObjectWithMethod, remoteObject2);
		ByteBuffer buffer = rmi.toBuffer();
		buffer.rewind();
		buffer.getInt(); // assume size has already been read

		RemoteMethodInvocation rmi2 = new RemoteMethodInvocation();
		
		rmi2.fromBuffer(buffer);
		Double result = rmi2.invoke().get(Double.class);
		
		assertEquals(result, new Double(remoteObject2.getDoubleField() * remoteObjectWithMethod.getDoubleField()));
		
	}
	
}
