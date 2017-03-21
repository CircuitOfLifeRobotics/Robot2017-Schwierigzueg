package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	
	private static Climber instance;
	
	public static Climber getInstance() {
		if (instance==null)
			instance = new Climber();
		return instance;
	}
	
	private Climber() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
