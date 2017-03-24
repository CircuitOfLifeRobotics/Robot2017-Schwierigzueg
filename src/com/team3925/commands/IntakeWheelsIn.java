package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsIn extends Command {
	
	private Intake intake;
	
	private static IntakeWheelsIn instance;
	
	public static IntakeWheelsIn getInstance() {
		if (instance==null)
			instance = new IntakeWheelsIn();
		return instance;
	}
	
	private IntakeWheelsIn() {
		intake = Intake.getInstance();
		requires(intake);
	}
	
	@Override
	protected void initialize() {
		intake.setWheels(1);
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
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
