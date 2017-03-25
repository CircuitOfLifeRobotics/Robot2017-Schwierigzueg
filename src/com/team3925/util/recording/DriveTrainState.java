package com.team3925.util.recording;

import java.io.Serializable;

public class DriveTrainState implements Serializable {

	private static final long serialVersionUID = 6299809317521671627L;
	public double left, right;

	public DriveTrainState(double left, double right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "Left: " + left + ", Right: " + right;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DriveTrainState) {
			DriveTrainState s = (DriveTrainState) obj;
			return s.left == left && s.right == right;
		} else
			return false;
	}

}
