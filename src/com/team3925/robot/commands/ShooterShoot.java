package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class ShooterShoot extends CommandPerformOnce {
	
	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_shooter_shoot", 0.4);
	}
	
	private static ShooterShoot instance;

	public static ShooterShoot getInstance() {
		return instance == null ? instance = new ShooterShoot() : instance;
	}
	
	private ShooterShoot() {
		super(()->ShooterFlyWheel.getInstance().setShooter(RIOConfigs.getInstance().getConfigOrAdd("pwr_shooter_shoot", 0.4)));
		requires(ShooterFlyWheel.getInstance());
	}
	
}
