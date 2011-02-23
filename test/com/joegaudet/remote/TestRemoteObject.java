package com.joegaudet.remote;

import java.util.Arrays;
import java.util.Random;

public class TestRemoteObject implements RemoteObject {

	private char charField;
	private Character characterField;

	private byte byteField;
	private Byte byteField2;

	private boolean booleanField;
	private Boolean booleanField2;

	private int intField;
	private Integer integerField;

	private float floatField;
	private Float floatField2;

	private long longField;
	private Long longField2;

	private double doubleField;
	private Double doubleField2;

	private boolean[] boolArray;
	private Boolean[] boolArray2;
	
	private byte[] byteArray;
	private Byte[] byteArray2;

	private char[] charArray;
	private Character[] characterArray;

	private int[] intArray;
	private Integer[] integerArray2;

	private float[] floatArray;
	private Float[] floatArray2;

	private long[] longArray;
	private Long[] longArray2;

	private double[] doubleArray;
	private Double[] doubleArray2;

	private TestRemoteObject anotherObject;
	
	public TestRemoteObject() {

	}

	public TestRemoteObject(Random rand) {
		charField = (char) rand.nextInt(255);
		characterField = (char) rand.nextInt(255);

		byteField = (byte) rand.nextInt(255);
		byteField2 = (byte) rand.nextInt(255);

		booleanField = rand.nextBoolean();
		booleanField2 = rand.nextBoolean();

		intField = rand.nextInt(255);
		integerField = rand.nextInt(255);

		floatField = rand.nextFloat();
		floatField2 = rand.nextFloat();

		longField = rand.nextLong();
		longField2 = rand.nextLong();

		doubleField = rand.nextDouble();
		doubleField2 = rand.nextDouble();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof TestRemoteObject))
			return false;

		TestRemoteObject other = (TestRemoteObject) obj;
		boolean retval = true;

		retval &= byteField == other.getByteField();
		retval &= byteField2.equals(other.getByteField2());
		check(retval, "Byte Fields");

		retval &= booleanField == other.isBooleanField();
		retval &= booleanField2.equals(other.getBooleanField2());
		check(retval, "Boolean Fields");

		retval &= charField == other.getCharField();
		retval &= characterField.equals(other.getCharacterField());
		check(retval, "Char Fields");

		retval &= intField == other.getIntField();
		retval &= integerField.equals(other.getIntegerField());
		check(retval, "Int Fields");

		retval &= floatField == other.getFloatField();
		retval &= floatField2.equals(other.getFloatField2());
		check(retval, "Float Fields");

		retval &= longField == other.getLongField();
		retval &= longField2.equals(other.getLongField2());
		check(retval, "Long Fields");

		retval &= Arrays.equals(byteArray, other.getByteArray());
		retval &= Arrays.equals(byteArray2, other.getByteArray2());
		check(retval, "ByteArr Fields");

		retval &= Arrays.equals(charArray, other.getCharArray());
		retval &= Arrays.equals(characterArray, other.getCharacterArray());
		check(retval, "CharArr Fields");

		retval &= Arrays.equals(intArray, other.getIntArray());
		retval &= Arrays.equals(integerArray2, other.getIntegerArray2());
		check(retval, "IntArr Fields");

		retval &= Arrays.equals(floatArray, other.getFloatArray());
		retval &= Arrays.equals(floatArray2, other.getFloatArray2());
		check(retval, "FloatArr Fields");

		retval &= Arrays.equals(longArray, other.getLongArray());
		retval &= Arrays.equals(longArray2, other.getLongArray2());
		check(retval, "LongArr Fields");

		retval &= Arrays.equals(doubleArray, other.getDoubleArray());
		retval &= Arrays.equals(doubleArray2, other.getDoubleArray2());
		check(retval, "DoubleArr Fields");

		retval &= Arrays.equals(doubleArray, other.getDoubleArray());
		retval &= Arrays.equals(doubleArray2, other.getDoubleArray2());
		check(retval, "BooleanArrs Fields");

		retval &= !(anotherObject == null ^ other.getAnotherObject() == null); // both must be true or false
		if(anotherObject != null && other.getAnotherObject() != null){
			retval &= anotherObject.equals(other.getAnotherObject());
		}
		return retval;
	}

	private void check(boolean retval, String string) {
		if (retval) {
//			System.out.println(string + " are equal");
		}
	}

	public int computeSchemaSize() throws IllegalArgumentException, IllegalAccessException {
		return RemoteObjectSerializer.computeSchemaSize(this);
	}

	public char getCharField() {
		return charField;
	}

	public void setCharField(char charField) {
		this.charField = charField;
	}

	public Character getCharacterField() {
		return characterField;
	}

	public void setCharacterField(Character characterField) {
		this.characterField = characterField;
	}

	public byte getByteField() {
		return byteField;
	}

	public void setByteField(byte byteField) {
		this.byteField = byteField;
	}

	public Byte getByteField2() {
		return byteField2;
	}

	public void setByteField2(Byte byteField2) {
		this.byteField2 = byteField2;
	}

	public int getIntField() {
		return intField;
	}

	public void setIntField(int intField) {
		this.intField = intField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public float getFloatField() {
		return floatField;
	}

	public void setFloatField(float floatField) {
		this.floatField = floatField;
	}

	public Float getFloatField2() {
		return floatField2;
	}

	public void setFloatField2(Float floatField2) {
		this.floatField2 = floatField2;
	}

	public long getLongField() {
		return longField;
	}

	public void setLongField(long longField) {
		this.longField = longField;
	}

	public Long getLongField2() {
		return longField2;
	}

	public void setLongField2(Long longField2) {
		this.longField2 = longField2;
	}

	public double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public Double getDoubleField2() {
		return doubleField2;
	}

	public void setDoubleField2(Double doubleField2) {
		this.doubleField2 = doubleField2;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public Byte[] getByteArray2() {
		return byteArray2;
	}

	public void setByteArray2(Byte[] byteArray2) {
		this.byteArray2 = byteArray2;
	}

	public char[] getCharArray() {
		return charArray;
	}

	public void setCharArray(char[] charArray) {
		this.charArray = charArray;
	}

	public Character[] getCharacterArray() {
		return characterArray;
	}

	public void setCharacterArray(Character[] characterArray) {
		this.characterArray = characterArray;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}

	public Integer[] getIntegerArray2() {
		return integerArray2;
	}

	public void setIntegerArray2(Integer[] integerArray2) {
		this.integerArray2 = integerArray2;
	}

	public float[] getFloatArray() {
		return floatArray;
	}

	public void setFloatArray(float[] floatArray) {
		this.floatArray = floatArray;
	}

	public Float[] getFloatArray2() {
		return floatArray2;
	}

	public void setFloatArray2(Float[] floatArray2) {
		this.floatArray2 = floatArray2;
	}

	public long[] getLongArray() {
		return longArray;
	}

	public void setLongArray(long[] longArray) {
		this.longArray = longArray;
	}

	public Long[] getLongArray2() {
		return longArray2;
	}

	public void setLongArray2(Long[] longArray2) {
		this.longArray2 = longArray2;
	}

	public double[] getDoubleArray() {
		return doubleArray;
	}

	public void setDoubleArray(double[] doubleArray) {
		this.doubleArray = doubleArray;
	}

	public Double[] getDoubleArray2() {
		return doubleArray2;
	}

	public void setDoubleArray2(Double[] doubleArray2) {
		this.doubleArray2 = doubleArray2;
	}

	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}

	public boolean isBooleanField() {
		return booleanField;
	}

	public void setBooleanField2(Boolean booleanField2) {
		this.booleanField2 = booleanField2;
	}

	public Boolean getBooleanField2() {
		return booleanField2;
	}

	public boolean[] getBoolArray() {
		return boolArray;
	}

	public void setBoolArray(boolean[] boolArray) {
		this.boolArray = boolArray;
	}

	public Boolean[] getBoolArray2() {
		return boolArray2;
	}

	public void setBoolArray2(Boolean[] boolArray2) {
		this.boolArray2 = boolArray2;
	}

	public void setAnotherObject(TestRemoteObject anotherObject) {
		this.anotherObject = anotherObject;
	}

	public TestRemoteObject getAnotherObject() {
		return anotherObject;
	}

}
