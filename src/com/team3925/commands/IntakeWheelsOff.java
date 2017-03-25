
package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsOff extends Command {

	private Intake intake;

	public IntakeWheelsOff() {
		intake = Intake.getInstance();
		requires(intake);
	}

	@Override
	protected void initialize() {
		intake.setWheels(0);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
