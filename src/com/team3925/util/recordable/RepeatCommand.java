package com.team3925.util.recordable;

import java.io.Serializable;
import java.util.Iterator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RepeatCommand<T> extends Command {

	private Recordable<T> recordable;
	private Record<T> recordStatic, record;
	private Iterator<Double> iterator;
	private double currentTime, snapshotTime, startTime;
	private double preTolerance, postTolerance;

	public RepeatCommand(Recordable<T> recordable, double preTolerance, double postTolerance) {
		this(recordable, preTolerance, postTolerance, true);
	}

	public RepeatCommand(Recordable<T> recordable, double preTolerance, double postTolerance, boolean doRequire) {
		this.recordable = recordable;
		this.preTolerance = preTolerance;
		this.postTolerance = postTolerance;
		if (doRequire && recordable instanceof Subsystem)
			requires((Subsystem) recordable);
		startTime = 0;
	}

	/**
	 * This command will automatically require {@link #recordable} if it is of
	 * type {@link Subsystem}.
	 * 
	 * @param recordable
	 * @param record
	 * @param preTolerance
	 * @param postTolerance
	 */
	public RepeatCommand(Recordable<T> recordable, Record<T> record, double preTolerance, double postTolerance) {
		this(recordable, record, preTolerance, postTolerance, true);
	}

	/**
	 * This command will require {@link #recordable} if
	 * {@link #doesRequire(Subsystem)} is true and it is of type
	 * {@link Subsystem}
	 * 
	 * @param recordable
	 * @param record
	 * @param preTolerance
	 * @param postTolerance
	 * @param doRequire
	 */
	public RepeatCommand(Recordable<T> recordable, Record<T> record, double preTolerance, double postTolerance,
			boolean doRequire) {
		this.recordable = recordable;
		this.recordStatic = record;
		this.record = recordStatic.clone();
		iterator = record.keySet().iterator();
		this.preTolerance = preTolerance;
		this.postTolerance = postTolerance;
		if (doRequire && recordable instanceof Subsystem)
			requires((Subsystem) recordable);
		startTime = 0;
	}

	@Override
	protected void initialize() {
		if (record == null)
			cancel();
		startTime = Timer.getFPGATimestamp();
		snapshotTime = iterator.next();
	}

	@Override
	protected void execute() {
		if (record != null) {
			while (iterator.hasNext()) {
				currentTime = Timer.getFPGATimestamp() - startTime;
				// if the snapshot is too far in the past, delete it and
				// immediately
				// check the next snapshot in the past
				if (currentTime - snapshotTime > postTolerance) {
					System.out.println("LATE BY "+(currentTime-snapshotTime)+" SECONDS");
					iterator.remove();
					snapshotTime = iterator.next();
					continue;
				}
				// if the snapshot is near enough to the current time (even
				// though
				// it's in the future), JUST DOOOOO IT.
				if (snapshotTime - currentTime <= preTolerance) {
					System.out.println("DONE " + (snapshotTime-currentTime) + " SECONDS EARLY");
					recordable.repeat(record.get(snapshotTime));
					iterator.remove();
					snapshotTime = iterator.next();
				}
				// unless the snapshot was in the past, end execution
				return;
			}
		}
	}

	@Override
	protected boolean isFinished() {
		if (record == null)
			return true;
		return !iterator.hasNext();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	/**
	 * Stops execution and resets the recording (to the one given by the
	 * constructor originally or set by another method).
	 */
	public boolean reset() {
		cancel();
		if (recordStatic != null) {
			record = recordStatic.clone();
			iterator = record.keySet().iterator();
			return true;
		}
		return false;
	}

	/**
	 * Note: this does not cancel the command or set the next record. You should
	 * call {@link #reset()} in order to do that.
	 * 
	 * @param record
	 */
	public void set(Record<T> record) {
		recordStatic = record;
	}
}
