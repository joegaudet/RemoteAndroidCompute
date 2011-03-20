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
	
	public static short[] getAnonymousShortArray() {
		short[] retval = new short[rand.nextInt(100)];
		for(int i = 0; i < retval.length; i++){
			retval[i] = (short) rand.nextInt();
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

	public static byte getAnonymousByte() {
		return (byte) (rand.nextInt() & 0xff);
	}

	public static boolean getAnonymousBoolean() {
		return rand.nextBoolean();
	}

	public static short getAnonymousShort() {
		return (short) rand.nextInt();
	}

	public static char getAnonymousCharacter() {
		return (char) rand.nextInt();
	}

	public static int getAnonymousInt() {
		return rand.nextInt();
	}

	public static float getAnonymousFloat() {
		return rand.nextFloat();
	}

	public static long getAnonymousLong() {
		return rand.nextLong();
	}

	public static double getAnonymousDouble() {
		return rand.nextDouble();
	}

	public static String getAnonymousString(String string) {
		return string + " " + rand.nextInt();
	}

}
