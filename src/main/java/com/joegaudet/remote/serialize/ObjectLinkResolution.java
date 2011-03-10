package com.joegaudet.remote.serialize;

import java.lang.reflect.Field;
import java.util.Map;

public class ObjectLinkResolution {

	private int hashCode;
	private Object object;
	private Field field;

	public ObjectLinkResolution(Object object, Field field, int hashCode) {
		this.object = object;
		this.field = field;
		this.hashCode = hashCode;

	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void resolve(Map<Integer, Object> readObjects) throws ObjectResolutionException {
		Object readObject = readObjects.get(hashCode);
		if(readObject != null){
			try {
				field.set(object, readObject);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new ObjectResolutionException("Could not find the object that was referenced by the stored hashcode.");
		}
	}

}
