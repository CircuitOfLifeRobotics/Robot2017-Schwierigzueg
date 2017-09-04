package com.team3925.util.recordable;

import java.io.Serializable;

/**
 * @author Adam Mendenhall
 *
 * @param <T>
 *            This parameter signifies the type of data that would be saved as a
 *            single snapshot in a recording. It must be {@link Serializable} so
 *            that it can be saved as a file (if it only contains primitive
 *            types then it can implement {@link Serializable} with no problems)
 *            and should override the {@link #equals(Object)} method in order to
 *            compare and contrast snapshots in time.
 */
public interface Recordable<T> {
	
	T record();
	
	void repeat(T snapshot);
	
}
