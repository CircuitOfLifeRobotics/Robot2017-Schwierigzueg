package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

class Turret extends Subsystem {
	
	private static Turret instance;
	
	public static Turret getInstance() {
		return (instance == null) ? instance = new Turret() : instance;
	}
	
	private Turret() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	public void getConversionFactor(){
		
	}
	
}