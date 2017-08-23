package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterLoader;
import com.team3925.util.CommandPerformOnce;

public class IntakeShoot extends CommandPerformOnce {

	private static IntakeShoot instance;

	public static IntakeShoot getInstance() {
		return instance == null ? instance = new IntakeShoot() : instance;
	}

	private IntakeShoot() {
		super(() -> ShooterLoader.getInstance().set(-1));
		requires(ShooterLoader.getInstance());
	}

}
