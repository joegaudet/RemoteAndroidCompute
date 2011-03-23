package com.joegaudet.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import com.joegaudet.remote.fields.arrays.BooleanArrayField;
import com.joegaudet.remote.fields.arrays.ByteArrayField;
import com.joegaudet.remote.fields.arrays.CharArrayField;
import com.joegaudet.remote.fields.arrays.DoubleArrayField;
import com.joegaudet.remote.fields.arrays.FloatArrayField;
import com.joegaudet.remote.fields.arrays.IntArrayField;
import com.joegaudet.remote.fields.arrays.LongArrayField;
import com.joegaudet.remote.fields.arrays.ShortArrayField;
import com.joegaudet.remote.fields.primatives.BooleanField;
import com.joegaudet.remote.fields.primatives.ByteField;
import com.joegaudet.remote.fields.primatives.CharacterField;
import com.joegaudet.remote.fields.primatives.DoubleField;
import com.joegaudet.remote.fields.primatives.FloatField;
import com.joegaudet.remote.fields.primatives.IntegerField;
import com.joegaudet.remote.fields.primatives.LongField;
import com.joegaudet.remote.fields.primatives.ShortField;
import com.joegaudet.remote.fields.primatives.StringField;
import com.joegaudet.remote.visitors.ObjectSizeVisitor;
import com.joegaudet.util.TestHelper;


public class TestRemoteObject extends AbstractRemoteObject {

	private ByteArrayField 		byteArrayField;
	private BooleanArrayField 	booleanArrayField;
	private ShortArrayField 	shortArrayField;
	private CharArrayField 		charArrayField;
	private IntArrayField 		intArrayField;
	private FloatArrayField 	floatArrayField;
	private LongArrayField 		longArrayField;
	private DoubleArrayField 	doubleArrayField;
	
	private ByteField 		byteField;
	private BooleanField 	booleanField;
	private ShortField 		shortField;
	private CharacterField 	charField;
	private IntegerField 	intField;
	private FloatField 		floatField;
	private LongField 		longField;
	private DoubleField 	doubleField;
	private StringField 	stringField;
	
	
	public int initializeRemoteObject() throws IllegalAccessException {

		int expectedSize = header();

		this.setByteField(TestHelper.getAnonymousByte());
		expectedSize += 5;

		this.setBooleanField(TestHelper.getAnonymousBoolean());
		expectedSize += 5;

		this.setShortField(TestHelper.getAnonymousShort());
		expectedSize += 6;

		this.setCharField(TestHelper.getAnonymousCharacter());
		expectedSize += 6;

		this.setIntField(TestHelper.getAnonymousInt());
		expectedSize += 8;

		this.setFloatField(TestHelper.getAnonymousFloat());
		expectedSize += 8;

		this.setLongField(TestHelper.getAnonymousLong());
		expectedSize += 12;

		this.setDoubleField(TestHelper.getAnonymousDouble());
		expectedSize += 12;

		String string = TestHelper.getAnonymousString("Test String");
		this.setStringField(string);
		expectedSize += 8 + string.length();
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		
		// bytes
		byte[] byteArray = TestHelper.getAnonymousByteArray();
		this.setByteArrayField(byteArray);
		expectedSize += byteArray.length + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// booleans
		boolean[] booleanArray = TestHelper.getAnonymousBooleanArray();
		this.setBooleanArrayField(booleanArray);
		expectedSize += booleanArray.length + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// char arrays
		char[] charArray = TestHelper.getAnonymousCharArray();
		this.setCharArrayField(charArray);
		expectedSize += charArray.length * 2 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// short arrays
		short[] shortArray = TestHelper.getAnonymousShortArray();
		this.setShortArrayField(shortArray);
		expectedSize += shortArray.length * 2 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// int arrays
		int[] intArray = TestHelper.getAnonymousIntArray();
		this.setIntArrayField(intArray);
		expectedSize += intArray.length * 4 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// float arrays
		float[] floatArray = TestHelper.getAnonymousFloatArray();
		this.setFloatArrayField(floatArray);
		expectedSize += floatArray.length * 4 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// long arrays
		long[] longArray = TestHelper.getAnonymousLongArray();
		this.setLongArrayField(longArray);
		expectedSize += longArray.length * 8 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		// long arrays
		double[] doubleArray = TestHelper.getAnonymousDoubleArray();
		this.setDoubleArrayField(doubleArray);
		expectedSize += doubleArray.length * 8 + 8;
		assertEquals(expectedSize, new ObjectSizeVisitor(this).computeObjectSize());

		return expectedSize;
	}

	public int header() {
		int expectedSize = 4; // object size
		expectedSize += 4; // class name size
		expectedSize += 4; // remote object hash code
		expectedSize += this.getClass().getName().length();
		return expectedSize;
	}
	
	public byte getByteField(){
		return byteField.get();
	}
	
	public void setByteField(byte value){
		byteField.set(value);
	}

	public boolean getBooleanField(){
		return booleanField.get();
	}
	
	public void setBooleanField(boolean value){
		booleanField.set(value);
	}
	
	public short getShortField(){
		return shortField.get();
	}
	
	public void setShortField(short value){
		shortField.set(value);
	}
	
	public char getCharField(){
		return charField.get();
	}
	
	public void setCharField(char value){
		charField.set(value);
	}
	
	public int getIntField(){
		return intField.get();
	}
	
	public void setIntField(int value){
		intField.set(value);
	}
	
	public float getFloatField(){
		return floatField.get();
	}
	
	public void setFloatField(float value){
		floatField.set(value);
	}
	
	public long getLongField(){
		return longField.get();
	}
	
	public void setLongField(long value){
		longField.set(value);
	}
	
	public double getDoubleField(){
		return doubleField.get();
	}
	
	public void setDoubleField(double value){
		doubleField.set(value);
	}
	
	public String getStringField(){
		return stringField.get();
	}
	
	public void setStringField(String value){
		stringField.set(value);
	}
	
	public byte[] getByteArrayField() {
		return byteArrayField.get();
	}
	
	public void setByteArrayField(byte[] value) {
		byteArrayField.set(value);
	}
	
	public boolean[] getBooleanArrayField() {
		return booleanArrayField.get();
	}
	
	public void setBooleanArrayField(boolean[] value) {
		booleanArrayField.set(value);
	}

	public short[] getShortArrayField() {
		return shortArrayField.get();
	}
	
	public void setShortArrayField(short[] value) {
		shortArrayField.set(value);
	}

	public char[] getCharArrayField() {
		return charArrayField.get();
	}
	
	public void setCharArrayField(char[] value) {
		charArrayField.set(value);
	}
	
	public int[] getIntArrayField() {
		return intArrayField.get();
	}
	
	public void setIntArrayField(int[] value) {
		intArrayField.set(value);
	}
	
	public float[] getFloatArrayField() {
		return floatArrayField.get();
	}
	
	public void setFloatArrayField(float[] value) {
		floatArrayField.set(value);
	}
	
	public long[] getLongArrayField() {
		return longArrayField.get();
	}
	
	public void setLongArrayField(long[] value) {
		longArrayField.set(value);
	}
	
	public double[] getDoubleArrayField() {
		return doubleArrayField.get();
	}
	
	public void setDoubleArrayField(double[] value) {
		doubleArrayField.set(value);
	}
	

	public void assertObjectEquals(TestRemoteObject object) {
		// primatives fields
		assertEquals(this.getBooleanField(), object.getBooleanField());
		assertEquals(this.getByteField(), object.getByteField());
		assertEquals(this.getCharField(), object.getCharField());
		assertEquals(this.getDoubleField(), object.getDoubleField(), 0);
		assertEquals(this.getFloatField(), object.getFloatField(), 0);
		assertEquals(this.getIntField(), object.getIntField());
		assertEquals(this.getLongField(), object.getLongField());
		assertEquals(this.getShortField(), object.getShortField());
		assertEquals(this.getStringField(), object.getStringField());

		// primative arrays

		assertTrue(Arrays.equals(this.getBooleanArrayField(), object.getBooleanArrayField()));
		assertTrue(Arrays.equals(this.getByteArrayField(), object.getByteArrayField()));
		assertTrue(Arrays.equals(this.getCharArrayField(), object.getCharArrayField()));
		assertTrue(Arrays.equals(this.getDoubleArrayField(), object.getDoubleArrayField()));
		assertTrue(Arrays.equals(this.getFloatArrayField(), object.getFloatArrayField()));
		assertTrue(Arrays.equals(this.getIntArrayField(), object.getIntArrayField()));
		assertTrue(Arrays.equals(this.getLongArrayField(), object.getLongArrayField()));
		assertTrue(Arrays.equals(this.getShortArrayField(), object.getShortArrayField()));
	}

}

