package com.team3925.util.recording;

import java.io.Serializable;

public class FeederState implements Serializable {

	private static final long serialVersionUID = 94923787096026432L;
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
