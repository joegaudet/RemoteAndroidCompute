package com.joegaudet.remote;

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

}

