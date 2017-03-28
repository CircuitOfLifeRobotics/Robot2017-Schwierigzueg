package com.team3925.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO: import AHRS

public class Navx {

	private static Navx instance;

	private AHRS ahrs;
	
	public static Navx getInstance() {
		if (instance == null)
			instance = new Navx();
		return instance;
	}

	private Navx() {
		//TODO: correct constructor
		ahrs = new AHRS(Port.kMXP);
		ahrs.reset();
	}
	
	public double getHeading() {
		SmartDashboard.putNumber("Roll", ahrs.getRoll());
		SmartDashboard.putNumber("Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		return ahrs.getAngle();
	}
	
	public void resetNavx(){
		ahrs.reset();
	}
	
}
