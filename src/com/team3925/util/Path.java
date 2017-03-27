
package com.team3925.util;

public interface Path<T> {
	
	public abstract T get(double time);
	
	public abstract double getTotalTime();
	
}
