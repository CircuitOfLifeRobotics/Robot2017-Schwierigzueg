
package com.team3925.team3925.robot.util;

import edu.wpi.first.wpilibj.Joystick;

public enum ControlMode {
	
	WHEEL_STICK_CUSTOM, WHEEL_STICK_XBOX // no launchpad mode
	,XBOX_CUSTOM // no wheel mode
	,XBOX_STICK // no launchpad, no wheel mode
	,XBOX // debug mode
	,WHEEL // racing mode!
	,NONE
	;
	
	public static boolean isWheel(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("thrustmaster");
	}
	
	public static boolean isStick(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("attack 3");
	}
	
	public static boolean isXbox(Joystick stick) {
		return stick.getIsXbox() || stick.getName().toLowerCase().contains("xbox");
	}
	
}
