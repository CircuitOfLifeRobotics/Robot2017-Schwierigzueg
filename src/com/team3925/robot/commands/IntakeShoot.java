package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterLoader;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class IntakeShoot extends CommandPerformOnce {

	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_intake_shoot", 0.4);
	}

	private static IntakeShoot instance;

	public static IntakeShoot getInstance() {
		return instance == null ? instance = new IntakeShoot() : instance;
	}

	private IntakeShoot() {
		super(() -> ShooterLoader.getInstance().set(RIOConfigs.getInstance().getConfigOrAdd("pwr_intake_shoot", 0.4)));
		requires(ShooterLoader.getInstance());
	}

}
