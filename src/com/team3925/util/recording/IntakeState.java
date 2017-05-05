package com.team3925.util.recording;

import java.io.Serializable;

public class IntakeState implements Serializable {

	private static final long serialVersionUID = -8170787415559249973L;
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
