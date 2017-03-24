package com.team3925.commands;

import com.team3925.subsystems.IntakePiston;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeGoDown extends Command {
	
	private IntakePiston intakePiston;
	
	private static IntakeGoDown instance;
	
	public static IntakeGoDown getInstance() {
		if (instance==null)
			instance = new IntakeGoDown();
		return instance;
	}
	
	private IntakeGoDown() {
		intakePiston = IntakePiston.getInstance();
		requires(intakePiston);
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
