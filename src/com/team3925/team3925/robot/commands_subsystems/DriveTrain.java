package com.team3925.team3925.robot.commands_subsystems;

import static com.team3925.team3925.robot.util.Constants.*;
import static org.usfirst.frc.team3925.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class DriveTrain extends Subsystem {
	
	private static DriveTrain instance;
	private CANTalon leftA, leftB, leftC, rightA, rightB, rightC;
	private DoubleSolenoid shifter, climber;
	private AnalogInput gearSensor;
	public static DriveTrain getInstance() {
		return (instance == null) ? instance = new DriveTrain() : instance;
	}
	
	private DriveTrain() {
		//construct stuff
				leftA = new CANTalon(TALON_ID_DRIVE_LEFT_A);
				leftB = new CANTalon(TALON_ID_DRIVE_LEFT_B);
				leftC = new CANTalon(TALON_ID_DRIVE_LEFT_C);
				rightA = new CANTalon(TALON_ID_DRIVE_RIGHT_A);
				rightB = new CANTalon(TALON_ID_DRIVE_RIGHT_B);
				rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_C);
				
				shifter = new DoubleSolenoid(SOLENOID_PORT_A_SHIFT, SOLENOID_PORT_B_SHIFT);
				climber = new DoubleSolenoid(SOLENOID_PORT_A_CLIMB, SOLENOID_PORT_B_CLIMB);
				gearSensor = new AnalogInput(GEAR_SENSOR_PORT);
				
				//configure encoders
				leftA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
				rightA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
				
				//configure brake mode and switches
				leftA.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				leftA.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				leftA.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				leftA.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				leftB.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				leftB.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				leftB.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				leftB.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				leftC.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				leftC.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				leftC.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				leftC.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				rightA.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				rightA.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				rightA.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				rightA.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				rightB.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				rightB.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				rightB.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				rightB.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				rightC.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
				rightC.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
				rightC.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
				rightC.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
				
				//change modes
				leftA.changeControlMode(TalonControlMode.Position);
				leftB.changeControlMode(TalonControlMode.Follower);
				leftC.changeControlMode(TalonControlMode.Follower);
				
				rightA.changeControlMode(TalonControlMode.Position);
				rightB.changeControlMode(TalonControlMode.Follower);
				rightC.changeControlMode(TalonControlMode.Follower);
				
				leftB.set(leftA.getDeviceID());
				leftC.set(leftA.getDeviceID());
				
				rightB.set(rightA.getDeviceID());
				rightC.set(rightA.getDeviceID());
				
				leftA.setEncPosition(0);
				leftA.setInverted(true);
				leftB.reverseOutput(true);
				leftC.reverseOutput(false);
//				leftB.setInverted(true);
//				leftC.setInverted(false);
				leftA.reverseSensor(false);
				
				rightA.setEncPosition(0);
				rightA.setInverted(true);
//				rightB.setInverted(true);
//				rightC.setInverted(true);
				rightB.reverseOutput(true);
				rightC.reverseOutput(true);
				
				leftA.configMaxOutputVoltage(10);
				rightA.configMaxOutputVoltage(10);
				
				//put stuff on smart dashboard
				SmartDashboard.putData("Left A", leftA);
				SmartDashboard.putData("Left B", leftB);
				SmartDashboard.putData("Left C", leftC);
				SmartDashboard.putData("Right A", rightA);
				SmartDashboard.putData("Right B", rightB);
				SmartDashboard.putData("Right C", rightC);
				
				leftA.setPID(TALON_P_DRIVETRAIN, TALON_I_DRIVETRAIN, TALON_D_DRIVETRAIN, 
						TALON_F_DRIVETRAIN, TALON_IZONE_DRIVETRAIN, TALON_RAMPRATE_DRIVETRAIN, TALON_PROFILE_DRIVETRAIN);
				
				
				rightA.setPID(TALON_P_DRIVETRAIN, TALON_I_DRIVETRAIN, TALON_D_DRIVETRAIN, 
						TALON_F_DRIVETRAIN, TALON_IZONE_DRIVETRAIN, TALON_RAMPRATE_DRIVETRAIN, TALON_PROFILE_DRIVETRAIN);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void setControlMode(TalonControlMode talonControlMode){
		leftA.changeControlMode(talonControlMode);
		leftB.changeControlMode(talonControlMode);
		leftC.changeControlMode(talonControlMode);
		
		rightA.changeControlMode(talonControlMode);
		rightB.changeControlMode(talonControlMode);
		rightC.changeControlMode(talonControlMode);
	}
	public void setControlMode(TalonControlMode leftAControlMode,TalonControlMode leftBControlMode,
			TalonControlMode leftCControlMode, TalonControlMode rightAControlMode, TalonControlMode rightBControlMode,
			TalonControlMode rightCControlMode){
		
		leftA.changeControlMode(leftAControlMode);
		leftB.changeControlMode(leftBControlMode);
		leftC.changeControlMode(leftCControlMode);
		
		rightA.changeControlMode(rightAControlMode);
		rightB.changeControlMode(rightBControlMode);
		rightC.changeControlMode(rightCControlMode);
	}
	public void setSetpointSpeed(double fps){
		double setpoint = ((fps/10) * getConversionFactor());
		leftA.setSetpoint(setpoint);
		rightA.setSetpoint(setpoint);
	}
	
	public void setRawAll(double leftASetpoint,double leftBSetpoint, double leftCSetpoint, double rightASetpoint, double rightBSetpoint, double rightCSetpoint) {
		leftA.set(leftASetpoint);
		leftB.set(leftBSetpoint);
		leftC.set(leftCSetpoint);
		
		rightA.set(rightASetpoint);
		rightB.set(rightBSetpoint);
		rightC.set(rightCSetpoint);
	}
	public void logEncoderVals(){
		SmartDashboard.putNumber("RightA", rightA.getEncPosition());
		SmartDashboard.putNumber("LeftA", leftA.getEncPosition());
	}
	public void setSetpointFeet(double feet){
		setLeftFeet(feet);
		setRightFeet(feet);
		SmartDashboard.putNumber("Error", leftA.getError());
		SmartDashboard.putNumber("Conversion Factor: ", getConversionFactor());
		SmartDashboard.putNumber("Setpoint", leftA.getSetpoint());
		SmartDashboard.putNumber("CurrentPosition: ", leftA.getEncPosition());
	}
	private void setLeftFeet(double feet){
		leftA.setSetpoint(feet * getConversionFactor());
	}
	private void setRightFeet(double feet){
		rightA.setSetpoint(feet * getConversionFactor());
	}
	public double getConversionFactor(){
		return ((1 / ((DRIVETRAIN_WHEEL_DIAMETER * Math.PI) / 12)) * DRIVETRAIN_ENC_TICKS_PER_REV);
	}
	public void zeroEncoders(){
		leftA.setEncPosition(0);
		rightA.setEncPosition(0);
	}
	public boolean isLeftAtSetpoint(int deadzone){
		return (getTalonError(leftA) < deadzone);
	}
	public double getError(){
		return Math.abs(leftA.getPosition() - leftA.getSetpoint());
	}
	public boolean isRightAtSetpoint(int deadzone){
		return (getTalonError(rightA) < deadzone);
	}
	public void turnRobot(double degrees){
		setLeftFeet(degrees * DRIVETRAIN_FEET_PER_DEGREE);
		setRightFeet(-1 * (degrees * DRIVETRAIN_FEET_PER_DEGREE));
	}
	public void configFollowers(CANTalon[] slaves, CANTalon master){
		for (int i = 0; slaves.length > i; i++){
			slaves[i].set(master.getDeviceID());
		}
	}
	private double getTalonError(CANTalon talon){
		return Math.abs(talon.getSetpoint() - talon.getEncPosition());
	}
	public void setMaxVoltage(double voltage){
		leftA.configMaxOutputVoltage(voltage);
		rightA.configMaxOutputVoltage(voltage);
	}
	public void shiftLow(){
		shifter.set(Value.kReverse);
	}
	public double getGearValue(){
		return gearSensor.getVoltage();
	}
	
}