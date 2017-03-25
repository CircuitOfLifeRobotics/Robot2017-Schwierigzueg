
package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsOut extends Command {

	private Intake intake;

	public IntakeWheelsOut() {
		intake = Intake.getInstance();
		requires(intake);
	}

	@Override
	protected void initialize() {
		intake.setWheels(-1);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
