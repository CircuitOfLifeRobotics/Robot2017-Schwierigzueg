package com.team3925.util;

import java.util.function.Function;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public abstract class Profile<T> implements Function<Double, T> {
	
	protected double startTime, endTime;
	protected T start, end;
	
	@Override
	public T apply(Double t) {
		return null;
	}

	protected abstract T applyTimeGaranteed(Double t);

}
