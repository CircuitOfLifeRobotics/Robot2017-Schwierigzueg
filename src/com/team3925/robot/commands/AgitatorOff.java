package com.team3925.robot.commands;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.util.CommandPerformOnce;

public class AgitatorOff extends CommandPerformOnce {
	
	private static AgitatorOff instance;

	public static AgitatorOff getInstance() {
		return instance == null ? instance = new AgitatorOff() : instance;
	}
	
	private AgitatorOff() {
		super(()->Agitator.getInstance().set(0));
	}
	
}
