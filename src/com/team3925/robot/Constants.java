
package com.team3925.robot;

public class Constants {

	public final static double MIN_CONFIG_WAIT_TIME;

	public final static double TALON_P_DRIVETRAIN;
	public final static double TALON_I_DRIVETRAIN;
	public final static double TALON_D_DRIVETRAIN;
	public final static double TALON_F_DRIVETRAIN;
	public final static int TALON_IZONE_DRIVETRAIN;
	public final static double TALON_RAMPRATE_DRIVETRAIN;
	public final static int TALON_PROFILE_DRIVETRAIN;

	public final static double TALON_P_TURRET;
	public final static double TALON_I_TURRET;
	public final static double TALON_D_TURRET;
	public final static double TALON_F_TURRET;
	public final static int TALON_IZONE_TURRET;
	public final static double TALON_RAMPRATE_TURRET;
	public final static int TALON_PROFILE_TURRET;

	public final static double TALON_P_HOOD;
	public final static double TALON_I_HOOD;
	public final static double TALON_D_HOOD;
	public final static double TALON_F_HOOD;
	public final static double TALON_RAMPRATE_HOOD;
	public final static int TALON_IZONE_HOOD;
	public final static int TALON_PROFILE_HOOD;

	public final static double TALON_P_FLYWHEEL;
	public final static double TALON_I_FLYWHEEL;
	public final static double TALON_D_FLYWHEEL;
	public final static double TALON_F_FLYWHEEL;
	public final static double TALON_RAMPRATE_FLYWHEEL;
	public final static int TALON_IZONE_FLYWHEEL;
	public final static int TALON_PROFILE_FLYWHEEL;

	public static final double DRIVETRAIN_WHEEL_DIAMETER;
	public static final int DRIVETRAIN_ENC_TICKS_PER_REV;
	public static final double DRIVETRAIN_FEET_PER_DEGREE;
	public static final int DRIVETRAIN_DEADZONE;
	public static final int DRIVETRAIN_SHIFT_THRESHOLD;
	public static final double DRIVETRAIN_FT_2_ENC_TICKS;

	public static final int SHOOTER_MOTOR_GEAR_TEETH;
	public static final int SHOOTER_FLYWHEEL_GEAR_TEETH;
	public static final int SHOOTER_ENC_TICKS_PER_REV;

	public static final int HOOD_FLYWHEEL_GEAR_TEETH;
	public static final int HOOD_MOTOR_GEAR_TEETH;
	public static final int HOOD_ENC_TICKS_PER_REV;

	public static final double DRIVETRAIN_GEAR_SENSOR_THRESHOLD;

	public static final boolean[] TALON_BOOLCONSTANTS_TURRET;
	public static final boolean[] TALON_BOOLCONSTANTS_SHOOTER;
	public static final boolean[] TALON_BOOLCONSTANTS_DRIVETRAIN;
	public static final boolean[] TALON_BOOLCONSTANTS_INTAKE;
	public static final boolean[] TALON_BOOLCONSTANTS_HOOD;

	public static final double SHOOTER_SPEED;
	public static final double SHOOTER_TARGET_SPEED;

	public static final double AUTO_BLUE_OFFSET;
	public static final double AUTO_RED_OFFSET;

	static {
		MIN_CONFIG_WAIT_TIME = 0.5;

		// DRIVETRAIN PID CONSTANTS
		TALON_P_DRIVETRAIN = 2;
		TALON_I_DRIVETRAIN = 130;
		TALON_D_DRIVETRAIN = 150;
		TALON_F_DRIVETRAIN = 0;
		TALON_IZONE_DRIVETRAIN = 1;
		TALON_RAMPRATE_DRIVETRAIN = 50;
		TALON_PROFILE_DRIVETRAIN = 0;

		// TURRET PID CONSTANTS
		TALON_P_TURRET = 1;
		TALON_I_TURRET = 0;
		TALON_D_TURRET = 0;
		TALON_F_TURRET = 0;
		TALON_IZONE_TURRET = 1;
		TALON_RAMPRATE_TURRET = 100;
		TALON_PROFILE_TURRET = 0;

		// FLYWHEEL PID CONSTANTS
		TALON_P_FLYWHEEL = 0.2;
		TALON_I_FLYWHEEL = 0;
		TALON_D_FLYWHEEL = 0;
		TALON_F_FLYWHEEL = 30000;
		TALON_IZONE_FLYWHEEL = 1;
		TALON_RAMPRATE_FLYWHEEL = 100;
		TALON_PROFILE_FLYWHEEL = 0;

		// HOOD PID CONSTANTS
		TALON_P_HOOD = 1;
		TALON_I_HOOD = 0;
		TALON_D_HOOD = 0;
		TALON_F_HOOD = 0;
		TALON_IZONE_HOOD = 1;
		TALON_RAMPRATE_HOOD = 100;
		TALON_PROFILE_HOOD = 0;

		// DRIVETRAIN DIMENSION CONSTANTS
		DRIVETRAIN_WHEEL_DIAMETER = 4.196;
		DRIVETRAIN_ENC_TICKS_PER_REV = 512;
		DRIVETRAIN_FEET_PER_DEGREE = 26.5 * Math.PI / 12 / 360;
		DRIVETRAIN_DEADZONE = 20;

		DRIVETRAIN_SHIFT_THRESHOLD = 225;

		DRIVETRAIN_GEAR_SENSOR_THRESHOLD = 0.32;
		DRIVETRAIN_FT_2_ENC_TICKS = 12 * Constants.DRIVETRAIN_ENC_TICKS_PER_REV
				/ (Math.PI * Constants.DRIVETRAIN_WHEEL_DIAMETER);

		// SHOOTER DIMENSION CONSTANTS
		SHOOTER_FLYWHEEL_GEAR_TEETH = 18;
		SHOOTER_MOTOR_GEAR_TEETH = 22;
		SHOOTER_ENC_TICKS_PER_REV = 4096;

		HOOD_FLYWHEEL_GEAR_TEETH = 15;
		HOOD_MOTOR_GEAR_TEETH = 50;
		HOOD_ENC_TICKS_PER_REV = 4096;

		SHOOTER_SPEED = 0.80;
		SHOOTER_TARGET_SPEED = 24500;

		AUTO_BLUE_OFFSET = 110.02 - 110.02;
		AUTO_RED_OFFSET = 110.02 - 110.02;

		// SWITCH AND BRAKE CONSTANTS
		// {fwdSwitchIsPresent, revSwitchIsPresent, fwdSwitchNormalyOpen,
		// revSwitchNormalyOpen, brakeModeEnabled}
		// configuration is same for every talon in each system
		TALON_BOOLCONSTANTS_DRIVETRAIN = new boolean[] { false, false, false, false, true };
		TALON_BOOLCONSTANTS_SHOOTER = new boolean[] { false, false, false, false, false };
		TALON_BOOLCONSTANTS_TURRET = new boolean[] { false, false, false, false, true };
		TALON_BOOLCONSTANTS_INTAKE = new boolean[] { false, false, false, false, false };
		TALON_BOOLCONSTANTS_HOOD = new boolean[] { false, false, false, false, true };
	}

	private Constants() {
	}

}
