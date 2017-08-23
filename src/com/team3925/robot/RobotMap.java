package com.team3925.robot;

import com.team3925.util.RIOConfigs;

public class RobotMap {

	public static final int PORT_DRIVE_LEFT_A, PORT_DRIVE_LEFT_B, PORT_DRIVE_RIGHT_A, PORT_DRIVE_RIGHT_B;
	public static final int PORT_SHOOTER_A, PORT_SHOOTER_B;
	public static final int PORT_LOADER;
	public static final int PORT_AGITATOR;
	public static final int PORT_CLIMBER;

	public static final boolean POLARITY_DRIVE_LEFT_A, POLARITY_DRIVE_LEFT_B, POLARITY_DRIVE_RIGHT_A,
			POLARITY_DRIVE_RIGHT_B;
	public static final boolean POLARITY_SHOOTER_A, POLARITY_SHOOTER_B;
	public static final boolean POLARITY_LOADER;
	public static final boolean POLARITY_AGITATOR;
	public static final boolean POLARITY_CLIMBER;
	
	public static final RIOConfigs CONFIGS;

	static {
//		CONFIGS = RIOConfigs.getInstance(System.getProperty("user.home") + "/preferences/Schwierigzeug_comp.txt");
		CONFIGS = RIOConfigs.getInstance();
		
		System.out.println(RIOConfigs.getInstance());
		
		PORT_DRIVE_LEFT_A = CONFIGS.getConfigOrAdd("port_drive_left_a", 1);
		PORT_DRIVE_LEFT_B = CONFIGS.getConfigOrAdd("port_drive_left_b", 2);
		PORT_DRIVE_RIGHT_A = CONFIGS.getConfigOrAdd("port_drive_right_a", 12);
		PORT_DRIVE_RIGHT_B = CONFIGS.getConfigOrAdd("port_drive_right_b", 11);

		PORT_SHOOTER_A = CONFIGS.getConfigOrAdd("port_shooter_flywheel_a", 3);
		PORT_SHOOTER_B = CONFIGS.getConfigOrAdd("port_shooter_flywheel_b", 9);

		PORT_LOADER = CONFIGS.getConfigOrAdd("port_shooter_loader", 5);

		PORT_AGITATOR = CONFIGS.getConfigOrAdd("port_agitator", 10);

		PORT_CLIMBER = CONFIGS.getConfigOrAdd("port_climber", 8);
		
		POLARITY_DRIVE_LEFT_A = CONFIGS.getConfigOrAdd("POLARITY_drive_left_a", false);
		POLARITY_DRIVE_LEFT_B = CONFIGS.getConfigOrAdd("POLARITY_drive_left_b", false);
		POLARITY_DRIVE_RIGHT_A = CONFIGS.getConfigOrAdd("POLARITY_drive_right_a", false);
		POLARITY_DRIVE_RIGHT_B = CONFIGS.getConfigOrAdd("POLARITY_drive_right_b", false);

		POLARITY_SHOOTER_A = CONFIGS.getConfigOrAdd("POLARITY_shooter_flywheel_a", true);
		POLARITY_SHOOTER_B = CONFIGS.getConfigOrAdd("POLARITY_shooter_flywheel_b", true);

		POLARITY_LOADER = CONFIGS.getConfigOrAdd("POLARITY_shooter_loader", true);

		POLARITY_AGITATOR = CONFIGS.getConfigOrAdd("POLARITY_agitator", true);
		
		POLARITY_CLIMBER = CONFIGS.getConfigOrAdd("POLARITY_climber", false);
	}

}
