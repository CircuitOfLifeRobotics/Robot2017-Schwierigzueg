package com.team3925.robot;

import com.team3925.util.RIOConfigs;

public class RobotMap {

	public static final int PORT_DRIVE_LEFT_A, PORT_DRIVE_LEFT_B, PORT_DRIVE_RIGHT_A, PORT_DRIVE_RIGHT_B;
	public static final int PORT_SHOOTER_A, PORT_SHOOTER_B;
	public static final int PORT_LOADER;
	public static final int PORT_AGITATOR;
	public static final int PORT_CLIMBER_A, PORT_CLIMBER_B;
	public static final int PORT_INTAKE_MOTOR;
	public static final int PORT_INTAKE_SOLENOID_FORWARD, PORT_INTAKE_SOLENOID_REVERSE;

	public static final boolean POLARITY_DRIVE_LEFT_A, POLARITY_DRIVE_LEFT_B, POLARITY_DRIVE_RIGHT_A,
			POLARITY_DRIVE_RIGHT_B;
	public static final boolean POLARITY_SHOOTER_A, POLARITY_SHOOTER_B;
	public static final boolean POLARITY_LOADER;
	public static final boolean POLARITY_AGITATOR;
	public static final boolean POLARITY_CLIMBER_A, POLARITY_CLIMBER_B;

	public static final double INTAKE_PICKUP_TIMEOUT;
	public static final double INTAKE_MOTOR_SPEED;
	public static double INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD;

	public static RIOConfigs CONFIGS;

	static {
		// CONFIGS = RIOConfigs.getInstance(System.getProperty("user.home") +
		// "/preferences/Schwierigzeug_comp.txt");
		CONFIGS = RIOConfigs.getInstance();

		PORT_DRIVE_LEFT_A = CONFIGS.getConfigOrAdd("port_drive_left_a", 1);
		PORT_DRIVE_LEFT_B = CONFIGS.getConfigOrAdd("port_drive_left_b", 2);
		PORT_DRIVE_RIGHT_A = CONFIGS.getConfigOrAdd("port_drive_right_a", 12);
		PORT_DRIVE_RIGHT_B = CONFIGS.getConfigOrAdd("port_drive_right_b", 11);

		PORT_SHOOTER_A = CONFIGS.getConfigOrAdd("port_shooter_flywheel_a", 3);
		PORT_SHOOTER_B = CONFIGS.getConfigOrAdd("port_shooter_flywheel_b", 9);

		PORT_LOADER = CONFIGS.getConfigOrAdd("port_shooter_loader", 5);

		PORT_AGITATOR = CONFIGS.getConfigOrAdd("port_agitator", 10);

		PORT_CLIMBER_A = CONFIGS.getConfigOrAdd("port_climber_a", 8);
		PORT_CLIMBER_B = CONFIGS.getConfigOrAdd("port_climber_b", 4);

		POLARITY_DRIVE_LEFT_A = CONFIGS.getConfigOrAdd("POLARITY_drive_left_a", false);
		POLARITY_DRIVE_LEFT_B = CONFIGS.getConfigOrAdd("POLARITY_drive_left_b", false);
		POLARITY_DRIVE_RIGHT_A = CONFIGS.getConfigOrAdd("POLARITY_drive_right_a", false);
		POLARITY_DRIVE_RIGHT_B = CONFIGS.getConfigOrAdd("POLARITY_drive_right_b", false);

		POLARITY_SHOOTER_A = CONFIGS.getConfigOrAdd("POLARITY_shooter_flywheel_a", true);
		POLARITY_SHOOTER_B = CONFIGS.getConfigOrAdd("POLARITY_shooter_flywheel_b", true);

		POLARITY_LOADER = CONFIGS.getConfigOrAdd("POLARITY_shooter_loader", true);

		POLARITY_AGITATOR = CONFIGS.getConfigOrAdd("POLARITY_agitator", true);

		POLARITY_CLIMBER_A = CONFIGS.getConfigOrAdd("POLARITY_climber_a", false);
		POLARITY_CLIMBER_B = CONFIGS.getConfigOrAdd("POLARITY_climber_b", false);

		INTAKE_PICKUP_TIMEOUT = CONFIGS.getConfigOrAdd("INTAKE_PICKUP_TIMEOUT", 0.1);
		INTAKE_MOTOR_SPEED = CONFIGS.getConfigOrAdd("INTAKE_MOTOR_SPEED", 1);
		INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD = CONFIGS.getConfigOrAdd("INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD", 0.5);

		PORT_INTAKE_MOTOR = CONFIGS.getConfigOrAdd("PORT_INTAKE_MOTOR", 7);
		PORT_INTAKE_SOLENOID_FORWARD = CONFIGS.getConfigOrAdd("PORT_INTAKE_SOLENOID_FORWARD", 4);
		PORT_INTAKE_SOLENOID_REVERSE = CONFIGS.getConfigOrAdd("PORT_INTAKE_SOLENOID_REVERSE", 5);

	}
	
	public static void reloadFileAndSelectedConfigs() {
		CONFIGS = RIOConfigs.getInstance();
		INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD = CONFIGS.getConfigOrAdd("INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD", 0.5);
	}
	
}
