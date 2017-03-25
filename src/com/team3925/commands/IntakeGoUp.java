package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeGoUp extends Command {

	private Intake intake;

	private static IntakeGoUp instance;

	public static IntakeGoUp getInstance() {
		if (instance == null)
			instance = new IntakeGoUp();
		return instance;
	}

	private IntakeGoUp() {
		intake = Intake.getInstance();
		requires(intake);
	}

	@Override
	protected void initialize() {
		intake.setUp();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
