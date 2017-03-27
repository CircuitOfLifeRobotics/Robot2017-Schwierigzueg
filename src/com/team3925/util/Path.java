
package com.team3925.util;

public abstract class Path<T> {
	
	int currentIndex;
	
	public Path() {
		reset();
	}
	
	public abstract T get(int index);
	
	public abstract boolean isFinished();
	
	public void reset() {
		currentIndex = -1;
	}
	
	public T getNext() {
		return get(++currentIndex);
	}
	
}
