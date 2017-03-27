
package com.team3925.util;

public class TimedFieldPosition extends FieldPosition {
	
	public double time;
	
	public TimedFieldPosition(double x, double y, double rotation, double time) {
		super(x, y, rotation);
		this.time = time;
	}
	
	public TimedFieldPosition(double x, double y, double rotation) {
		this(x, y, rotation, 0);
	}
	
	public TimedFieldPosition(FieldPosition position, double time) {
		this(position.x, position.y, position.rotation, time);
	}
	
	public TimedFieldPosition(FieldPosition position) {
		this(position.x, position.y, position.rotation, 0);
	}
	
	public TimedFieldPosition() {
		this(0, 0, 0, 0);
	}
	
	public TimedFieldPosition clone() {
		return new TimedFieldPosition(x, y, rotation, time);
	}
	
}
