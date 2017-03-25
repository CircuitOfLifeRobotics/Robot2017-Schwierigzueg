package com.team3925.commands;

import com.team3925.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class SetShooter extends Command {
	double shooterSpeed;

	public SetShooter(double speed) {
		requires(Shooter.getInstance());
		shooterSpeed = speed;
	}

	protected void initialize() {
		Shooter.getInstance().setShooterVel(shooterSpeed);
	}

	protected boolean isFinished() {
		return true;
	}
}
