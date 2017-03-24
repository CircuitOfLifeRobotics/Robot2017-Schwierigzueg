package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakePiston extends Subsystem {
	
	private static IntakePiston instance;
	
	public static IntakePiston getInstance() {
		if (instance==null)
			instance = new IntakePiston();
		return instance;
	}
	
	private IntakePiston() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDown() {
		
	}
	
	public void setUp() {
		
	}
	
	public void set(boolean isUp) {
		if (isUp)
			setUp();
		else
			setDown();
	}
	
}
