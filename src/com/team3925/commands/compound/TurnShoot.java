package com.team3925.commands.compound;

import com.team3925.commands.Timeout;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.driveTrain.GyroTurnDynamic;
import com.team3925.commands.feeder.SetFeeder;
import com.team3925.commands.shooter.SetShooterDynamic;
import com.team3925.commands.shooter.UpdateShooterSpeed;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TurnShoot extends CommandGroup {
	public TurnShoot(double firstTurn) {
		addSequential(new GyroTurn(firstTurn));

		addSequential(new Timeout(.1));
		addSequential(new GyroTurnDynamic(),1.5);
		addSequential(new Timeout(.3));
		addSequential(new GyroTurnDynamic(),1);
		addSequential(new SetShooterDynamic());
		addSequential(new Timeout(1));
		addSequential(new SetFeeder(-175));
		addSequential(new SetShooterDynamic());
	}
}
