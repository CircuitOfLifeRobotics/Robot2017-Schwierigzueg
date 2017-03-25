package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeGoDown extends Command {
	
	private Intake intake;
	
	private static IntakeGoDown instance;
	
	public static IntakeGoDown getInstance() {
		if (instance==null)
			instance = new IntakeGoDown();
		return instance;
	}
	
	private IntakeGoDown() {
		intake = Intake.getInstance();
		requires(intake);
	}
	
	@Override
	protected void initialize() {
		intake.setDown();
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
