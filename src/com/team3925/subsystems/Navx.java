package com.team3925.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;

public class Navx {

	private static Navx instance;

	private AHRS ahrs;

	public static Navx getInstance() {
		if (instance == null)
			instance = new Navx();
		return instance;
	}

	private Navx() {
		ahrs = new AHRS(Port.kMXP);
		ahrs.reset();
	}

	public double getHeading() {
		return ahrs.getAngle();
	}

	public void resetNavx() {
		ahrs.reset();
		ahrs.zeroYaw();
	}

}
