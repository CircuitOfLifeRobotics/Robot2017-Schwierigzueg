package com.team3925.util.ditto;

import java.util.LinkedHashMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class RepeatCommand<T> extends Command {
	private LinkedHashMap<Double, T> record, staticRecord;
	private Repeatable<T> repeatable;
	private double startTime;
	private double currTime;
	private static final double PRE_TOLERANCE = 0.0;
	private static final double POST_TOLERANCE = 0.1;

	public RepeatCommand(Repeatable<T> repeatable) {
		this.repeatable = repeatable;
		staticRecord = new LinkedHashMap<>();
		record = new LinkedHashMap<>();
		startTime = 0;
	}

	public RepeatCommand(Repeatable<T> repeatable, LinkedHashMap<Double, T> record) {
		this.repeatable = repeatable;
		staticRecord = new LinkedHashMap<>(record);
		this.record = new LinkedHashMap<>(staticRecord);
		startTime = 0;
	}

	public void initialize() {
		record.clear();
		record.putAll(staticRecord);
	}

	public void execute() {
		currTime = Timer.getFPGATimestamp() - startTime;
		record.keySet().removeIf(t -> currTime - t > POST_TOLERANCE);
		record.keySet().stream().filter(t -> t - currTime < PRE_TOLERANCE).findFirst()
				.ifPresent(t -> repeatable.repeat(record.remove(t)));
	}

	public boolean isFinished() {
		return record.isEmpty();
	}

	@Override
	public void start() {
		startWithRecord();
	}

	public void startFromZero() {
		startTime = Timer.getFPGATimestamp();
		super.start();
	}

	public void startFromCurrent() {
		startTime = 0;
		super.start();
	}

	public void startWithRecord() {
		startTime = staticRecord.keySet().stream().findFirst().orElse(Timer.getFPGATimestamp());
		super.start();
	}

	public void setNextRecord(LinkedHashMap<Double, T> record) {
		staticRecord = new LinkedHashMap<>(record);
	}
}