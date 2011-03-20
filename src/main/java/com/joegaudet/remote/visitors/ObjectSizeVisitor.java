package com.joegaudet.remote.visitors;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.RemoteableField;

public class ObjectSizeVisitor implements FieldVisitor {

	int size ;
	private RemoteObject object;
	
	public ObjectSizeVisitor(RemoteObject object){
		this.object = object;
	}
	
	public int computeObjectSize(){
		size = object.size();
		for(RemoteableField<?> field:object.getDirtyFields()){
			field.acceptVisitor(this);
		}
		return size;
	}

	@Override
	public void visit(RemoteableField<?> visitable) {
		if(visitable != object){
			if(visitable.isPrimativeField()){
				size += visitable.size();
			}
			else {
				size += 8;
			}
		}
	}

	@Override
	public boolean shouldVisitChildren(RemoteableField<?> field) {
		return false;
	}
}
