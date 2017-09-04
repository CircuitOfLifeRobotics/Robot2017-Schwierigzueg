package com.team3925.robot.commands;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.util.CommandPerformOnce;
import com.team3925.util.RIOConfigs;

public class AgitatorShoot extends CommandPerformOnce {
	
	static {
		RIOConfigs.getInstance().getConfigOrAdd("pwr_agitator_shoot", 0.4);
	}
	
	private static AgitatorShoot instance;

	public static AgitatorShoot getInstance() {
		return instance == null ? instance = new AgitatorShoot() : instance;
	}
	
	private AgitatorShoot() {
		super(()->Agitator.getInstance().set(RIOConfigs.getInstance().getConfigOrAdd("pwr_agitator_shoot", 0.4)));
		requires(Agitator.getInstance());
	}
	
}
