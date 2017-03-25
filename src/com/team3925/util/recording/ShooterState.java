package com.team3925.util.recording;

public class ShooterState {

	public double speed;

	public ShooterState(double speed) {
		this.speed = speed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ShooterState) {
			return ((ShooterState) obj).speed == speed;
		} else
			return false;
	}

}
