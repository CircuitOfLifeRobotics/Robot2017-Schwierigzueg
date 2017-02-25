package com.team3925.team3925.robot.util;

import java.util.HashMap;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.tables.ITable;

public class Stopwatch implements NamedSendable {
	
	private static HashMap<String, Double> startTimes;
	private static Stopwatch instance;
	private static ITable itable;
	
	public static Stopwatch getInstance() {
		return instance==null ? instance = new Stopwatch() : instance;
	}
	
	private Stopwatch() {
	}
	
	public static boolean start(String timer) {
		boolean hasKey = startTimes.containsKey(timer);
		startTimes.put(timer, Timer.getFPGATimestamp());
		itable.putNumber(timer, 0);
		return hasKey;
	}
	
	public static boolean lap(String timer) {
		boolean hasKey = startTimes.containsKey(timer);
		if (hasKey)
			itable.putNumber(timer, Timer.getFPGATimestamp()-startTimes.get(timer));
		return hasKey;
	}
	
	public static boolean remove(String timer) {
		boolean hasKey = startTimes.containsKey(timer);
		if (hasKey)
			startTimes.remove(timer);
		return hasKey;
	}

	@Override
	public void initTable(ITable subtable) {
		
	}

	@Override
	public ITable getTable() {
		return itable;
	}

	@Override
	public String getSmartDashboardType() {
		return "Stopwatch";
	}

	@Override
	public String getName() {
		return "Stopwatch";
	}
	
}
