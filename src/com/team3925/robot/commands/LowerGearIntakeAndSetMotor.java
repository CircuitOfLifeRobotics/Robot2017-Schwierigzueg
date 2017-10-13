package com.team3925.robot.commands;

import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class LowerGearIntakeAndSetMotor extends Command {

	public LowerGearIntakeAndSetMotor() {
		requires(GearIntake.getInstance());
	}

	protected void initialize() {
		GearIntake.getInstance().setSolenoid(false);
	}

	protected boolean isFinished() {
		return true;
	}
}
