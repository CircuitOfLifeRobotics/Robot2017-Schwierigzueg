package com.team3925.robot.commands;

import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class GearOutput extends Command {

	public GearOutput() {
		requires(GearIntake.getInstance());
	}

	protected void initialize() {
		GearIntake.getInstance().setMotor(-1);
	}

	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		GearIntake.getInstance().setMotor(0);
	}
}
