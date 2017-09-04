package com.team3925.util;

import java.util.function.Function;

public class TrapezoidalRecord implements Function<Double, Double> {

	private double timeStartIntermediate, timeStartSlowdown, tEnd;
	private double intermediate, mStart, bStart, mEnd, bEnd, start, end;

	public TrapezoidalRecord(double start, double end, double intermediate, double startRate, double endRate,
			double time, double dt) {
		timeStartIntermediate = Math.abs((intermediate - start) / startRate);
		timeStartSlowdown = time - Math.abs((intermediate - end) / endRate);
		tEnd = time;
		this.intermediate = intermediate;
		this.start = start;
		this.end = end;
		mStart = startRate;
		mEnd = endRate;
		bStart = start;
		bEnd = end - endRate * time;
	}

	@Override
	public Double apply(Double t) {
		if (t > tEnd)
			return end;
		if (t > timeStartIntermediate)
			return bEnd + mEnd * t;
		if (t > timeStartIntermediate)
			return intermediate;
		if (t > 0)
			return bStart + mStart * t;
		return start;
	}

}
