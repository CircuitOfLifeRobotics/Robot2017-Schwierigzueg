package com.team3925.team3925.robot.commands_subsystems;

import static com.team3925.team3925.robot.util.Constants.TALON_BOOLCONSTANTS_INTAKE;
import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

class Intake extends Subsystem {
	
	private static Intake instance;
	private CANTalon intake, lowerElevator, upperElevator;
	
	
	public static Intake getInstance() {
		return (instance == null) ? instance = new Intake() : instance;
	}
	
	private Intake() {
		intake = new CANTalon(TALON_ID_INTAKE);
		intake.changeControlMode(TalonControlMode.PercentVbus);
		intake.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		intake.setInverted(false);
		intake.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0],TALON_BOOLCONSTANTS_INTAKE[1]);
		intake.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		intake.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
		
		lowerElevator = new CANTalon(TALON_ID_ELEVATOR_LOWER);
		lowerElevator.changeControlMode(TalonControlMode.PercentVbus);
		lowerElevator.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		lowerElevator.setInverted(true);
		lowerElevator.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0],TALON_BOOLCONSTANTS_INTAKE[1]);
		lowerElevator.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		lowerElevator.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
		
		upperElevator = new CANTalon(TALON_ID_ELEVATOR_UPPER);
		upperElevator.changeControlMode(TalonControlMode.PercentVbus);
		upperElevator.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		upperElevator.setInverted(true);
		upperElevator.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0],TALON_BOOLCONSTANTS_INTAKE[1]);
		upperElevator.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		upperElevator.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
		
		
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	public void setIntakeSpeed(double speed){
		
	}
	public void runIntake(double speed){
		intake.set(speed);
		lowerElevator.set(speed);
		upperElevator.set(speed);
	}
}