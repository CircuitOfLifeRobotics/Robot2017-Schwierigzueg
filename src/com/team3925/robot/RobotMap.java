package com.team3925.robot;

import com.team3925.util.RIOConfigs;

public class RobotMap {

	public static final int PORT_DRIVE_LEFT_A, PORT_DRIVE_LEFT_B, PORT_DRIVE_RIGHT_A, PORT_DRIVE_RIGHT_B;
	public static final int PORT_SHOOTER_A, PORT_SHOOTER_B;
	public static final int PORT_LOADER;
	public static final int PORT_AGITATOR;

	public static final boolean POLARITY_DRIVE_LEFT_A, POLARITY_DRIVE_LEFT_B, POLARITY_DRIVE_RIGHT_A,
			POLARITY_DRIVE_RIGHT_B;
	public static final boolean POLARITY_SHOOTER_A, POLARITY_SHOOTER_B;
	public static final boolean POLARITY_LOADER;
	public static final boolean POLARITY_AGITATOR;

	public static final RIOConfigs CONFIGS;

	static {
		CONFIGS = RIOConfigs.getInstance(System.getProperty("user.home") + "/preferences/Schwierigzeug_comp.txt");

		PORT_DRIVE_LEFT_A = CONFIGS.getConfig("port_drive_left_a", 1, Integer::parseInt);
		PORT_DRIVE_LEFT_B = CONFIGS.getConfig("port_drive_left_b", 2, Integer::parseInt);
		PORT_DRIVE_RIGHT_A = CONFIGS.getConfig("port_drive_right_a", 12, Integer::parseInt);
		PORT_DRIVE_RIGHT_B = CONFIGS.getConfig("port_drive_right_b", 11, Integer::parseInt);

		PORT_SHOOTER_A = CONFIGS.getConfig("port_shooter_flywheel_a", 3, Integer::parseInt);
		PORT_SHOOTER_B = CONFIGS.getConfig("port_shooter_flywheel_b", 9, Integer::parseInt);

		PORT_LOADER = CONFIGS.getConfig("port_shooter_loader", 5, Integer::parseInt);

		PORT_AGITATOR = CONFIGS.getConfig("port_agitator", 10, Integer::parseInt);

		POLARITY_DRIVE_LEFT_A = CONFIGS.getConfig("POLARITY_drive_left_a", false, Boolean::parseBoolean);
		POLARITY_DRIVE_LEFT_B = CONFIGS.getConfig("POLARITY_drive_left_b", false, Boolean::parseBoolean);
		POLARITY_DRIVE_RIGHT_A = CONFIGS.getConfig("POLARITY_drive_right_a", false, Boolean::parseBoolean);
		POLARITY_DRIVE_RIGHT_B = CONFIGS.getConfig("POLARITY_drive_right_b", false, Boolean::parseBoolean);

		POLARITY_SHOOTER_A = CONFIGS.getConfig("POLARITY_shooter_flywheel_a", true, Boolean::parseBoolean);
		POLARITY_SHOOTER_B = CONFIGS.getConfig("POLARITY_shooter_flywheel_b", true, Boolean::parseBoolean);

		POLARITY_LOADER = CONFIGS.getConfig("POLARITY_shooter_loader", true, Boolean::parseBoolean);

		POLARITY_AGITATOR = CONFIGS.getConfig("POLARITY_agitator", true, Boolean::parseBoolean);
	}

}
