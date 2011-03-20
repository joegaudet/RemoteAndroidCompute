package com.joegaudet.remote.fields;

import java.nio.ByteBuffer;

import com.joegaudet.remote.visitors.FieldVisitor;

public interface RemoteableField<T> {
	
	public int size();
	
	/**
	 * Returns the value held by this field
	 * @return
	 */
	public T get();
	
	/**
	 * Sets the value on this field
	 * @param value
	 */
	public void set(T value);
	
	/**
	 * If this value has been changed since it was last
	 * touched
	 * @return
	 */
	public boolean isDirty();

	
	public void setDirty(boolean dirty);

	/**
	 * Serialize this field's contents to the byte buffer
	 * @param buffer
	 */
	public void serialize(ByteBuffer buffer);

	/**
	 * Deserialize this field from the contents of the byte buffer
	 * @param buffer
	 */
	public void deserialize(ByteBuffer buffer);

	/**
	 * Quacks like a duck
	 * @return
	 */
	public boolean isPrimativeField();

	/**
	 * Quacks like a duck, basically means we can
	 * safely cast it to a remote object field
	 * @return
	 */
	public boolean isObjectField();
	
	/**
	 * Returns the hash code of the value that this field
	 * contains.
	 * @return
	 */
	public int getValueHashCode();
	
	/**
	 * 
	 * @param <V>
	 * @param vistor
	 * @return
	 */
	public <V extends FieldVisitor> V acceptVisitor(V vistor);
}
