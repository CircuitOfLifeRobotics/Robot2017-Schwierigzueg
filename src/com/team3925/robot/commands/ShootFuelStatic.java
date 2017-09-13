package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.RIOConfigs;

import edu.wpi.first.wpilibj.command.Command;

public class ShootFuelStatic extends Command {

	private double power;
	private boolean getFromConfigs;

	public ShootFuelStatic(double power) {
		requires(ShooterFlyWheel.getInstance());
		this.power = power;
		getFromConfigs = false;
	}

	public ShootFuelStatic() {
		requires(ShooterFlyWheel.getInstance());
		power = RIOConfigs.getInstance().getConfigOrAdd("SHOOTER POWER", 1d);
		getFromConfigs = true;
	}

	@Override
	protected void initialize() {
		if (getFromConfigs) {
			RIOConfigs.getInstance().reloadConfigs();
			power = RIOConfigs.getInstance().getConfigOrAdd("SHOOTER POWER", power);
		}
		ShooterFlyWheel.getInstance().setShooter(power);
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
