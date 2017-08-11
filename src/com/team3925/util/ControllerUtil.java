package com.team3925.util;

import edu.wpi.first.wpilibj.Spark;

public class ControllerUtil {

	public static void configSpark(double expiration, boolean inverted, Spark... sparks) {
		for (Spark spark : sparks) {
			configSpark(expiration, inverted, spark);
		}
	}

	public static void configSpark(double expiration, boolean inverted, Spark spark) {
		spark.setExpiration(expiration);
		spark.setInverted(inverted);
	}

}
