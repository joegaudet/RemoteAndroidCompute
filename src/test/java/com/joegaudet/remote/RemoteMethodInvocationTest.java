package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.joegaudet.remote.compute.ArgumentArray;
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
		
		Double result = (Double) rmi.invoke();
		
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
		ByteBuffer buffer = rmi.serialize();
		
		buffer.rewind();
		RemoteMethodInvocation rmi2 = new RemoteMethodInvocation();
		buffer.getInt();
		
		rmi2.deserialize(buffer);
		Double result = (Double) rmi2.invoke();
		
		assertEquals(result, new Double(remoteObject2.getDoubleField() * remoteObjectWithMethod.getDoubleField()));
		
	}
	
}
