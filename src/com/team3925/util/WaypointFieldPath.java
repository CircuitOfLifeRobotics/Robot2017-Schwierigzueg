
package com.team3925.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class WaypointFieldPath implements Path<FieldPosition> {
	
	private LinkedHashMap<Double, FieldPosition> times_wayPoints;
	private double totalTime;
	
	public WaypointFieldPath(LinkedList<FieldPosition> wayPoints, double totalTime) {
		times_wayPoints = new LinkedHashMap<>();
		this.totalTime = totalTime;
		double increment = totalTime / (wayPoints.size() - 1);
		for (int i = 0; i < wayPoints.size(); ++i) {
			times_wayPoints.put(i * increment, wayPoints.get(i).clone());
		}
	}
	
	@Override
	public FieldPosition get(double time) {
		if (time<=totalTime)
			return times_wayPoints.get(times_wayPoints.keySet().stream().filter(t -> t.doubleValue()>=time).findFirst());
		else
			return FieldPosition.ZEROZEROZERO;
	}
	
	@Override
	public double getTotalTime() {
		return totalTime;
	}
	
}
