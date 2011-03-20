package com.joegaudet.remote;

import com.joegaudet.remote.fields.ObjectField;

public class TestRemoteObjectWithObjectReference extends TestRemoteObject {

	private ObjectField<TestRemoteObject> remoteObjectField;
	private ObjectField<TestRemoteObject> remoteObjectField2;
	
	public TestRemoteObject getRemoteObject(){
		return remoteObjectField.get();
	}
	
	public void setRemoteObject(TestRemoteObject testRemoteObject){
		remoteObjectField.set(testRemoteObject);
	}

	public TestRemoteObject getRemoteObject2(){
		return remoteObjectField2.get();
	}
	
	public void setRemoteObject2(TestRemoteObject testRemoteObject){
		remoteObjectField2.set(testRemoteObject);
	}
	
}
