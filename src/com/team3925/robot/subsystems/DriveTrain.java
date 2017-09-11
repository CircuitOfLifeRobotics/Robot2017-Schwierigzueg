//package com.team3925.robot.subsystems;
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
import com.team3925.util.RIOConfigs;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements Recordable<DriveTrainState> {

	public class DriveTrainState {
		public double l, r;

		public DriveTrainState(double l, double r) {
			this.l = l;
			this.r = r;
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
		talon.changeControlMode(TalonControlMode.Speed);
		// talon.changeControlMode(TalonControlMode.PercentVbus);
		talon.configEncoderCodesPerRev(256 / 4);
		talon.setPID(RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_P", 1 / 1800d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_I", 1 / 1800d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_D", 0), 0, 9999, 9999, 0);
		talon.setPID(RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_P_POS", 10d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_I_POS", 0),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_D_POS", 0), 0, 9999, 9999, 1);
		talon.setProfile(0);
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
		talon.changeControlMode(TalonControlMode.Speed);
		// talon.changeControlMode(TalonControlMode.PercentVbus);
		talon.configEncoderCodesPerRev(256 / 4);
		talon.setPID(RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_P", 1 / 1800d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_I", 10d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_D", 0), 0, 9999, 9999, 0);
		talon.setPID(RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_P_POS", 1 / 1800d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_I_POS", 1 / 1800d),
				RIOConfigs.getInstance().getConfigOrAdd("DRIVE_TRAIN_PID_D_POS", 0), 0, 9999, 9999, 1);
		talon.setProfile(0);
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

	public double getLeftDriveSpeed() {
		return lA.getEncVelocity();
	}

	public double getRightDriveSpeed() {
		return rA.getEncVelocity();
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void setRaw(double left, double right) {
		left = MiscMath.capRange(-1, 1, left) * 2500;
		right = MiscMath.capRange(-1, 1, right) * 2500;
		// left = MiscMath.capRange(-1, 1, left) ;
		// right = MiscMath.capRange(-1, 1, right) ;
		// left = MiscMath.capRange(-1, 1, left);
		// right = -MiscMath.capRange(-1, 1, right);
		lA.set(left);
		rA.set(right);
		SmartDashboard.putNumber("left error = ", lA.getError());
		SmartDashboard.putNumber("left closed loop error = ", lA.getClosedLoopError());
		SmartDashboard.putNumber("right error = ", rA.getError());
		SmartDashboard.putNumber("right closed loop error = ", rA.getClosedLoopError());
		// SmartDashboard.putNumber("left position", lA.getPosition());
		// SmartDashboard.putNumber("left enc position", lA.getEncPosition());
		// SmartDashboard.putNumber("right position", rA.getPosition());
		// SmartDashboard.putNumber("right enc position", rA.getEncPosition());

		SmartDashboard.putNumber("drive train left enc velocity", lA.getEncVelocity());
		SmartDashboard.putNumber("drive train left speed", lA.getSpeed());
		SmartDashboard.putNumber("drive train right enc velocity", rA.getEncVelocity());
		SmartDashboard.putNumber("drive train right speed", rA.getSpeed());
	}

	public void resetEncoders() {
		lA.setEncPosition(0);
		rA.setEncPosition(0);
	}

	@Override
	public DriveTrainState record() {
		//speed
		// return new DriveTrainState(lA.getSpeed() * (lA.getInverted() ? -1 :
		// 1),
		// rA.getSpeed() * (rA.getInverted() ? -1 : 1));0
		//commanded speed
		// return new DriveTrainState(lA.getSetpoint(), rA.getSetpoint());
		//fused
		 return new DriveTrainState((lA.getSpeed() * (lA.getInverted() ? -1 :
		 1) + lA.getSetpoint()) / 2,
		 (rA.getSpeed() * (rA.getInverted() ? -1 : 1) + rA.getSetpoint()) /
		 2);
		//fused position
//		return new DriveTrainState(lA.getPosition() * (lA.getInverted() ? -1 : 1) + lA.getSetpoint() * 0.025,
//				rA.getPosition() * (rA.getInverted() ? -1 : 1) + rA.getSetpoint() * 0.025);
	}

//	public void changeToSpeedMode() {
//		lA.changeControlMode(TalonControlMode.Speed);
//		rA.changeControlMode(TalonControlMode.Speed);
//		lA.setProfile(0);
//		rA.setProfile(0);
//	}
//
//	public void changeToPositionMode() {
//		lA.changeControlMode(TalonControlMode.Position);
//		rA.changeControlMode(TalonControlMode.Position);
//		lA.setProfile(1);
//		rA.setProfile(1);
//	}

	@Override
	public void repeat(DriveTrainState snapshot) {
		lA.set(snapshot.l);
		rA.set(snapshot.r);
	}
}
