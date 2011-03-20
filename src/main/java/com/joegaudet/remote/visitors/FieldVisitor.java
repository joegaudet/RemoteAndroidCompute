package com.joegaudet.remote.visitors;

import com.joegaudet.remote.fields.RemoteableField;

public interface FieldVisitor {
	
	public void visit(RemoteableField<?> visitable);
	
	public boolean shouldVisitChildren(RemoteableField<?> field);
	
}
