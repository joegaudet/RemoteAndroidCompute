package com.joegaudet.remote.fields;

import java.nio.ByteBuffer;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.visitors.FieldVisitor;

public class ObjectField<T extends RemoteObject> extends AbstractRemotableField<T> {

	private T value;
	private int tempHashCode;

	public ObjectField(RemoteObject parent) {
		super(parent);
	}

	@Override
	public boolean isDirty() {
		// set has been called, or the value it self has changed state
		return super.isDirty() || (value != null && value.isDirty());
	}

	@Override
	public int size() {
		return super.size() + 4;
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
	public void serialize(ByteBuffer buffer) {
		super.serialize(buffer);
		buffer.putInt(getValueHashCode());
	}

	@Override
	public void deserialize(ByteBuffer buffer) {
		setDirty(true);
		setTempHashCode(buffer.getInt());
	}

	@SuppressWarnings("unchecked")
	public void setWithCast(Object readObject) {
		this.set((T) readObject);
	}

	public T getValue() {
		return value;
	}

	@Override
	public int getValueHashCode() {
		return value == null ? -1 : value.hashCode();
	}

	@Override
	public boolean isPrimativeField() {
		return false;
	}


	@Override
	public boolean isObjectField() {
		return true;
	}

	@Override
	public <V extends FieldVisitor> V acceptVisitor(V vistor) {
		if(vistor.shouldVisitChildren(this) && value != null){
			for(RemoteableField<?> field: value.getFields()){
				field.acceptVisitor(vistor);
			}
		}
		vistor.visit(this);
		return vistor;
	}

	public void setTempHashCode(int tempHashCode) {
		this.tempHashCode = tempHashCode;
	}

	public int getTempHashCode() {
		return tempHashCode;
	}

}
