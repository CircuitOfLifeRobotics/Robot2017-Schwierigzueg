package com.team3925.team3925.robot.commands_subsystems;

import static com.team3925.team3925.robot.util.Constants.*;
import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class Shooter extends Subsystem {
	

	private static Shooter instance;
	private CANTalon flywheel;
	private CANTalon hood;
	
	public static Shooter getInstance() {
		return (instance == null) ? instance = new Shooter() : instance;
	}
	
	private Shooter() {
		hood = new CANTalon(TALON_ID_HOOD);
		hood.changeControlMode(TalonControlMode.PercentVbus);
		hood.setPID(TALON_P_HOOD, TALON_I_HOOD, TALON_D_HOOD);
		hood.enableBrakeMode(TALON_BOOLCONSTANTS_HOOD[4]);
		hood.enableLimitSwitch(TALON_BOOLCONSTANTS_HOOD[0],TALON_BOOLCONSTANTS_HOOD[1]);
		hood.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_HOOD[2]);
		hood.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_HOOD[3]);
		
		
		flywheel = new CANTalon(TALON_ID_FLYWHEEL);
		flywheel.changeControlMode(TalonControlMode.Speed);
		flywheel.setPID(TALON_P_FLYWHEEL, TALON_I_FLYWHEEL, TALON_D_FLYWHEEL, TALON_F_FLYWHEEL, TALON_IZONE_FLYWHEEL, TALON_RAMPRATE_FLYWHEEL, TALON_PROFILE_FLYWHEEL);
		flywheel.enableBrakeMode(TALON_BOOLCONSTANTS_SHOOTER[4]);
		flywheel.enableLimitSwitch(TALON_BOOLCONSTANTS_SHOOTER[0],TALON_BOOLCONSTANTS_SHOOTER[1]);
		flywheel.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_SHOOTER[2]);
		flywheel.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_SHOOTER[3]);
		
		hood.configMaxOutputVoltage(5);
	}
	
	public void setSpeed(double rpm){
		flywheel.setSetpoint(rpm * getShooterConversionFactor());
		SmartDashboard.putNumber("Setpoint", flywheel.getSetpoint());
		SmartDashboard.putNumber("Encoder", flywheel.getEncPosition());
	}
	public void setControlMode(TalonControlMode controlMode){
		flywheel.changeControlMode(controlMode);
	}
	public void setHoodSetpoint(double degrees){
		hood.setSetpoint(degrees * getHoodConversionFactor());
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	private double getShooterConversionFactor(){
		double TicksPerRev = ((SHOOTER_MOTOR_GEAR_TEETH * SHOOTER_FLYWHEEL_GEAR_TEETH) * SHOOTER_ENC_TICKS_PER_REV )/600;
		return TicksPerRev / 600;
	}
	public double getHoodConversionFactor(){
		return HOOD_ENC_TICKS_PER_REV / 360;
	}
	public void setHoodVbus(double voltage){
		hood.set(voltage);
	}
	
}
