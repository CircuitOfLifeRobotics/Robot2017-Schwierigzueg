package com.team3925.commands;

import com.team3925.subsystems.ClimberShifting;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberEngage extends Command {

	private ClimberShifting climberShifting;

	private static ClimberEngage instance;

	public static ClimberEngage getInstance() {
		if (instance == null)
			instance = new ClimberEngage();
		return instance;
	}

	private ClimberEngage() {
		climberShifting = ClimberShifting.getInstance();
		requires(climberShifting);
	}

	@Override
	protected void initialize() {
		climberShifting.setClimber(true);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
