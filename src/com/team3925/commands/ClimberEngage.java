package com.team3925.commands;

import com.team3925.subsystems.ClimberShifting;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberEngage extends Command {
	
	private ClimberShifting climberShifting;
	
	private static ClimberEngage instance;
	
	public static ClimberEngage getInstance() {
		if (instance==null)
			instance = new ClimberEngage();
		return instance;
	}
	
	private ClimberEngage() {
		climberShifting = ClimberShifting.getInstance();
		requires(climberShifting);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}
	
}
