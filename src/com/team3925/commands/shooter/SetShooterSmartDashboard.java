package com.team3925.commands.shooter;

import com.team3925.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

public class SetShooterSmartDashboard extends Command {
	public SetShooterSmartDashboard() {
		requires(Shooter.getInstance());
	}

	protected void initialize() {
		Shooter.getInstance().setShooterVelSmartDashboard();
	}

	protected boolean isFinished() {
		return true;
	}
}
