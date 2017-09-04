package com.team3925.robot.commands;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class AgitatorReverse extends CommandPerformOnce {
	
	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_agitator_reverse", -0.4);
	}
	
	private static AgitatorReverse instance;

	public static AgitatorReverse getInstance() {
		return instance == null ? instance = new AgitatorReverse() : instance;
	}

	private AgitatorReverse() {
		super(() -> Agitator.getInstance().set(RIOConfigs.getInstance().getConfigOrAdd("pwr_agitator_reverse", -0.4)));
		requires(Agitator.getInstance());
	}

}
