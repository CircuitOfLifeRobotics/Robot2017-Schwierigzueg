
package com.team3925.commands;

import com.team3925.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeWheelsOff extends Command {

	private Intake intake;

	private static IntakeWheelsOff instance;

	public static IntakeWheelsOff getInstance() {
		if (instance == null)
			instance = new IntakeWheelsOff();
		return instance;
	}

	private IntakeWheelsOff() {
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
