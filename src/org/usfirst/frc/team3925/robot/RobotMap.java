package org.usfirst.frc.team3925.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public final static double MIN_CONFIG_WAIT_TIME;
	public final static int TALON_ID_TURRET;
	public final static double TALON_P_TURRET;
	public final static double TALON_I_TURRET;
	public final static double TALON_D_TURRET;
	public final static double TALON_F_TURRET;
	public final static int TALON_IZONE_TURRET;
	public final static double TALON_RAMPRATE_TURRET;
	public final static int TALON_PROFILE_TURRET;
	
	public final static int TALON_ID_FLYWHEEL;
	public final static double TALON_P_FLYWHEEL;
	public final static double TALON_I_FLYWHEEL;
	public final static double TALON_D_FLYWHEEL;
	public final static double TALON_F_FLYWHEEL;
	public final static int TALON_IZONE_FLYWHEEL;
	public final static double TALON_RAMPRATE_FLYWHEEL;
	public final static int TALON_PROFILE_FLYWHEEL;
	
	
	public final static int TALON_ID_INTAKE;
	public final static double TALON_P_INTAKE;
	public final static double TALON_I_INTAKE;
	public final static double TALON_D_INTAKE;
	public final static double TALON_F_INTAKE;
	public final static int TALON_IZONE_INTAKE;
	public final static double TALON_RAMPRATE_INTAKE;
	public final static int TALON_PROFILE_INTAKE;
	
	
	
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
	
	public static final boolean[] TALON_BOOLCONSTANTS_TURRET;
	public static final boolean[] TALON_BOOLCONSTANTS_SHOOTER;
	public static final boolean[] TALON_BOOLCONSTANTS_DRIVETRAIN;
	public static final boolean[] TALON_BOOLCONSTANTS_INTAKE; 
	public static final boolean[] TALON_BOOLCONSTANTS_HOOD;
	
	//TODO: correct ports
	static {
		MIN_CONFIG_WAIT_TIME = 0.5;
		
		TALON_ID_TURRET = 0;
		TALON_P_TURRET = 1;
		TALON_I_TURRET = 0;
		TALON_D_TURRET = 0;
		TALON_F_TURRET = 0;
		TALON_IZONE_TURRET = 1;
		TALON_RAMPRATE_TURRET = 100;
		TALON_PROFILE_TURRET = 0;

		
		TALON_ID_FLYWHEEL = 0;
		TALON_P_FLYWHEEL = 1;
		TALON_I_FLYWHEEL = 0;
		TALON_D_FLYWHEEL = 0;
		TALON_F_FLYWHEEL = 0;
		TALON_IZONE_FLYWHEEL = 1;
		TALON_RAMPRATE_FLYWHEEL = 100;
		TALON_PROFILE_FLYWHEEL = 0;
		
		TALON_ID_INTAKE = 0;
		TALON_P_INTAKE = 1;
		TALON_I_INTAKE = 0;
		TALON_D_INTAKE = 0;
		TALON_F_INTAKE = 0;
		TALON_IZONE_INTAKE = 1;
		TALON_RAMPRATE_INTAKE = 100;
		TALON_PROFILE_INTAKE = 0;
		
		//{fwdSwitchIsPresent, revSwitchIsPresent, fwdSwitchNormalyOpen, revSwitchNormalyOpen, brakeModeEnabled}
		TALON_BOOLCONSTANTS_DRIVETRAIN = new boolean[]{false,false,false,false,true};
		TALON_BOOLCONSTANTS_SHOOTER = new boolean[]{false,false,false,false,false};
		TALON_BOOLCONSTANTS_TURRET = new boolean[]{true,false,false,false,true}; 
		TALON_BOOLCONSTANTS_INTAKE = new boolean[]{false,false,false,false,true}; 
		TALON_BOOLCONSTANTS_HOOD = new boolean[]{false,false,false,false,true}; 
		
		TALON_ID_DRIVE_LEFT_A = 0;
		TALON_ID_DRIVE_LEFT_B = 1;
		TALON_ID_DRIVE_LEFT_C = 2;
		TALON_ID_DRIVE_RIGHT_A = 3;
		TALON_ID_DRIVE_RIGHT_B = 10;
		TALON_ID_DRIVE_RIGHT_C = 11;
		
		SOLENOID_PORT_A_SHIFT = 0;
		SOLENOID_PORT_B_SHIFT = 1;
		SOLENOID_PORT_A_CLIMB = 2;
		SOLENOID_PORT_B_CLIMB = 3;
	}
	
}
