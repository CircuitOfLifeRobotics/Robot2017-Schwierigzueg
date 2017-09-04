package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.POLARITY_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.PORT_DRIVE_RIGHT_B;

import com.ctre.CANTalon;
import com.team3925.robot.subsystems.DriveTrain.DriveTrainState;
import com.team3925.util.MiscMath;
import com.team3925.util.MultiSpeedController;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Recordable<DriveTrainState> {

	public class DriveTrainState {
		public double l, r;

		public DriveTrainState(double l, double r) {
			this.l = l;
			this.r = r;
		}
	}

	private MultiSpeedController left, right;

	private static DriveTrain instance;

	public static DriveTrain getInstance() {
		return instance == null ? instance = new DriveTrain() : instance;
	}

	public DriveTrain() {
		left = new MultiSpeedController();
		right = new MultiSpeedController();
		CANTalon talon = new CANTalon(PORT_DRIVE_LEFT_A);
		talon.enableBrakeMode(true);
		talon.enableLimitSwitch(false, false);
		left.addController(talon, POLARITY_DRIVE_LEFT_A);
		talon = new CANTalon(PORT_DRIVE_LEFT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		left.addController(talon, POLARITY_DRIVE_LEFT_B);
		talon = new CANTalon(PORT_DRIVE_RIGHT_A);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		right.addController(talon, POLARITY_DRIVE_RIGHT_A);
		talon = new CANTalon(PORT_DRIVE_RIGHT_B);
		talon.enableLimitSwitch(false, false);
		talon.enableBrakeMode(true);
		right.addController(talon, POLARITY_DRIVE_RIGHT_B);
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void setRaw(double left, double right) {
		left = MiscMath.capRange(-1, 1, left);
		right = -MiscMath.capRange(-1, 1, right);
		this.left.set(left);
		this.right.set(right);
	}

	@Override
	public DriveTrainState record() {
		return new DriveTrainState(left.get(), right.get());
	}

	@Override
	public void repeat(DriveTrainState snapshot) {
		left.set(snapshot.l);
		right.set(snapshot.r);
	}
}
