package com.team3925.robot.commands;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.util.CommandPerformOnce;

public class AgitatorReverse extends CommandPerformOnce {

	private static AgitatorReverse instance;

	public static AgitatorReverse getInstance() {
		return instance == null ? instance = new AgitatorReverse() : instance;
	}

	private AgitatorReverse() {
		super(() -> Agitator.getInstance().set(-1));
	}

}
