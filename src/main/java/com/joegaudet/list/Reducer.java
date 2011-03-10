package com.joegaudet.list;

public interface Reducer<T, E> {
	public T apply(T carry, E element);
}
