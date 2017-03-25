package com.team3925.commands.intake;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeGoUp extends Command {

	private Intake intake;

	public IntakeGoUp() {
		intake = Intake.getInstance();
		requires(intake);
	}

	@Override
	protected void initialize() {
		intake.setUp();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
