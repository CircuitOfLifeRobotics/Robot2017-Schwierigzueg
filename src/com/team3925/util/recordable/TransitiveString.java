package com.team3925.util.recordable;

public abstract class TransitiveString<T extends TransitiveString<T>> {
	
	public abstract String toString();
	
	public abstract T fromString(String s);
	
}
