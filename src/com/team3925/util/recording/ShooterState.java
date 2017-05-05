package com.team3925.util.recording;

import java.io.Serializable;

public class ShooterState implements Serializable {

	private static final long serialVersionUID = -7411006141152698709L;
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
