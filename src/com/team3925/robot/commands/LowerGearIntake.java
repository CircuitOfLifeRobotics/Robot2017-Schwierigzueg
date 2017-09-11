package com.team3925.robot.commands;

import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class LowerGearIntake extends Command {

	public LowerGearIntake() {
		requires(GearIntake.getInstance());
	}

	protected void initialize() {
		GearIntake.getInstance().setSolenoid(false);
		GearIntake.getInstance().setMotor();
	}

	protected boolean isFinished() {
		return true;
	}
}
