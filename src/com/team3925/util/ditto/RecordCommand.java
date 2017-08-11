package com.team3925.util.ditto;

import java.util.LinkedHashMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RecordCommand<T> extends Command {
	private LinkedHashMap<Double, T> record, staticRecord;
	private Recordable<T> recordable;
	private double startTime;
	private double currTime;
	private T currAction, lastAction;

	public RecordCommand(Recordable<T> recordable) {
		this.recordable = recordable;
		staticRecord = new LinkedHashMap<>();
		record = new LinkedHashMap<>();
		startTime = 0;
	}

	@Override
	protected void initialize() {
		lastAction = null;
	}

	public void execute() {
		currTime = Timer.getFPGATimestamp() - startTime;
		if (lastAction == null || !lastAction.equals(currAction = recordable.record()))
			record.put(currTime, lastAction = currAction);
	}

	public boolean isFinished() {
		return false;
	}

	public void interrupted() {
		staticRecord.clear();
		staticRecord.putAll(record);
	}

	@Override
	public void start() {
		startFreshFromZero();
	}

	protected boolean startConcat() {
		if (!isRunning()) {
			super.start();
			return true;
		}
		return false;
	}

	public boolean startConcatFromCurrent() {
		startTime = 0;
		return startConcat();
	}

	public boolean startConcatFromZero() {
		startTime = Timer.getFPGATimestamp();
		return startConcat();
	}

	public boolean startConcatFromLast() {
		startTime = Timer.getFPGATimestamp() - currTime;
		return startConcat();
	}

	protected boolean startFresh() {
		record.clear();
		return startConcat();
	}

	public boolean startFreshFromCurrent() {
		startTime = 0;
		return startFresh();
	}

	public boolean startFreshFromZero() {
		startTime = Timer.getFPGATimestamp();
		return startFresh();
	}

	public LinkedHashMap<Double, T> getFinishedRecord() {
		return new LinkedHashMap<>(staticRecord);
	}

}