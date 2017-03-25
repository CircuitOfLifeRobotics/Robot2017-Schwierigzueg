package com.team3925.commands.shooter;

import com.team3925.commands.Vision;
import com.team3925.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class SetShooterDynamic extends Command {
	public SetShooterDynamic() {
		requires(Shooter.getInstance());
	}

	protected void initialize() {
		Shooter.getInstance().setShooterVel(Vision.getInstance().getSpeed());
	}

	protected boolean isFinished() {
		return true;
	}
}
