package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.util.CommandPerformOnce;

public class ShooterOff extends CommandPerformOnce {

	private static ShooterOff instance;

	public static ShooterOff getInstance() {
		return instance == null ? instance = new ShooterOff() : instance;
	}

	private ShooterOff() {
		super(() -> ShooterFlyWheel.getInstance().setShooter(0));
		requires(ShooterFlyWheel.getInstance());
	}

}
