package com.team3925.util;

public class MiscMath {

	public static double capRange(double min, double max, double value) {
		return Math.min(max, Math.max(min, value));
	}

}
