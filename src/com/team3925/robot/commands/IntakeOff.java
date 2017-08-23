package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterLoader;
import com.team3925.util.CommandPerformOnce;

public class IntakeOff extends CommandPerformOnce {

	private static IntakeOff instance;

	public static IntakeOff getInstance() {
		return instance == null ? instance = new IntakeOff() : instance;
	}

	private IntakeOff() {
		super(() -> ShooterLoader.getInstance().set(0));
		requires(ShooterLoader.getInstance());
	}

}
