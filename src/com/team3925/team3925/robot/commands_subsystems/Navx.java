
package com.team3925.team3925.robot.commands_subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Navx extends Subsystem {
	
	private static Navx instance;
	
	private AHRS ahrs;
	
	public static Navx getInstance() {
		return (instance == null) ? instance = new Navx() : instance;
	}
	
	private Navx() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}
