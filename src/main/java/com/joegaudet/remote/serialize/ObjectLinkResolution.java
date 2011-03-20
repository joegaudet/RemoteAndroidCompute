package com.joegaudet.remote.serialize;

import java.util.Map;

import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.serialize.exceptions.ObjectResolutionException;
import com.joegaudet.util.Log;

public class ObjectLinkResolution {

	private int hashCode;
	private ObjectField<?> objectField;

	public ObjectLinkResolution(ObjectField<?> objectField, int hashCode) {
		this.objectField = objectField;
		this.hashCode = hashCode;

	}

	public void resolve(Map<Integer, Object> readObjects) throws ObjectResolutionException {
		Object readObject = readObjects.get(hashCode);
		if (readObject != null) {
			objectField.setWithCast(readObject);
		}
		else {
			Log.error("Could not find the hashcode: " + hashCode);
			throw new ObjectResolutionException("Could not find the object that was referenced by the stored hashcode.");
		}
	}

}
