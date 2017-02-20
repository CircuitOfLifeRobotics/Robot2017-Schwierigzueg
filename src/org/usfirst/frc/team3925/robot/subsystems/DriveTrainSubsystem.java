package org.usfirst.frc.team3925.robot.subsystems;

import static org.usfirst.frc.team3925.robot.RobotMap.*;
import org.usfirst.frc.team3925.robot.Constants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainSubsystem extends Subsystem {
	
	private static DriveTrainSubsystem instance;
	
	//TODO: Revert For debugging
	private CANTalon leftA, leftB, leftC, rightA, rightB, rightC;
	private DoubleSolenoid shifter, climber;
	
	public static DriveTrainSubsystem getInstance() {
		if (instance==null)
			instance = new DriveTrainSubsystem();
		return instance;
	}
	
	private DriveTrainSubsystem() {
		
		//construct stuff
		leftA = new CANTalon(TALON_ID_DRIVE_LEFT_A);
		leftB = new CANTalon(TALON_ID_DRIVE_LEFT_B);
		leftC = new CANTalon(TALON_ID_DRIVE_LEFT_C);
		rightA = new CANTalon(TALON_ID_DRIVE_RIGHT_A);
		rightB = new CANTalon(TALON_ID_DRIVE_RIGHT_B);
		rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_C);
		shifter = new DoubleSolenoid(SOLENOID_PORT_A_SHIFT, SOLENOID_PORT_B_SHIFT);
		climber = new DoubleSolenoid(SOLENOID_PORT_A_CLIMB, SOLENOID_PORT_B_CLIMB);
		
		//configure encoders
		leftA.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightA.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
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
		leftA.changeControlMode(TalonControlMode.PercentVbus);
		leftB.changeControlMode(TalonControlMode.Follower);
		leftC.changeControlMode(TalonControlMode.Follower);
		
		rightA.changeControlMode(TalonControlMode.PercentVbus);
		rightB.changeControlMode(TalonControlMode.Follower);
		rightC.changeControlMode(TalonControlMode.Follower);
		
		leftB.set(leftA.getDeviceID());
		leftC.set(leftA.getDeviceID());
		
		rightB.set(rightA.getDeviceID());
		rightC.set(rightA.getDeviceID());
		
		leftA.setInverted(true);
		leftB.reverseOutput(true);
		leftC.reverseOutput(false);
		
		rightA.setInverted(true);
		rightB.reverseOutput(false);
		rightC.reverseOutput(false);
		
		//put stuff on smart dashboard
		SmartDashboard.putData("Left A", leftA);
		SmartDashboard.putData("Left B", leftB);
		SmartDashboard.putData("Left C", leftC);
		SmartDashboard.putData("Right A", rightA);
		SmartDashboard.putData("Right B", rightB);
		SmartDashboard.putData("Right C", rightC);
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setRaw(double left, double right) {
		leftA.set(left);
		rightA.set(right);
	}
	
	public void setShifter(boolean engaged) {
		shifter.set(engaged ? DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse);
	}
	
	public void setClimber(boolean engaged) {
		climber.set(engaged ? DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse);
	}
	
	public void setBrake(boolean engaged) {
		leftA.enableBrakeMode(engaged);
		leftB.enableBrakeMode(engaged);
		leftC.enableBrakeMode(engaged);
		rightA.enableBrakeMode(engaged);
		rightB.enableBrakeMode(engaged);
		rightC.enableBrakeMode(engaged);
	}
	public void setControlModes(TalonControlMode controlMode){
		leftA.changeControlMode(controlMode);
		leftB.changeControlMode(controlMode);
		leftC.changeControlMode(controlMode);
		
		rightA.changeControlMode(controlMode);
		rightB.changeControlMode(controlMode);
		rightC.changeControlMode(controlMode);
	}
	public void setSetpoint(double left, double right){
		leftA.setSetpoint(left);
		rightA.setSetpoint(right);
	}
	public void setSetpointFeet(double feet){
		leftA.setSetpoint(feet * getConversionFactor());
		rightA.setSetpoint(feet * getConversionFactor());
	}
	
	public void setLeftAInverted(boolean inverted) {
		leftA.setInverted(inverted);
	}
	
	public void setLeftBInverted(boolean inverted) {
		leftB.reverseOutput(inverted);
	}
	
	public void setLeftCInverted(boolean inverted) {
		leftC.reverseOutput(inverted);
	}
	
	public void setRightAInverted(boolean inverted) {
		rightA.setInverted(inverted);
	}
	
	public void setRightBInverted(boolean inverted) {
		rightB.reverseOutput(inverted);
	}
	
	public void setRightCInverted(boolean inverted) {
		rightC.reverseOutput(inverted);
	}

	public double getConversionFactor(){
		return (1 / ((Constants.DRIVETRAIN_WHEEL_DIAMETER * Math.PI) / 12)) * Constants.ENCODER_TICKS_PER_REV;
	}
	
}
