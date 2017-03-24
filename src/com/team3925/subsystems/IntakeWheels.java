package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeWheels extends Subsystem {
	
	private static IntakeWheels instance;
	
	public static IntakeWheels getInstance() {
		if (instance==null)
			instance = new IntakeWheels();
		return instance;
	}
	
	private IntakeWheels() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setRaw(double percent) {
		
	}
	
}
