package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.CommandPerformOnce;

public class ShooterShoot extends CommandPerformOnce {
	
	private static ShooterShoot instance;

	public static ShooterShoot getInstance() {
		return instance == null ? instance = new ShooterShoot() : instance;
	}
	
	private ShooterShoot() {
		super(()->ShooterFlyWheel.getInstance().setShooter(1));
		requires(ShooterFlyWheel.getInstance());
	}
	
}
