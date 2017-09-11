package com.team3925.util;

import java.util.HashSet;

import edu.wpi.first.wpilibj.SpeedController;

public class MultiSpeedController implements SpeedController {

	private HashSet<SpeedController> controllers;
	private double lastSet;

	public MultiSpeedController(SpeedController... controllers) {
		this.controllers = new HashSet<>();
		for (SpeedController speedController : controllers)
			this.controllers.add(speedController);
		lastSet = 0;
	}

	public void addController(SpeedController speedController, boolean inverted) {
		controllers.add(speedController);
		speedController.setInverted(inverted);
	}
	
	public void addController(SpeedController speedController) {
		controllers.add(speedController);
	}

	public void removeAll() {
		controllers.clear();
	}

	@Override
	public void pidWrite(double output) {
		controllers.forEach(c -> c.pidWrite(output));
	}

	@Override
	public double get() {
		return lastSet;
	}

	@Override
	public void set(double speed) {
		lastSet = speed;
		controllers.forEach(c -> c.set(speed));
	}

	@Deprecated
	@Override
	/**
	 * This method does nothing
	 */
	public void setInverted(boolean isInverted) {
	}

	@Deprecated
	@Override
	/**
	 * This method does nothing
	 */
	public boolean getInverted() {
		return false;
	}

	@Override
	public void disable() {
		controllers.forEach(c -> c.disable());
	}

	@Override
	public void stopMotor() {
		controllers.forEach(c -> c.stopMotor());
	}

}
