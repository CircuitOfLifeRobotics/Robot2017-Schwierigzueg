package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

class Intake extends Subsystem {
	
	private static Intake instance;
	
	public static Intake getInstance() {
		return (instance == null) ? instance = new Intake() : instance;
	}
	
	private Intake() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}