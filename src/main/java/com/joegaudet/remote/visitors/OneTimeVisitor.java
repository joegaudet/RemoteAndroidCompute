package com.joegaudet.remote.visitors;

import java.util.HashSet;
import java.util.Set;

import com.joegaudet.remote.fields.RemoteableField;

public abstract class OneTimeVisitor implements FieldVisitor{

	protected Set<Integer> visitedHashCodes;

	public OneTimeVisitor() {
		this.visitedHashCodes = new HashSet<Integer>();
	}
	
	@Override
	public void visit(RemoteableField<?> visitable) {
		if(visitable.isPrimativeField()){
			visitPrimative(visitable);
		}
		else {
			int hashCode = visitable.getValueHashCode();
			if(!visitedHashCodes.contains(hashCode)){
				visitNewObject(visitable);
				visitedHashCodes.add(hashCode);
			}
			else {
				visitOldObject(visitable);
			}
		}
	}
	
	@Override
	public boolean shouldVisitChildren(RemoteableField<?> field) {
		return !visitedHashCodes.contains(field.getValueHashCode());
	}

	protected abstract void visitPrimative(RemoteableField<?> visitable);
	protected abstract void visitNewObject(RemoteableField<?> visitable);
	protected abstract void visitOldObject(RemoteableField<?> visitable);
	

}
