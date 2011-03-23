package com.joegaudet.remote.compute;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import com.joegaudet.remote.RemoteObject;
import com.joegaudet.remote.serialize.RemoteObjectDeserializer;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;
import com.joegaudet.remote.visitors.SchemaSizeVisitor;

public class RemoteMethodInvocation {

	public int methodNameHashCode;
	public RemoteObject target;
	public ArgumentArray args;
	
	public RemoteMethodInvocation(Method m, RemoteObject target, Object... args){
		this.target = target;
		this.methodNameHashCode = m.getName().hashCode();
		this.args = new ArgumentArray(args);
	}
	
	public RemoteMethodInvocation() {
	}

	public ByteBuffer serialize(){
		int size =  8 + args.size() + 4 + new SchemaSizeVisitor(target).computeSchemaSize();
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.putInt(size);
		buffer.putInt(methodNameHashCode);
		new RemoteObjectSerializer().serialize(target, buffer);
		args.toBuffer(buffer);
		return buffer;
	}
	
	public void deserialize(ReadableByteChannel channel) throws IOException, ClassNotFoundException{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		channel.read(buffer);
		buffer.rewind();
		buffer = ByteBuffer.allocate(buffer.getInt());
		channel.read(buffer);
		buffer.rewind();
		methodNameHashCode = buffer.getInt();
		target = new RemoteObjectDeserializer().readObjectFromByteBuffer(buffer);
		args = new ArgumentArray(buffer);
	}

	public void deserialize(ByteBuffer buffer) throws IOException, ClassNotFoundException{
		methodNameHashCode = buffer.getInt();
		target = new RemoteObjectDeserializer().readObjectFromByteBuffer(buffer);
		args = new ArgumentArray(buffer);
	}
	
	public Object invoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		String methodName = null;
		for(Method method : target.getClass().getMethods()){
			String name = method.getName();
			if(name.hashCode() == methodNameHashCode){
				methodName = name;
				break;
			}
		}
		
		Object[] methodArgs = args.getArray();
		Class<?>[] argTypes = new Class<?>[methodArgs.length];
		for(int i = 0; i < methodArgs.length; i++){
			argTypes[i] = methodArgs[i].getClass();
		}
		return target.getClass().getMethod(methodName, argTypes).invoke(target, methodArgs);
	}
	
}
