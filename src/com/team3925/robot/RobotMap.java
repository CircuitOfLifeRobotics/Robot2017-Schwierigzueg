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
	public final static int TALON_ID_TURRET;
	public final static int TALON_ID_HOOD;
	public final static int TALON_ID_FLYWHEEL;
	public final static int TALON_ID_INTAKE;
	public final static int TALON_ID_ELEVATOR_LOWER;
	public final static int TALON_ID_ELEVATOR_UPPER;
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
	
	public static final int GEAR_SENSOR_PORT;
	
	public static final int LED_PORT_RING;
	public static final int LED_PORT_BLUE_B;
	public static final int LED_PORT_BLUE_A;
	public static final int LED_PORT_RED_B;
	public static final int LED_PORT_RED_A;
	
	static {
		// TALON ID CONSTANTS
//		TALON_ID_DRIVE_LEFT_A = 3;
//		TALON_ID_DRIVE_LEFT_B = 10;
//		TALON_ID_DRIVE_LEFT_C = 11;
//		TALON_ID_DRIVE_RIGHT_A = 59;
//		TALON_ID_DRIVE_RIGHT_B = 58;
//		TALON_ID_DRIVE_RIGHT_C = 2;
//		TALON_ID_ELEVATOR_UPPER = 4;
//		TALON_ID_ELEVATOR_LOWER = 5;
//		TALON_ID_INTAKE = 6;
//		TALON_ID_FLYWHEEL = 7;
//		TALON_ID_HOOD = 8;
//		TALON_ID_TURRET = 9;
		
		TALON_ID_DRIVE_LEFT_A = 40;//Good
		TALON_ID_DRIVE_LEFT_B = 32;
		TALON_ID_DRIVE_LEFT_C = 2;//Good
		TALON_ID_DRIVE_RIGHT_A = 55;
		TALON_ID_DRIVE_RIGHT_B = 10;//Good
		TALON_ID_DRIVE_RIGHT_C = 11; //Invert
		TALON_ID_ELEVATOR_UPPER = 4;
		TALON_ID_ELEVATOR_LOWER = 5;
		TALON_ID_INTAKE = 6;
		TALON_ID_FLYWHEEL = 7;
		TALON_ID_HOOD = 8;
		TALON_ID_TURRET = 9;
		
		GEAR_SENSOR_PORT = 0;
		
		LED_PORT_RING = 0;
		LED_PORT_BLUE_B = 1;
		LED_PORT_BLUE_A = 2;
		LED_PORT_RED_B = 3;
		LED_PORT_RED_A = 4;
		
		// SOLENOID PORTS
		SOLENOID_PORT_A_SHIFT = 0;
		SOLENOID_PORT_B_SHIFT = 1;
		SOLENOID_PORT_A_CLIMB = 2;
		SOLENOID_PORT_B_CLIMB = 3;
	}
	
	private RobotMap() {}
	
}
