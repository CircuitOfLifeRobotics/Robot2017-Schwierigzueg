
package com.team3925.util;

import java.util.LinkedList;

public class WaypointFieldPath extends Path<TimedFieldPosition> {
	
	private LinkedList<TimedFieldPosition> wayPoints;
	
	public WaypointFieldPath(LinkedList<TimedFieldPosition> timedWayPoints) {
		wayPoints = new LinkedList<>();
		wayPoints.addAll(timedWayPoints);
	}
	
	public WaypointFieldPath(LinkedList<FieldPosition> wayPoints, double totalTime) {
		this.wayPoints = new LinkedList<>();
		double increment = totalTime / (wayPoints.size() - 1);
		for (int i = 0; i < wayPoints.size(); ++i) {
			this.wayPoints.add(new TimedFieldPosition(wayPoints.get(i), i * increment));
		}
	}
	
	@Override
	public TimedFieldPosition get(int index) {
		index = Math.max(0, Math.min(wayPoints.size() - 1, index));
		return wayPoints.get(index);
	}
	
	@Override
	public boolean isFinished() {
		return currentIndex >= wayPoints.size() - 1;
	}
	
}
