package com.team3925.robot.commands;

import static com.team3925.robot.RobotMap.CONFIGS;

import com.team3925.robot.subsystems.ShooterFlyWheel;

import edu.wpi.first.wpilibj.command.Command;

public class ShootFuelStatic extends Command {

	public ShootFuelStatic() {
		requires(ShooterFlyWheel.getInstance());
	}

	@Override
	protected void initialize() {
		ShooterFlyWheel.getInstance().setShooter(CONFIGS.getConfigOrAdd("power default shooter", 1));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		interrupted();
	}

	@Override
	protected void interrupted() {
		ShooterFlyWheel.getInstance().setShooter(0);
	}

}
