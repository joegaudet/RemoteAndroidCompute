package com.joegaudet.remote;

import java.nio.ByteBuffer;

import com.joegaudet.remote.visitors.FieldVisitor;

public interface VisitableRemoteEntity {

	public int objectSize();

	public boolean isDirty();

	public void setDirty(boolean dirty);

	public void serialize(ByteBuffer buffer);

	public void deserialize(ByteBuffer buffer);

	public boolean isField();

	public boolean isPrimativeField();

	public boolean isObject();
	
	public int getValueHashCode();
	
	public <V extends FieldVisitor> V acceptObjectVisitor(V objectVisitor);
	
}
