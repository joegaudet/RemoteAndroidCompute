package com.joegaudet.list;

public interface Filter<T> {
	public boolean apply(T element);
}
