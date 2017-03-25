package com.team3925.util.recording;

public class FeederState {

	public double speed;

	public FeederState(double speed) {
		this.speed = speed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FeederState) {
			return ((FeederState) obj).speed == speed;
		} else
			return false;
	}

}
