package com.joegaudet.remote.fields;

import com.joegaudet.remote.RemoteObject;

public abstract class AbstractPrimativeField<T extends Object> extends AbstractRemotableField<T>{

	protected T value;
	
	public AbstractPrimativeField(RemoteObject parent) {
		super(parent);
	}
	
	@Override
	public T get() {
		return value;
	}

	@Override
	public void set(T value) {
		super.set(value);
		this.value = value;
	}

	@Override
	public int getValueHashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean isPrimativeField() {
		return true;
	}
	
	@Override
	public boolean isObjectField() {
		return false;
	}
	
	public <V extends com.joegaudet.remote.visitors.FieldVisitor> V acceptVisitor(V vistor) {
		vistor.visit(this);
		return vistor;
	}
	
}
