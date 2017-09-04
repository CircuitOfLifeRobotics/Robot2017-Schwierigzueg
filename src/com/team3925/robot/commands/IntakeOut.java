package com.team3925.robot.commands;

import com.team3925.robot.subsystems.ShooterLoader;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class IntakeOut extends CommandPerformOnce {
	
	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_intake_reverse", -0.4);
	}
	
	private static IntakeOut instance;

	public static IntakeOut getInstance() {
		return instance == null ? instance = new IntakeOut() : instance;
	}

	private IntakeOut() {
		super(() -> ShooterLoader.getInstance().set(RIOConfigs.getInstance().getConfigOrAdd("pwr_intake_reverse", -0.4)));
		requires(ShooterLoader.getInstance());
	}

}
