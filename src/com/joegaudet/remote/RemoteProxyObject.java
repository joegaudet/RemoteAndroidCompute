package com.joegaudet.remote;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.joegaudet.remote.compute.Compute;
import com.joegaudet.remote.compute.OffloadComputeTask;

@SuppressWarnings("serial")
public class RemoteProxyObject implements InvocationHandler, Serializable {

	private RemoteObject object;

	@SuppressWarnings("unchecked")
	public static <T extends RemoteObject, U extends RemoteObject> T newInstance(U object, Class<T> klass) {
		return (T) java.lang.reflect.Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new RemoteProxyObject(object));
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
//		System.out.println("Detected a remote annotation attempting to Inoke Method: " + m.getName() + " remotely.");
		
		Object retval = null;
		OffloadComputeTask task = new OffloadComputeTask(object, m.getName(), args, m.getParameterTypes());
		try {
			Registry registry = LocateRegistry.getRegistry("50.56.71.70",1099);
			Compute comp = (Compute) registry.lookup("Compute");
			retval = comp.executeTask(task);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return retval;
	}

}