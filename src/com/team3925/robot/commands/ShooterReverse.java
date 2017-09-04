package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class ShooterReverse extends CommandPerformOnce {
	
	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_shooter_reverse", -0.4);
	}
	
	private static ShooterReverse instance;

	public static ShooterReverse getInstance() {
		return instance == null ? instance = new ShooterReverse() : instance;
	}
	
	private ShooterReverse() {
		super(()->ShooterFlyWheel.getInstance().setShooter(RIOConfigs.getInstance().getConfigOrAdd("pwr_shooter_reverse", -0.4)));
		requires(ShooterFlyWheel.getInstance());
	}
	
}
