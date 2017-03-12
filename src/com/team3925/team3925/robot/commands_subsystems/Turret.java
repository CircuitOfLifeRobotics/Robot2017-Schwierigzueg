package com.team3925.team3925.robot.commands_subsystems;

import static com.team3925.team3925.robot.util.Constants.*;
import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

class Turret extends Subsystem {
	
	private static Turret instance;
	CANTalon turret;
	
	public static Turret getInstance() {
		return (instance == null) ? instance = new Turret() : instance;
	}
	
	private Turret() {
		turret = new CANTalon(TALON_ID_TURRET);
		turret.changeControlMode(TalonControlMode.PercentVbus);
		turret.setPID(TALON_P_TURRET, TALON_I_TURRET, TALON_D_TURRET);
		turret.enableBrakeMode(TALON_BOOLCONSTANTS_TURRET[4]);
		turret.enableLimitSwitch(TALON_BOOLCONSTANTS_TURRET[0],TALON_BOOLCONSTANTS_TURRET[1]);
		turret.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[2]);
		turret.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_TURRET[3]);
	}
	public void setSpeed(double percent){
		turret.set(percent);
	}
	public boolean getSwitch(){
		return turret.isRevLimitSwitchClosed();
	}
	@Override
	protected void initDefaultCommand() {
		
	}
	public void getConversionFactor(){
		
	}
	
}