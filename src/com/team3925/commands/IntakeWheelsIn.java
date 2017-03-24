package com.team3925.commands;

import com.team3925.subsystems.IntakeWheels;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsIn extends Command {
	
	private IntakeWheels intakeWheels;
	
	private static IntakeWheelsIn instance;
	
	public static IntakeWheelsIn getInstance() {
		if (instance==null)
			instance = new IntakeWheelsIn();
		return instance;
	}
	
	private IntakeWheelsIn() {
		intakeWheels = IntakeWheels.getInstance();
		requires(intakeWheels);
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
