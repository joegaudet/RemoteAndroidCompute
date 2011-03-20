package com.joegaudet.remote;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.joegaudet.list.FArrayList;
import com.joegaudet.list.Filter;
import com.joegaudet.remote.fields.ObjectField;
import com.joegaudet.remote.fields.RemoteableField;
import com.joegaudet.remote.visitors.ObjectSizeVisitor;
import com.joegaudet.util.Log;

public abstract class AbstractRemoteObject implements RemoteObject {

	private static Map<Class<?>, Field[]> declaredFields = new HashMap<Class<?>, Field[]>();

	public AbstractRemoteObject() {
		initFields();
	}

	private void initFields() {
		initializeRemotableFields(this);
	}

	@Override
	public int size() {
		return 12 + this.getClass().getName().length();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void setDirty(boolean dirty) {

	}

	@Override
	public void serialize(ByteBuffer buffer) {
		String name = this.getClass().getName();

		// size - hashCode
		int size = new ObjectSizeVisitor(this).computeObjectSize();
		buffer.putInt(size);
		buffer.putInt(this.hashCode());

		// name
		buffer.putInt(name.getBytes().length);
		buffer.put(name.getBytes());
	}

	@Override
	public void deserialize(ByteBuffer buffer) {

	}

	@Override
	public int hashCodeForField(RemoteableField<?> remoteableField) {
		return getHashCodeForField(remoteableField, this);
	}

	@Override
	public Set<RemoteableField<?>> getFields() {
		return getRemotableFieldObjects(this);
	}
	
	@Override
	public Set<RemoteableField<?>> getDirtyFields() {
		return getDirtyRemotableFieldObjects(this);
	}

	@Override
	public Set<ObjectField<?>> getObjectFields() {
		return getRemotableObjectFieldObjects(this);
	}

	@Override
	public RemoteableField<?> fieldForHashCode(final int hashCode) {
		return getFieldForHashCode(this, hashCode);
	}


	// --- static delegate methods

	public static Set<RemoteableField<?>> getRemotableFieldObjects(RemoteObject object) {
		HashSet<RemoteableField<?>> retval = new HashSet<RemoteableField<?>>();
		for(Field field:getDeclaredRemotableFields(object)){
			try {
				retval.add((RemoteableField<?>) field.get(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retval;
	}

	public static Set<RemoteableField<?>> getDirtyRemotableFieldObjects(RemoteObject object) {
		HashSet<RemoteableField<?>> retval = new HashSet<RemoteableField<?>>();
		for(Field field:getDeclaredRemotableFields(object)){
			try {
				RemoteableField<?> remoteableField = (RemoteableField<?>) field.get(object);
				if(remoteableField.isDirty()){
					retval.add(remoteableField);
				}
 			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retval;
	}

	public static Set<ObjectField<?>> getRemotableObjectFieldObjects(RemoteObject object) {
		HashSet<ObjectField<?>> retval = new HashSet<ObjectField<?>>();
		for(Field field:getDeclaredRemotableFields(object)){
			try {
				RemoteableField<?> remoteableField = (RemoteableField<?>) field.get(object);
				if(remoteableField.isObjectField()){
					retval.add((ObjectField<?>) remoteableField);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retval;
	}
	
	public static Field[] getDeclaredRemotableFields(RemoteObject object) {
		Class<?> klass = object.getClass();
		Field[] fields = declaredFields.get(klass);
		if (fields == null) {
			ArrayList<Field> fieldsList = new ArrayList<Field>();
			while (klass != Object.class) {
				fields = klass.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if (RemoteableField.class.isAssignableFrom(field.getType())) {
						fieldsList.add(field);
					}
				}
				klass = klass.getSuperclass();
			}
			fields = fieldsList.toArray(new Field[fieldsList.size()]);
			declaredFields.put(klass, fields);
		}
		return fields;
	}

	public static RemoteableField<?> getFieldForHashCode(RemoteObject object, final int hashCode) {
		RemoteableField<?> retval = null;
		FArrayList<Field> fArrayList = new FArrayList<Field>(getDeclaredRemotableFields(object));
		Field field = fArrayList.find(new Filter<Field>() {
			@Override
			public boolean apply(Field element) {
				return element.getName().hashCode() == hashCode;
			}
		});
		
		try {
			retval = (RemoteableField<?>) field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retval;
	}

	public static int getHashCodeForField(RemoteableField<?> remoteableField, RemoteObject remoteObject) {
		int hashCode = -1;
		for (Field field : getDeclaredRemotableFields(remoteObject)) {
			try {
				if (field.get(remoteObject) == remoteableField) {
					hashCode = field.getName().hashCode();
					break;
				}
			} catch (Exception e) {
				Log.error(e);
			}
		}
		return hashCode;
	}
	
	public void initializeRemotableFields(RemoteObject remoteObject) {
		for (Field field : getDeclaredRemotableFields(remoteObject)) {
			Object object;
			try {
				object = field.getType().getConstructor(RemoteObject.class).newInstance(remoteObject);
				field.set(remoteObject, object);
			} catch (Exception e) {
				e.printStackTrace();
				Log.error(e);
			}
		}
	}

}
