package com.team3925.util.recording;

public class IntakeState {

	public boolean upDown;
	public double speed;

	public IntakeState(boolean upDown, double speed) {
		this.upDown = upDown;
		this.speed = speed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntakeState) {
			IntakeState state = (IntakeState) obj;
			return state.upDown == upDown && state.speed == speed;
		} else
			return false;
	}

}
