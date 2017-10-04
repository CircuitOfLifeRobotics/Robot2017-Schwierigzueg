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

import java.util.ArrayList;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.util.MiscMath;
import com.team3925.util.RIOConfigs;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements Recordable {

	public static class GyroDriveTrainState {

		public double l, r, angle, fusedFloorAccel, xv, yv, zv, xa, ya, za;

		public GyroDriveTrainState() {
			this(DriveTrain.getInstance().getLeftSetpoint(), DriveTrain.getInstance().getRightSetpoint(),
					Gyro.getInstance().getFusedHeading(), Gyro.getInstance().getFusedFloorAccel(),
					Gyro.getInstance().getXVelocity(), Gyro.getInstance().getYVelocity(),
					Gyro.getInstance().getZVelocity(), Gyro.getInstance().getXAccel(), Gyro.getInstance().getYAccel(),
					Gyro.getInstance().getZAccel());
		}

		public GyroDriveTrainState(double l, double r, double angle, double fusedFloorAccel, double xv, double yv,
				double zv, double xa, double ya, double za) {
			this.l = l;
			this.r = r;
			this.angle = angle;
			this.fusedFloorAccel = fusedFloorAccel;
			this.xv = xv;
			this.yv = yv;
			this.zv = zv;
			this.xa = xa;
			this.ya = ya;
			this.za = za;
		}

		public static GyroDriveTrainState fromString(String s) {
			try {
				ArrayList<Double> list = new ArrayList<>();
				int idx1;
				int idx0 = 0;
				while ((idx1 = s.indexOf('`', idx0)) != -1) {
					list.add(Double.parseDouble(s.substring(idx0, idx1)));
					idx0 = idx1 + 1;
				}
				if (list.size() >= 9)
					return new GyroDriveTrainState(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4),
							list.get(5), list.get(6), list.get(7), list.get(8), list.get(9));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return new GyroDriveTrainState(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}

		public String toString() {
			return l + "`" + r + "`" + angle + "`" + fusedFloorAccel + "`" + xv + "`" + yv + "`" + zv + "`" + xa + "`"
					+ ya + "`" + za + "`";
		}
	}

	private static CANTalon lA, rA;

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
	public String record() {
		return new GyroDriveTrainState().toString();
	}

	public double getLeftSetpoint() {
		return lA.getSetpoint();
	}

	public double getRightSetpoint() {
		return rA.getSetpoint();
	}

	@Override
	public void repeat(String s) {
		GyroDriveTrainState snapshot = GyroDriveTrainState.fromString(s);
		double angleError = (snapshot.angle - Gyro.getInstance().getFusedHeading()) % 360;
		if (angleError > 180)
			angleError -= 360;
		if (angleError < -180)
			angleError += 360;
		double prelimL = snapshot.l;
		double prelimR = snapshot.r;

		// correction with gyro
		if (RIOConfigs.getInstance().getConfigOrAdd("DO AUTO GYRO CORRECTION", false)) {
			prelimL += angleError * RIOConfigs.getInstance().getConfigOrAdd("AUTO GYRO CORRECTION CONSTANT", 0.05);
			prelimR -= angleError * RIOConfigs.getInstance().getConfigOrAdd("AUTO GYRO CORRECTION CONSTANT", 0.05);
			SmartDashboard.putBoolean("DO AUTO GYRO CORRECTION", true);
		} else {
			SmartDashboard.putBoolean("DO AUTO GYRO CORRECTION", false);
		}

		double mag = snapshot.fusedFloorAccel;
		double curr = Math
				.sqrt(Math.pow(Gyro.getInstance().getXVelocity(), 2) + Math.pow(Gyro.getInstance().getYVelocity(), 2));
		double errorMagnitude = curr - mag;
		SmartDashboard.putNumber("GYRO MAG ERROR", errorMagnitude);
		prelimL += RIOConfigs.getInstance().getConfigOrAdd("AUTO MAG CORRECTION CONSTANT", 1.5) * errorMagnitude;
		prelimR += RIOConfigs.getInstance().getConfigOrAdd("AUTO MAG CORRECTION CONSTANT", 1.5) * errorMagnitude;

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

		lA.set(prelimL);
		rA.set(prelimR);
	}

}
