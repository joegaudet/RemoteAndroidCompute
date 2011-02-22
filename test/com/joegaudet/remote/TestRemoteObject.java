package com.joegaudet.remote;

import java.util.Arrays;
import java.util.Random;

public class TestRemoteObject implements RemoteObject {

	private char charField;
	private Character characterField;

	private byte byteField;
	private Byte byteField2;

	private int intField;
	private Integer integerField;

	private float floatField;
	private Float floatField2;

	private long longField;
	private Long longField2;

	private double doubleField;
	private Double doubleField2;

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

	public TestRemoteObject(){
		
	}
	
	public TestRemoteObject(Random rand) {
		charField = (char) rand.nextInt(255);
		characterField = (char) rand.nextInt(255);

		byteField = (byte) rand.nextInt(255);
		byteField2 = (byte) rand.nextInt(255);

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
		
		if(!(obj instanceof TestRemoteObject)) return false;
		
		TestRemoteObject other = (TestRemoteObject)obj;
		boolean retval = true;

		retval &= byteField == other.getByteField();
		retval &= byteField2.equals(other.getByteField2());
		
		retval &= charField == other.getCharField();
		retval &= characterField.equals(other.getCharacterField());
		
		retval &= intField == other.getIntField();
		retval &= integerField.equals(other.getIntegerField());
		
		retval &= floatField == other.getFloatField();
		retval &= floatField2.equals(other.getFloatField2());
		
		retval &= longField == other.getLongField();
		retval &= longField2.equals(other.getLongField2());
		
		retval &= Arrays.equals(byteArray, other.getByteArray());
		retval &= Arrays.equals(byteArray2, other.getByteArray2());
		
		retval &= Arrays.equals(charArray, other.getCharArray());
		retval &= Arrays.equals(characterArray, other.getCharacterArray());
		
		retval &= Arrays.equals(intArray, other.getIntArray());
		retval &= Arrays.equals(integerArray2, other.getIntegerArray2());
		
		retval &= Arrays.equals(floatArray,	 other.getFloatArray());
		retval &= Arrays.equals(floatArray2, other.getFloatArray2());
		
		retval &= Arrays.equals(longArray,	other.getLongArray());
		retval &= Arrays.equals(longArray2, other.getLongArray2());
		retval &= Arrays.equals(doubleArray,	other.getDoubleArray());
		retval &= Arrays.equals(doubleArray2, 	other.getDoubleArray2());
		
		return retval;
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

}
