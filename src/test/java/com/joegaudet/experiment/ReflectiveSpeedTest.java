package com.joegaudet.experiment;

import java.lang.reflect.InvocationTargetException;


public class ReflectiveSpeedTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		SimplePojo pojo = new SimplePojo();
		int samples = 1000000;
		long time = System.currentTimeMillis();
		Integer one = new Integer(1);
		for (int i = 0; i < samples; i++) {
			Integer a = pojo.getaField();
		}
		System.out.println("Average Execution of a straight getter: " + ((double) (System.currentTimeMillis() - time) / (double) samples));

		time = System.currentTimeMillis();
		for (int i = 0; i < samples; i++) {
			Integer a = pojo.get("aField", Integer.class);
		}
		System.out.println("Average Execution of a reflective get: " + ((double) (System.currentTimeMillis() - time) / (double) samples));
		
		time = System.currentTimeMillis();
		for (int i = 0; i < samples; i++) {
			Integer a = (Integer) SimplePojo.class.getMethod("get", String.class, Class.class).invoke(pojo, "aField", Integer.class);
		}
		System.out.println("Average Execution of a reflective invocation of get: " + ((double) (System.currentTimeMillis() - time) / (double) samples));
		
		time = System.currentTimeMillis();
		for (int i = 0; i < samples; i++) {
			Integer a = (Integer) SimplePojo.class.getMethod("getaField").invoke(pojo);
		}
		System.out.println("Average Execution reflective invocation of a method: " + ((double) (System.currentTimeMillis() - time) / (double) samples));
	}
}
