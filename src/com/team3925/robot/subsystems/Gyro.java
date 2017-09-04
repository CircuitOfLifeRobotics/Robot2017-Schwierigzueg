package com.team3925.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

	private AHRS ahrs;

	private static Gyro instance;

	private Gyro() {
		ahrs = new AHRS(Port.kMXP);
	}

	public static Gyro getInstance() {
		return instance == null ? instance = new Gyro() : instance;
	}

	@Override
	protected void initDefaultCommand() {

	}

	public double getFusedHeading() {
		return ahrs.getFusedHeading();
	}

}
