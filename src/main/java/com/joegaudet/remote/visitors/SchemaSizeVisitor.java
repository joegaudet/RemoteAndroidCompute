package com.joegaudet.remote.visitors;

import java.util.Set;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.fields.RemoteableField;

public class SchemaSizeVisitor extends OneTimeVisitor {

	int size = 0;
	private final RemoteObject object;

	public SchemaSizeVisitor(RemoteObject object) {
		this.object = object;
	}

	public SchemaSizeVisitor(RemoteObject object, Set<Integer> visitedHashCodes) {
		this.object = object;
		this.visitedHashCodes = visitedHashCodes;
	}

	public int computeSchemaSize() {
		size += object.size();
		visitedHashCodes.add(object.hashCode());

		for (RemoteableField<?> field : object.getFields()) {
			field.acceptVisitor(this);
		}

		return size;
	}

	@Override
	protected void visitPrimative(RemoteableField<?> visitable) {
		if (visitable.isDirty()) {
			size += visitable.size();
		}
	}

	@Override
	protected void visitNewObject(RemoteableField<?> visitable) {
		if (visitable.isDirty()) {
			size += 8;
			ObjectField<?> objectField = (ObjectField<?>) visitable;
			RemoteObject remoteObject = objectField.get();
			size += remoteObject.size();
		}
	}

	@Override
	protected void visitOldObject(RemoteableField<?> visitable) {
		if (visitable.isDirty()) {
			size += 8;
		}
	}

	@Override
	public boolean shouldVisitChildren(RemoteableField<?> field) {
		return !visitedHashCodes.contains(field.getValueHashCode());
	}

}
