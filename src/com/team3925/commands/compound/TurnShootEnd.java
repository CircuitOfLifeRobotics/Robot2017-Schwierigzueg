package com.team3925.commands.compound;

import com.team3925.commands.feeder.SetFeeder;
import com.team3925.commands.shooter.SetShooter;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TurnShootEnd extends CommandGroup {
	public TurnShootEnd() {
		addSequential(new SetShooter(0));
		addSequential(new SetFeeder(0));
	}
}
