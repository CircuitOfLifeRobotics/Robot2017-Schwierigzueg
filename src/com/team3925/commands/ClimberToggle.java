package com.team3925.commands;

import com.team3925.subsystems.ClimberShifting;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberToggle extends Command {

	private ClimberShifting climberShifting;


	public ClimberToggle() {
		climberShifting = ClimberShifting.getInstance();
		requires(climberShifting);
	}

	@Override
	protected void initialize() {
		climberShifting.setClimber(!climberShifting.getClimberEngaged());
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
