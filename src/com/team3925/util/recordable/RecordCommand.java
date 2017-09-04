package com.team3925.util.recordable;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RecordCommand<T> extends Command {

	private Recordable<T> recordable;
	private Record<T> record;
	private double startTime;

	public RecordCommand(Recordable<T> recordable) {
		this.recordable = recordable;
		record = new Record<>();
	}

	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		record.putIfNew(Timer.getFPGATimestamp()-startTime, recordable.record());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	/**
	 * Stops execution and resets the recording.
	 */
	public void reset() {
		cancel();
		record.clear();
	}

	/**
	 * Gets a copy of the recording.
	 */
	public Record<T> get() {
		return record.clone();
	}
}
