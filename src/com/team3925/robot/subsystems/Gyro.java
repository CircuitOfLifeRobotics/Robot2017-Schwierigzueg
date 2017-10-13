package com.team3925.robot.subsystems;

import java.util.Optional;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

	private AHRS ahrs;
	private Optional<Double> lastHeading;
	private Optional<Double> lastFusedFloorAccel;
	private int rotations;

	private static Gyro instance;

	private Gyro() {
		ahrs = new AHRS(Port.kMXP);
		lastFusedFloorAccel = lastHeading = Optional.empty();
		rotations = 0;
	}

	public static Gyro getInstance() {
		return instance == null ? instance = new Gyro() : instance;
	}

	@Override
	protected void initDefaultCommand() {

	}

	public double getFusedFloorAccel() {
		double magAccel;
		double xPred = Gyro.getInstance().getXAccel() / Math.cos(Gyro.getInstance().getFusedHeading());
		double yPred = Gyro.getInstance().getYAccel() / Math.sin(Gyro.getInstance().getFusedHeading());
		double shouldBeZero = xPred - yPred;
		boolean bad = Math.abs(shouldBeZero) > 1;
		if (Math.abs(xPred) > Math.abs(yPred))
			magAccel = yPred;
		else
			magAccel = xPred;
		if (bad && lastFusedFloorAccel.isPresent())
			return lastFusedFloorAccel.get();
		lastFusedFloorAccel = Optional.of(magAccel);
		return magAccel;
		// if (!lastFusedFloorAccel.isPresent()) {
		// lastFusedFloorAccel = Optional.of(magAccel);
		// return magAccel;
		// } else {
		// double last = lastFusedFloorAccel.get();
		// lastFusedFloorAccel = Optional.of(magAccel);
		// return (last + magAccel) / 2;
		// }
	}

	public double getFusedHeading() {
		double currHeading = ahrs.getFusedHeading();
		if (lastHeading.isPresent()) {
			if (Math.abs(lastHeading.get() - currHeading) > 180)
				rotations += Math.signum(lastHeading.get() - currHeading);
		}
		lastHeading = Optional.of(currHeading);
		return lastHeading.get() + 360 * rotations;
	}

	public void setAngleToZero() {
		ahrs.zeroYaw();
		lastHeading = Optional.empty();
		rotations = 0;
	}

	public double getXAccel() {
		return ahrs.getWorldLinearAccelX();
	}

	public double getYAccel() {
		return ahrs.getWorldLinearAccelY();
	}

	public double getZAccel() {
		return ahrs.getWorldLinearAccelZ();
	}

	public double getXVelocity() {
		return ahrs.getVelocityX();
	}

	public double getYVelocity() {
		return ahrs.getVelocityY();
	}

	public double getZVelocity() {
		return ahrs.getVelocityZ();
	}

}
