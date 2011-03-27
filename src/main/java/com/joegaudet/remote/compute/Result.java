package com.joegaudet.remote.compute;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Result {
	
	public Object result;
	private Type type;
	
	public Result(Object result){
		this.result = result;
		this.type = Type.typeForClass(result.getClass());
	}
	
	public Result(ByteBuffer buffer) throws IOException, ClassNotFoundException{
		this.type = Type.getTypeForByte(buffer.get());
		this.result = type.getObject(buffer);
	}
	
	public ByteBuffer toBuffer(){
		int size = type.sizeOf(result);
		ByteBuffer buffer = ByteBuffer.allocate(4 + size);
		buffer.putInt(size);
		type.putObject(result, buffer);
		buffer.rewind();
		return buffer;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T get(Class<T> klass){
		return (T) result;
	}

	public Object getResult() {
		return result;
	}

	public Type getType() {
		return type;
	}
	
}
