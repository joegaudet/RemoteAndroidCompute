package com.joegaudet.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("serial")
/**
 * Extension to the Java standard ArrayList to allow for some
 * functional(ish) goodness.
 */
public class FArrayList<E> extends ArrayList<E> implements FList<E> {

	public FArrayList() {
		super();
	}
	
	public FArrayList(E[] arr){
		super(Arrays.asList(arr));
	}
	
	public FArrayList(Collection<E> collection) {
		super(collection);
	}

	public FArrayList(int capacity) {
		super(capacity);
	}

	/* (non-Javadoc)
	 * @see com.matygo.list.MList#filter(com.matygo.list.Filter)
	 */
	@Override
	public FList<E> filter(Filter<E> filter) {
		FArrayList<E> retval = new FArrayList<E>();
		for (E element : this) {
			if (filter.apply(element)) {
				retval.add(element);
			}
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see com.matygo.list.MList#map(com.matygo.list.MapFilter)
	 */
	@Override
	public <O extends Object> FList<O> map(MapFilter<E, O> filter) {
		FArrayList<O> retval = new FArrayList<O>();
		for (E element : this) {
			retval.add(filter.map(element));
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see com.matygo.list.MList#find(com.matygo.list.Filter)
	 */
	@Override
	public E find(Filter<E> filter) {
		E e = null;
		for (E element : this) {
			if (filter.apply(element)) {
				e = element;
				break;
			}
		}
		return e;
	}

	/* (non-Javadoc)
	 * @see com.matygo.list.MList#some(com.matygo.list.Filter)
	 */
	@Override
	public boolean some(Filter<E> filter) {
		boolean retval = false;
		for (E element : this) {
			if (filter.apply(element)) {
				retval = true;
				break;
			}
		}
		return retval;
	}

	
	/* (non-Javadoc)
	 * @see com.matygo.list.MList#every(com.matygo.list.Filter)
	 */
	@Override
	public boolean every(Filter<E> filter) {
		boolean retval = true;
		for (E element : this) {
			retval &= filter.apply(element);

			if (!retval)
				break;
		}
		return retval;
	}
	
	/* (non-Javadoc)
	 * @see com.matygo.list.MList#reduce(T, com.matygo.list.Reducer)
	 */
	@Override
	public <T extends Object> T reduce(T initialValue, Reducer<T,E> reducer){
		for(E element : this){
			initialValue = reducer.apply(initialValue, element);
		}
		return initialValue;
	}
	
	/* (non-Javadoc)
	 * @see com.matygo.list.MList#last()
	 */
	@Override
	public E last(){
		int size = size();
		return size == 0 ? null : this.get(size -1);
	}

	/* (non-Javadoc)
	 * @see com.matygo.list.MList#first()
	 */
	@Override
	public E first(){
		return size() == 0 ? null : this.get(0);
	}
}
