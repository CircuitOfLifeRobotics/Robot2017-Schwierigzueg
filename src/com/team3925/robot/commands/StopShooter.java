package com.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StopShooter extends CommandGroup {

	public StopShooter() {
		addParallel(ShooterOff.getInstance());
		addParallel(IntakeOff.getInstance());
		addParallel(AgitatorOff.getInstance());
	}
}
