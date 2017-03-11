package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

class PInterface extends Subsystem {
	
	private static PInterface instance;
	
	public static PInterface getInstance() {
		return (instance == null) ? instance = new PInterface() : instance;
	}
	
	private PInterface() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}
