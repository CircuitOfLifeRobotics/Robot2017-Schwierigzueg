package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;

import edu.wpi.first.wpilibj.command.Command;

public class SetShooterDynamic extends Command {
	
	public SetShooterDynamic() {
		requires(ShooterFlyWheel.getInstance());
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	
	
}
