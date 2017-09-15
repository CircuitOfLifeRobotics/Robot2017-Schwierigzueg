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
import com.team3925.robot.subsystems.DriveTrain.PolarDriveTrainState;
import com.team3925.util.MiscMath;
import com.team3925.util.RIOConfigs;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements Recordable<PolarDriveTrainState> {

	public static class PolarDriveTrainState {
		public double angle, l, r;

		public PolarDriveTrainState(double angle, double l, double r) {
			this.angle = angle;
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return angle + "`" + l + "`" + r;
		}

		public static PolarDriveTrainState fromString(String s) {
			try {
				double angle = Double.parseDouble(s.substring(0, s.indexOf('`')));
				double l = Double.parseDouble(s.substring(s.indexOf('`') + 1, s.lastIndexOf('`')));
				double r = Double.parseDouble(s.substring(s.lastIndexOf('`') + 1));
				return new PolarDriveTrainState(angle, l, r);
			} catch (NumberFormatException e) {
				return new PolarDriveTrainState(0, 0, 0);
			}

		}
	}

	public static class PercentVBusDriveTrainState {
		public double l, r;

		public PercentVBusDriveTrainState(double l, double r) {
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return l + "`" + r;
		}

		public static PercentVBusDriveTrainState fromString(String s) {
			try {
				double l = Double.parseDouble(s.substring(0, s.indexOf('`')));
				double r = Double.parseDouble(s.substring(s.indexOf('`') + 1));
				return new PercentVBusDriveTrainState(l, r);
			} catch (NumberFormatException e) {
				return new PercentVBusDriveTrainState(0, 0);
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
	public PolarDriveTrainState record() {
		return new PolarDriveTrainState(Gyro.getInstance().getFusedHeading(), lA.getSetpoint(), rA.getSetpoint());
	}

	public double getRightSpeed() {
		return rA.getSpeed();
	}

	public double getRightEncVelocity() {
		return rA.getEncVelocity();
	}

	public double getRightSetpoint() {
		return rA.getSetpoint();
	}

	@Override
	public void repeat(PolarDriveTrainState snapshot) {
		double angleError = (snapshot.angle - Gyro.getInstance().getFusedHeading()) % 360;
		if (angleError > 180)
			angleError -= 360;
		if (angleError < -180)
			angleError += 360;
		double prelimL = snapshot.l;
		double prelimR = snapshot.r;

		// correction with gyro
		if (RIOConfigs.getInstance().getConfigOrAdd("DO AUTO GYRO CORRECTION", false)) {
			prelimL += angleError / 10;
			prelimR -= angleError / 10;
			if (prelimL > 1) {
				prelimR += (1 - prelimL);
				prelimL = 1;
			}
			if (prelimL < -1) {
				prelimR += (-1 - prelimL);
				prelimL = -1;
			}
			if (prelimR > 1) {
				prelimL += (1 - prelimR);
				prelimR = 1;
			}
			if (prelimR < -1) {
				prelimL += (-1 - prelimR);
				prelimR = -1;
			}
			SmartDashboard.putBoolean("DO AUTO GYRO CORRECTION", true);
		} else {
			SmartDashboard.putBoolean("DO AUTO GYRO CORRECTION", false);
		}

		SmartDashboard.putNumber("error angle", angleError);
		SmartDashboard.putNumber("record l", snapshot.l);
		SmartDashboard.putNumber("record r", snapshot.r);
		SmartDashboard.putNumber("repeat l", prelimL);
		SmartDashboard.putNumber("repeat r", prelimR);
		lA.set(prelimL);
		rA.set(prelimR);
	}

	// @Override
	// public PercentVBusDriveTrainState record() {
	// // Note: getSetpoint() returns the correct value (both positive) in
	// // spite of inversions. get() returns the incorrect value
	// return new PercentVBusDriveTrainState(lA.getSetpoint(),
	// rA.getSetpoint());
	// }
	//
	// @Override
	// public void repeat(PercentVBusDriveTrainState snapshot) {
	// lA.set(snapshot.l);
	// rA.set(snapshot.r);
	// }
}
