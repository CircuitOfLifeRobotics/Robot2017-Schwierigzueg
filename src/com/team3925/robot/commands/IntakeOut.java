package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterLoader;
import com.team3925.util.CommandPerformOnce;

public class IntakeOut extends CommandPerformOnce {

	private static IntakeOut instance;

	public static IntakeOut getInstance() {
		return instance == null ? instance = new IntakeOut() : instance;
	}

	private IntakeOut() {
		super(() -> ShooterLoader.getInstance().set(1));
		requires(ShooterLoader.getInstance());
	}

}
