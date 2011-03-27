package com.joegaudet.remote;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.joegaudet.remote.compute.RemoteMethodInvocation;
import com.joegaudet.remote.compute.Result;
import com.joegaudet.remote.serialize.RemoteObjectSerializer;

@SuppressWarnings("serial")
public class RemoteProxyObject implements InvocationHandler, Serializable {

	private RemoteObject object;
	private RemoteObjectSerializer serializer =	new RemoteObjectSerializer();

	@SuppressWarnings("unchecked")
	public static <T extends RemoteObject, I extends RemoteObject> I newInstance(T object, Class<I> klass) {
		return (I) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new RemoteProxyObject(object));
	}

	private RemoteProxyObject(RemoteObject object) {
		this.object = object;
	}

	/**
	 * This is where the magic should happen with regards to remote invocation
	 */
	public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
//		System.out.println("Invoking method " + m.getName());
		Object result;
		try {
			Remote remote = object.getClass().getMethod(m.getName(), m.getParameterTypes()).getAnnotation(Remote.class);
			if (remote == null) {
				result = m.invoke(object, args);
			} else {
				result = this.conditionallyInvokeRemote(m, args);
			}
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
		}
		return result;
	}

	private Object conditionallyInvokeRemote(Method m, final Object[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		System.out.println("Detected a remote annotation attempting to Inoke Method: " + m.getName() + " remotely.");
		
		Object retval = null;
		SocketChannel channel = null;
		try {
			channel = SocketChannel.open(new InetSocketAddress("localHost", 5678));
			RemoteMethodInvocation rmi = new RemoteMethodInvocation(m, object, args);
			ByteBuffer serialize = rmi.toBuffer();
			serialize.rewind();
			
			while(serialize.hasRemaining()) channel.write(serialize);

			// return
			ByteBuffer buffer = ByteBuffer.allocate(4);
			channel.read(buffer);
			buffer.rewind();
			
			buffer = ByteBuffer.allocate(buffer.getInt());
			while(buffer.hasRemaining()) channel.read(buffer);
			
			buffer.rewind();
			
			System.out.println("Returned: " + buffer.capacity());
			
			Result result = new Result(buffer);

			System.out.println("result :" + result.getType());

			retval = result.getResult();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if(channel != null && channel.isOpen()){
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return retval;
	}

}