
package org.usfirst.frc.team3925.robot.subsystems;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TurretSubsystem extends Subsystem {
	
	private static TurretSubsystem instance;
	
	private CANTalon yaw, pitch;
	private NetworkTable table;
	
	public static TurretSubsystem getInstance() {
		if (instance==null)
			instance = new TurretSubsystem();
		return instance;
	}
	
	private TurretSubsystem() {
		yaw = new CANTalon(TALON_ID_TURRET);
		pitch = new CANTalon(TALON_ID_HOOD);
		
		yaw.changeControlMode(TalonControlMode.Position);
		pitch.changeControlMode(TalonControlMode.Position);
		
		yaw.setPID(TALON_P_TURRET, TALON_I_TURRET, TALON_D_TURRET, TALON_F_TURRET, TALON_IZONE_TURRET,
				TALON_RAMPRATE_TURRET, TALON_PROFILE_TURRET);
		pitch.setPID(TALON_P_HOOD, TALON_I_HOOD, TALON_D_HOOD, TALON_F_HOOD, TALON_IZONE_HOOD,
				TALON_RAMPRATE_HOOD, TALON_PROFILE_HOOD);
		
		pitch.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_HOOD[2]);
		pitch.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_HOOD[3]);
		yaw.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[2]);
		yaw.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[3]);
		
		pitch.enableLimitSwitch(TALON_BOOLCONSTANTS_HOOD[0], TALON_BOOLCONSTANTS_HOOD[1]);
		yaw.enableLimitSwitch(TALON_BOOLCONSTANTS_TURRET[0], TALON_BOOLCONSTANTS_TURRET[1]);
		
		pitch.enableBrakeMode(TALON_BOOLCONSTANTS_TURRET[4]);
		yaw.enableBrakeMode(TALON_BOOLCONSTANTS_TURRET[4]);
		
		// TODO: correct name of network table
		table = NetworkTable.getTable("CameraStream");
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	//TODO: convert units to degrees
	public void setTurretSetpoint(double setpoint) {
		yaw.set(setpoint);
	}
	
	//TODO: convert units to degrees
	public void setHoodSetpoint(double setpoint) {
		yaw.set(setpoint);
	}
	
}
