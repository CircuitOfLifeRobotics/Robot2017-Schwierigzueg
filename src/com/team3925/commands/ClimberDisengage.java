package com.team3925.commands;

import com.team3925.subsystems.ClimberShifting;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberDisengage extends Command {

	private ClimberShifting climberShifting;
	private static ClimberDisengage instance;

	public static ClimberDisengage getInstance() {
		if (instance == null)
			instance = new ClimberDisengage();
		return instance;
	}

	private ClimberDisengage() {
		climberShifting = ClimberShifting.getInstance();
		requires(climberShifting);
	}

	@Override
	protected void initialize() {
		climberShifting.setClimber(false);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
