package com.joegaudet.experiment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class SimplePojo {

	private Map<Class<?>, Field[]> fieldsCache = new HashMap<Class<?>, Field[]>();
	private Map<String, Field> fields = new HashMap<String, Field>();

	private Integer aField = 0;
	private Integer aaField = 0;
	private Integer aaaField = 0;
	private Integer aaaaField = 0;
	private Integer aaaaaField = 0;

	public SimplePojo() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(String name, Class<T> expectedResult) {
		T retval = null;
		try {
			Class<? extends SimplePojo> class1 = this.getClass();
			Field[] declaredFields = fieldsCache.get(class1);

			if (declaredFields == null) {
				declaredFields = class1.getDeclaredFields();
				fieldsCache.put(class1, declaredFields);
			}

			Field thisField = fields.get(name);
			if (thisField == null) {
				for (Field field : declaredFields) {
					if (field.getName().equals(name))
						thisField = field;

				}
				if (thisField == null) {
					throw new RuntimeException("Field " + name + " is not a member of this class.");
				}
				else {
					this.fields.put(name, thisField);
				}
			}
			retval = (T) thisField.get(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return retval;
	}

	public void setaField(Integer aField) {
		this.aField = aField;
	}

	public Integer getaField() {
		return aField;
	}

}
