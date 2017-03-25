package com.team3925.commands.intake;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeGoDown extends Command {

	private Intake intake;

	public IntakeGoDown() {
		intake = Intake.getInstance();
		requires(intake);
	}

	@Override
	protected void initialize() {
		intake.setDown();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
