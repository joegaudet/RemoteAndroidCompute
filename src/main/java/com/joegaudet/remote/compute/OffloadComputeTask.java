package com.joegaudet.remote.compute;

import java.lang.reflect.Method;

import com.joegaudet.remote.RemoteObject;

public class OffloadComputeTask implements Task<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final RemoteObject object;
	private final String methodName;
	private final Object[] args;
	private final Class<?>[] parameterTypes;

	public OffloadComputeTask(RemoteObject object, String methodName, Object[] args, Class<?>[] parameterTypes) {
		this.object = object;
		this.methodName = methodName;
		this.args = args;
		this.parameterTypes = parameterTypes;
	}

	@Override
	public Object execute() {
		Object retval = null;
		try {
			Method m = object.getClass().getMethod(methodName, parameterTypes);
			retval = m.invoke(object, args);
		} catch (Exception e) {
			System.err.println("Error Executing Task");
			e.printStackTrace();
		}

		return retval;
	}

}
