package com.joegaudet.remote;

import java.nio.ByteBuffer;
import java.util.Set;

import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.fields.RemoteableField;

public interface RemoteObject {

	/**
	 * Constant size with respect to this object
	 * @return
	 */
	public int size();
	
	
	/**
	 * Set of the remoteable fields that are dirty on this object
	 * @return
	 */
	public Set<RemoteableField<?>> getDirtyFields();

	/**
	 * Set of the remoteable fields that are declared on this object
	 * @return
	 */
	public Set<RemoteableField<?>> getFields();
	
	public Set<ObjectField<?>> getObjectFields();

	/**
	 * Returns the field that's name has a matching hash code
	 * @param hashCode
	 * @return
	 */
	public RemoteableField<?> fieldForHashCode(int hashCode);
	
	/**
	 * Returns the hash code of the field name that this
	 * object uses
	 * @param remoteableField
	 * @return
	 */
	public int hashCodeForField(RemoteableField<?> remoteableField);

	/**
	 * If this object is dirty
	 * @return
	 */
	public boolean isDirty();

	/**
	 * Set if the object is dirty
	 * @param dirty
	 */
	public void setDirty(boolean dirty);

	/**
	 * Serialize this object to the buffer
	 * @param buffer
	 */
	public void serialize(ByteBuffer buffer);

	/**
	 * deserialize this object from the buffer
	 * @param buffer
	 */
	public void deserialize(ByteBuffer buffer);



}
