package com.team3925.robot;

/*
 * POPULATED: TRUE
 * WHEN: 3/7/17
 * TESTED: FALSE
 * WHEN: 
 */

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public final static int TALON_ID_INTAKE;
	public final static int TALON_ID_DRIVE_LEFT_A;
	public final static int TALON_ID_DRIVE_LEFT_B;
	public final static int TALON_ID_DRIVE_LEFT_C;
	public final static int TALON_ID_DRIVE_RIGHT_A;
	public final static int TALON_ID_DRIVE_RIGHT_B;
	public final static int TALON_ID_DRIVE_RIGHT_C;

	public final static int SOLENOID_PORT_A_CLIMB;
	public final static int SOLENOID_PORT_B_CLIMB;

	public final static int SOLENOID_PORT_A_SHIFT;
	public final static int SOLENOID_PORT_B_SHIFT;

	public final static int SOLENOID_PORT_A_INTAKE;
	public final static int SOLENOID_PORT_B_INTAKE;

	static {
		// TALON ID CONSTANTS
		TALON_ID_DRIVE_LEFT_A = 40;
		TALON_ID_DRIVE_LEFT_B = 32;
		TALON_ID_DRIVE_LEFT_C = 2;
		TALON_ID_DRIVE_RIGHT_A = 55;
		TALON_ID_DRIVE_RIGHT_B = 10;
		TALON_ID_DRIVE_RIGHT_C = 11;
		TALON_ID_INTAKE = 6;

		// SOLENOID PORTS
		SOLENOID_PORT_A_SHIFT = 0;
		SOLENOID_PORT_B_SHIFT = 1;
		SOLENOID_PORT_A_CLIMB = 2;
		SOLENOID_PORT_B_CLIMB = 3;
		SOLENOID_PORT_A_INTAKE = 4;
		SOLENOID_PORT_B_INTAKE = 5;
	}

	private RobotMap() {
	}

}
