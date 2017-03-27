
package com.team3925.util;

public class FieldPosition {
	
	public static final FieldPosition ZEROZEROZERO = new FieldPosition(0, 0, 0);
	
	public double x, y, rotation;
	
	public FieldPosition(double x, double y, double rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	
	public FieldPosition() {
		this(0, 0, 0);
	}
	
	public FieldPosition clone() {
		return new FieldPosition(x, y, rotation);
	}
	
}
