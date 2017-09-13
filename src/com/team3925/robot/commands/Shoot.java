package com.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Shoot extends CommandGroup {

	public Shoot() {
		addParallel(new ShootFuelStatic());
		// addParallel(); TODO: Add Aim Command
		addSequential(new WaitCommand(1));
		addSequential(new LoadFuel());
	}
}
