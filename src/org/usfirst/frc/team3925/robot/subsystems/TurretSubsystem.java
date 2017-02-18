
package org.usfirst.frc.team3925.robot.subsystems;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TurretSubsystem extends Subsystem {
	
	private static TurretSubsystem instance;
	
	private CANTalon turret;
	private NetworkTable table;
	
	private boolean valuesExist;
	private double degs2ticks;
	private double rads2ticks;
	private double rots2ticks;
	
	public static TurretSubsystem getInstance() {
		if (instance==null)
			instance = new TurretSubsystem();
		return instance;
	}
	
	private TurretSubsystem() {
		turret = new CANTalon(TALON_ID_TURRET);
		turret.changeControlMode(TalonControlMode.Position);
		// TODO: change profile so that both are used
		turret.setPID(TALON_P_TURRET, TALON_I_TURRET, TALON_D_TURRET, TALON_F_TURRET, TALON_IZONE_TURRET,
				TALON_RAMPRATE_TURRET, TALON_PROFILE_TURRET);
		
		
		turret.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[2]);
		turret.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[3]);
		
		turret.enableLimitSwitch(TALON_BOOLCONSTANTS_TURRET[0], TALON_BOOLCONSTANTS_TURRET[1]);
		
		turret.enableBrakeMode(TALON_BOOLCONSTANTS_TURRET[4]);	
		
		
		// TODO: correct name of network table
		table = NetworkTable.getTable("CameraStream");
		
		degs2ticks = 0;//
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setSetpointDegs(double setpoint) {
		turret.set(setpoint);
	}
	
}
