package com.joegaudet.remote;



public class TestRemoteObjectWithMethod extends TestRemoteObject {
	
	public double doubleProduct(TestRemoteObject object){
		return this.getDoubleField() * object.getDoubleField();
	}
	
}

