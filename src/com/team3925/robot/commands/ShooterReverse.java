package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.CommandPerformOnce;

public class ShooterReverse extends CommandPerformOnce {
	
	private static ShooterReverse instance;

	public static ShooterReverse getInstance() {
		return instance == null ? instance = new ShooterReverse() : instance;
	}
	
	private ShooterReverse() {
		super(()->ShooterFlyWheel.getInstance().setShooter(-1));
		requires(ShooterFlyWheel.getInstance());
	}
	
}
