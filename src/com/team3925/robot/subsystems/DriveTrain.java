//
//import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_A;
//import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_B;
//import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_A;
//import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_B;
//import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_A;
//import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_B;
//import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_A;
//import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_B;
//import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_A;
//import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_B;
//import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_A;
//import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_B;
//import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_A;
//import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_B;
//import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_A;
//import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_B;
//
//import com.ctre.CANTalon;
//import com.team3925.robot.subsystems.DriveTrain.DriveTrainState;
//import com.team3925.util.MiscMath;
//import com.team3925.util.MultiSpeedController;
//import com.team3925.util.recordable.Recordable;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class DriveTrain extends Subsystem implements Recordable<DriveTrainState> {
//
//	public class DriveTrainState {
//		public double l, r;
//
//		public DriveTrainState(double l, double r) {
//			this.l = l;
//			this.r = r;
//		}
//	}
//
//	private MultiSpeedController left, right;
//
//	private CANTalon lA, rA;
//
//	private static DriveTrain instance;
//
//	public static DriveTrain getInstance() {
//		return instance == null ? instance = new DriveTrain() : instance;
//	}
//
//	public DriveTrain() {
//		left = new MultiSpeedController();
//		right = new MultiSpeedController();
//		CANTalon talon;
//
//		talon = new CANTalon(PORT_DRIVE_RIGHT_A);
//		talon.enableLimitSwitch(false, false);
//		talon.enableBrakeMode(true);
//		talon.setInverted(POLARITY_DRIVE_RIGHT_A);
//		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_A);
//		talon.reverseSensor(REVERSE_SENSOR_RIGHT_A);
//		right.addController(talon);
//		rA = talon;
//		talon = new CANTalon(PORT_DRIVE_RIGHT_B);
//		talon.enableLimitSwitch(false, false);
//		talon.enableBrakeMode(true);
//		talon.setInverted(POLARITY_DRIVE_RIGHT_B);
//		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_B);
//		talon.reverseSensor(REVERSE_SENSOR_RIGHT_B);
//		right.addController(talon);
//
//		talon= new CANTalon(PORT_DRIVE_LEFT_A);
//		talon.enableBrakeMode(true);
//		talon.enableLimitSwitch(false, false);
//		talon.setInverted(POLARITY_DRIVE_LEFT_A);
//		talon.reverseOutput(REVERSE_OUTPUT_LEFT_A);
//		talon.reverseSensor(REVERSE_SENSOR_LEFT_A);
//		left.addController(talon);
//		lA = talon;
//		talon = new CANTalon(PORT_DRIVE_LEFT_B);
//		talon.enableLimitSwitch(false, false);
//		talon.enableBrakeMode(true);
//		talon.setInverted(POLARITY_DRIVE_LEFT_B);
//		talon.reverseOutput(REVERSE_OUTPUT_LEFT_B);
//		talon.reverseSensor(REVERSE_SENSOR_LEFT_B);
//		left.addController(talon);
//	}
//
//	@Override
//	protected void initDefaultCommand() {
//
//	}
//
//	public void setRaw(double left, double right) {
//		left = MiscMath.capRange(-1, 1, left);
//		right = -MiscMath.capRange(-1, 1, right);
//		this.left.set(left);
//		this.right.set(right);
//		SmartDashboard.putNumber("drive train left enc velocity", lA.getEncVelocity());
//		SmartDashboard.putNumber("drive train left speed", lA.getSpeed());
//		SmartDashboard.putNumber("drive train right enc velocity", rA.getEncVelocity());
//		SmartDashboard.putNumber("drive train right speed", rA.getSpeed());
//	}
//	public void resetEncoders() {
//		lA.setEncPosition(0);
//		rA.setEncPosition(0);
//	}
//
//	@Override
//	public DriveTrainState record() {
//		return new DriveTrainState(left.get(), right.get());
//	}
//
//	@Override
//	public void repeat(DriveTrainState snapshot) {
//		left.set(snapshot.l);
//		right.set(snapshot.r);
//	}
//}

package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_A;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_LEFT_B;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_A;
import static com.team3925.robot.RobotMap.REVERSE_OUTPUT_RIGHT_B;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_A;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_LEFT_B;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_A;
import static com.team3925.robot.RobotMap.REVERSE_SENSOR_RIGHT_B;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.subsystems.DriveTrain.DriveTrainState;
import com.team3925.util.MiscMath;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Recordable<DriveTrainState> {

	public static class DriveTrainState {
		public double l, r;

		public DriveTrainState(double l, double r) {
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return l + "`" + r;
		}

		public static DriveTrainState fromString(String s) {
			try {
				double l = Double.parseDouble(s.substring(0, s.indexOf('`')));
				double r = Double.parseDouble(s.substring(s.indexOf('`') + 1));
				return new DriveTrainState(l, r);
			} catch (NumberFormatException e) {
				return new DriveTrainState(0, 0);
			}

		}
	}

	private CANTalon lA, rA;

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
		rA = talon;
		talon = new CANTalon(PORT_DRIVE_RIGHT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_RIGHT_B);
		talon.reverseOutput(REVERSE_OUTPUT_RIGHT_B);
		talon.reverseSensor(REVERSE_SENSOR_RIGHT_B);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.set(rA.getDeviceID());

		talon = new CANTalon(PORT_DRIVE_LEFT_A);
		talon.enableBrakeMode(true);
		talon.enableLimitSwitch(false, false);
		talon.setInverted(POLARITY_DRIVE_LEFT_A);
		talon.reverseOutput(REVERSE_OUTPUT_LEFT_A);
		talon.reverseSensor(REVERSE_SENSOR_LEFT_A);
		talon.changeControlMode(TalonControlMode.PercentVbus);
		lA = talon;
		talon = new CANTalon(PORT_DRIVE_LEFT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		talon.setInverted(POLARITY_DRIVE_LEFT_B);
		talon.reverseOutput(REVERSE_OUTPUT_LEFT_B);
		talon.reverseSensor(REVERSE_SENSOR_LEFT_B);
		talon.changeControlMode(TalonControlMode.Follower);
		talon.set(lA.getDeviceID());
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

	@Override
	public DriveTrainState record() {
		// Note: getSetpoint() returns the correct value (both positive) in
		// spite of inversions. get() returns the incorrect value
		return new DriveTrainState(lA.getSetpoint(), rA.getSetpoint());
	}

	@Override
	public void repeat(DriveTrainState snapshot) {
		lA.set(snapshot.l);
		rA.set(snapshot.r);
	}
}
