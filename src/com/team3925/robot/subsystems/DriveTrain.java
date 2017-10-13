package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_C;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_C;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_C;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_C;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_A;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_B;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_C;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_A;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_B;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_C;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_A;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_B;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_C;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_A;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_B;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_C;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.util.MiscMath;
import com.team3925.util.RIOConfigs;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem{

	private static CANTalon lA, rA;
	private static CANTalon lB, rB;
	private static CANTalon lC, rC;

	private static DriveTrain instance;

	public static DriveTrain getInstance() {
		return instance == null ? instance = new DriveTrain() : instance;
	}

	public DriveTrain() {
		CANTalon talon;
		talon = new CANTalon(PORT_DRIVE_RIGHT_A);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_RIGHT_A);
		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_A);
		talon.reverseSensor(REVERSE_SENSOR_RIGHT_A);
		talon.changeControlMode(TalonControlMode.PercentVbus);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		rA = talon;
		
		talon = new CANTalon(PORT_DRIVE_RIGHT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_RIGHT_B);
		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_B);
		talon.reverseSensor(REVERSE_SENSOR_RIGHT_B);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		talon.set(rA.getDeviceID());
		rB = talon;
		
		talon = new CANTalon(PORT_DRIVE_RIGHT_C);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_RIGHT_C);
		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_C);
		talon.reverseSensor(REVERSE_SENSOR_RIGHT_C);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		talon.set(rA.getDeviceID());
		rC = talon;

		talon = new CANTalon(PORT_DRIVE_LEFT_A);
		talon.enableBrakeMode(true);
		talon.enableLimitSwitch(false, false);
		talon.setInverted(POLARITY_DRIVE_LEFT_A);
		talon.reverseOutput(REVERSE_OUTPUT_LEFT_A);
		talon.reverseSensor(REVERSE_SENSOR_LEFT_A);
		talon.changeControlMode(TalonControlMode.PercentVbus);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		lA = talon;
		
		talon = new CANTalon(PORT_DRIVE_LEFT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_LEFT_B);
		talon.reverseOutput(REVERSE_OUTPUT_LEFT_B);
		talon.reverseSensor(REVERSE_SENSOR_LEFT_B);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		talon.set(lA.getDeviceID());
		lB = talon;
		
		talon = new CANTalon(PORT_DRIVE_LEFT_C);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_LEFT_C);
		talon.reverseOutput(REVERSE_OUTPUT_LEFT_C);
		talon.reverseSensor(REVERSE_SENSOR_LEFT_C);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.setVoltageRampRate(RIOConfigs.getInstance().getConfigOrAdd("DRIVE TRAIN RAMP RATE", 8));
		talon.set(lA.getDeviceID());
		lC = talon;
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void setRaw(double left, double right) {
		left = MiscMath.capRange(-1, 1, left);
		right = MiscMath.capRange(-1, 1, right);
		lA.set(left);
		rA.set(right);
		
		
	}
	public double getLeftEncoder(){
		return lA.getEncPosition();
	}
	public double getRightEncoder(){
		return rA.getEncPosition();
	}


	public double getLeftSetpoint() {
		return lA.getSetpoint();
	}

	public double getRightSetpoint() {
		return rA.getSetpoint();
	}

	public double getRightSpeed() {
		return rA.getEncVelocity();
	}
	public double getLeftSpeed() {
		return lA.getEncVelocity();
	}
	
}
