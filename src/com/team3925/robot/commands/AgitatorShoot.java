package com.team3925.robot.commands;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.util.CommandPerformOnce;

public class AgitatorShoot extends CommandPerformOnce {
	
	private static AgitatorShoot instance;

	public static AgitatorShoot getInstance() {
		return instance == null ? instance = new AgitatorShoot() : instance;
	}
	
	private AgitatorShoot() {
		super(()->Agitator.getInstance().set(1));
	}
	
}
