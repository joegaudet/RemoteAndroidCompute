package com.joegaudet.remote.visitors;

import java.util.HashSet;
import java.util.Set;

import com.joegaudet.remote.VisitableRemoteEntity;
import com.joegaudet.remote.fields.RemoteableField;

public class DirtyCheckVisistor extends OneTimeVisitor {
	
	private boolean dirty = true;
	private Set<Integer> visited;
	
	public DirtyCheckVisistor(VisitableRemoteEntity visitable){
		visited = new HashSet<Integer>();
		visited.add(visitable.getValueHashCode());
	}
	
	public boolean isDirty(){
		return dirty;
	}

	@Override
	protected void visitPrimative(RemoteableField<?> visitable) {
		dirty |= visitable.isDirty();
	}

	@Override
	protected void visitNewObject(RemoteableField<?> visitable) {
		dirty |= visitable.isDirty();
	}

	@Override
	protected void visitOldObject(RemoteableField<?> visitable) {
		
	}

}
