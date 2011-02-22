package com.joegaudet.list;

import java.util.List;

public interface FList<E> extends List<E>{

	/**
	 * @param filter - filter to be applied to each element
	 * @return a list where ever element satisfies the method provided in the filter
	 */
	public abstract FList<E> filter(Filter<E> filter);

	/**
	 * @param <O> The output object that is generated in the mapping
	 * @param filter - The Mapping which transforms an element in the list, 
	 * into an element in the resultant list
	 * @return A list of the mapped elements
	 */
	public abstract <O extends Object> FList<O> map(MapFilter<E, O> filter);

	/**
	 * Finds the first element that satisfies the filter function
	 * @param filter 
	 * @return
	 */
	public abstract E find(Filter<E> filter);

	/**
	 * @param filter 
	 * @return whether or not some element in the list satisfies the method specified
	 * in the filter object
	 */
	public abstract boolean some(Filter<E> filter);

	/**
	 * @param filter 
	 * @return whether or not every element in the list satisfies the method specified
	 * in the filter object
	 */
	public abstract boolean every(Filter<E> filter);

	/**
	 * Reduces every element in the list to some value, by way of the reduction
	 * function
	 * @param <T>
	 * @param initialValue
	 * @param reducer
	 * @return
	 */
	public abstract <T extends Object> T reduce(T initialValue, Reducer<T, E> reducer);

	public abstract E last();

	public abstract E first();

}