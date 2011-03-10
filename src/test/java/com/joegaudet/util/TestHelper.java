package com.joegaudet.util;

import java.util.Random;

public class TestHelper {

	private static Random rand = new Random();
	
	public static byte[] getAnonymousByteArray() {
		byte[] retval = new byte[rand.nextInt(100)];
		rand.nextBytes(retval);
		return retval;
	}

	public static Byte[] getAnonymousByteObjectArray() {
		byte[] anonymousByteArray = getAnonymousByteArray();
		Byte[] retval = new Byte[anonymousByteArray.length];
		for(int i = 0; i < anonymousByteArray.length; i++){
			retval[i] = anonymousByteArray[i];
		}
		return retval;
	}

	public static Character[] getAnonymousCharacterArray() {
		byte[] anonymousByteArray = getAnonymousByteArray();
		Character[] retval = new Character[anonymousByteArray.length];
		for(int i = 0; i < anonymousByteArray.length; i++){
			retval[i] = (char) anonymousByteArray[i];
		}
		return retval;
	}

	public static char[] getAnonymousCharArray() {
		byte[] anonymousByteArray = getAnonymousByteArray();
		char[] retval = new char[anonymousByteArray.length];
		for(int i = 0; i < anonymousByteArray.length; i++){
			retval[i] = (char) anonymousByteArray[i];
		}
		return retval;
	}

	public static int[] getAnonymousIntArray() {
		int[] retval = new int[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextInt();
		}
		return retval;
	}
	
	public static Integer[] getAnonymousIntegerArray() {
		Integer[] retval = new Integer[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextInt();
		}
		return retval;
	}

	public static float[] getAnonymousFloatArray() {
		float[] retval = new float[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextFloat();
		}
		return retval;
	}
	
	public static Float[] getAnonymousFloatObjectArray() {
		Float[] retval = new Float[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextFloat();
		}
		return retval;
	}

	public static long[] getAnonymousLongArray() {
		long[] retval = new long[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextLong();
		}
		return retval;
	}
	
	public static Long[] getAnonymousLongObjectArray() {
		Long[] retval = new Long[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextLong();
		}
		return retval;
	}

	public static double[] getAnonymousDoubleArray() {
		double[] retval = new double[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextDouble();
		}
		return retval;
	}
	
	public static Double[] getAnonymousDoubleObjectArray() {
		Double[] retval = new Double[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextDouble();
		}
		return retval;
	}

	public static boolean[] getAnonymousBooleanArray() {
		boolean[] retval = new boolean[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextBoolean();
		}
		return retval;
	}
	
	public static Boolean[] getAnonymousBooleanObjectArray() {
		Boolean[] retval = new Boolean[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = rand.nextBoolean();
		}
		return retval;
	}

}
