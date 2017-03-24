package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	
	private static Intake instance;
	
	public static Intake getInstance() {
		if (instance==null)
			instance = new Intake();
		return instance;
	}
	
	private Intake() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setWheels(double percent) {
		
	}
	
	public void setDown() {
		
	}
	
	public void setUp() {
		
	}
	
	public void setPiston(boolean isUp) {
		if (isUp)
			setUp();
		else
			setDown();
	}
	
}
