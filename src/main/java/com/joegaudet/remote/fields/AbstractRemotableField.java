package com.joegaudet.remote.fields;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;

public abstract class AbstractRemotableField<T> implements RemoteableField<T>{
	
	protected boolean dirty = false;
	protected final RemoteObject parent;
	
	public AbstractRemotableField(RemoteObject parent){
		this.parent = parent;
	}
	
	@Override
	public int size() {
		return 4; // my hashCode
	}
	
	@Override
	public boolean isDirty() {
		return dirty;
	}
	
	@Override
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	public void serialize(ByteBuffer buffer) {
		dirty = false;
		buffer.putInt(parent.hashCodeForField(this));
	}
	
	public void set(T value) {
		dirty = true;
	}

}
