package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberShifting extends Subsystem {
	
	private static ClimberShifting instance;
	
	public static ClimberShifting getInstance() {
		if (instance==null)
			instance = new ClimberShifting();
		return instance;
	}
	
	private ClimberShifting() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setClimberEngaged() {
		
	}
	
	public void setClimberDisengaged() {
		
	}
	
	public void setClimber(boolean engaged) {
		if (engaged)
			setClimberEngaged();
		else
			setClimberDisengaged();
	}

}
