package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsOff extends Command {
	
	private Intake intake;
	
	private static IntakeWheelsOff instance;
	
	public static IntakeWheelsOff getInstance() {
		if (instance==null)
			instance = new IntakeWheelsOff();
		return instance;
	}
	
	private IntakeWheelsOff() {
		intake = Intake.getInstance();
		requires(intake);
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
