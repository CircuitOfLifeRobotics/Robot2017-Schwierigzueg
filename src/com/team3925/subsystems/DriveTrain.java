package com.team3925.subsystems;

import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_LEFT_A;
import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_LEFT_B;
import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_LEFT_C;
import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_RIGHT_A;
import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_RIGHT_B;
import static com.team3925.robot.RobotMap.TALON_ID_DRIVE_RIGHT_C;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.Constants;
import com.team3925.util.MiscUtil;
import com.team3925.util.recording.DriveTrainState;
import com.team3925.util.recording.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem implements Recordable<DriveTrainState> {

	private static DriveTrain driveTrain;

	public CANTalon leftA, leftB, leftC;
	public CANTalon rightA, rightB, rightC;

	public static DriveTrain getInstance() {
		if (driveTrain == null)
			driveTrain = new DriveTrain();
		return driveTrain;
	}

	private DriveTrain() {
		leftA = new CANTalon(TALON_ID_DRIVE_LEFT_A);
		leftB = new CANTalon(TALON_ID_DRIVE_LEFT_B);
		leftC = new CANTalon(TALON_ID_DRIVE_LEFT_C);
		rightA = new CANTalon(TALON_ID_DRIVE_RIGHT_A);
		rightB = new CANTalon(TALON_ID_DRIVE_RIGHT_B);
		rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_C);

		// configure brake mode and switches
		MiscUtil.configureTalon(leftA, false, false, false, false,  true);
		MiscUtil.configureTalon(leftB, false, false, false, false,  true);
		MiscUtil.configureTalon(leftC, false, false, false, false,  true);
		MiscUtil.configureTalon(rightA, false, false, false, false, true);
		MiscUtil.configureTalon(rightB, false, false, false, false, true);
		MiscUtil.configureTalon(rightC, false, false, false, false, true);

		// change modes
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

		leftA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightA.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		// change polarity configs
		leftA.setInverted(true);
		leftA.reverseSensor(true);
		leftB.reverseOutput(true);
		leftC.reverseOutput(true);

//		rightA.setInverted(false);
//		rightA.reverseSensor(true);
//		rightB.reverseOutput(true);
//		rightC.reverseOutput(false);
		rightA.setInverted(false);
		rightA.reverseSensor(true);
		rightB.reverseOutput(true);
		rightC.reverseOutput(true);

		// set electrical maximums
		leftA.configMaxOutputVoltage(12);
		rightA.configMaxOutputVoltage(12);
		leftA.setVoltageRampRate(20);
		rightA.setVoltageRampRate(20);

		leftA.setPID(2, 130, 150, 0, 1, 50, 0);
		rightA.setPID(2, 130, 150, 0, 1, 50, 0);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void enableSpeed() {

		leftA.changeControlMode(TalonControlMode.Speed);
		leftB.changeControlMode(TalonControlMode.Follower);
		leftC.changeControlMode(TalonControlMode.Follower);

		rightA.changeControlMode(TalonControlMode.Speed);
		rightB.changeControlMode(TalonControlMode.Follower);
		rightC.changeControlMode(TalonControlMode.Follower);

		leftA.configNominalOutputVoltage(+0.0f, -0.0f);
		leftA.configPeakOutputVoltage(+12.0f, -12.0f);
		rightA.configNominalOutputVoltage(+0.0f, -0.0f);
		rightA.configPeakOutputVoltage(+12.0f, -12.0f);

		// leftA.reverseSensor(true);
		// rightA.reverseSensor(true);

		leftA.configEncoderCodesPerRev(Constants.DRIVETRAIN_ENC_TICKS_PER_REV);
		rightA.configEncoderCodesPerRev(Constants.DRIVETRAIN_ENC_TICKS_PER_REV);
		leftA.setPID(0.1, 0.0000, 0.0, 1.74, 0, 20, 0);
		rightA.setPID(0.1, 0.0000, 0.0, 1.74, 0, 20, 0);
//		rightA.setPID(0.1, 0.0000, 0.0, 1.53, 0, 20, 0);
		leftA.setVoltageRampRate(10);
		rightA.setVoltageRampRate(10);
	}

	public void enablePercentVbus() {
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

		// set electrical maximums
		leftA.configMaxOutputVoltage(10);
		rightA.configMaxOutputVoltage(10);
		leftA.setVoltageRampRate(20);
		rightA.setVoltageRampRate(20);

		leftA.setPID(2, 130, 150, 0, 1, 50, 0);
		rightA.setPID(2, 130, 150, 0, 1, 50, 0);
	}

	public void setEachMotorRaw(double leftA, double leftB, double leftC, double rightA, double rightB, double rightC) {
		this.leftA.set(leftA);
		this.leftB.set(leftB);
		this.leftC.set(leftC);
		this.rightA.set(rightA);
		this.rightB.set(rightB);
		this.rightC.set(rightC);
	}

	public void setSideRaw(double left, double right) {
		leftA.set(-left);
		rightA.set(-right);
	}

	public double getLeftEncRaw() {
		return -leftA.getEncPosition();
	}

	public double getRightEncRaw() {
		return rightA.getEncPosition();
	}
	
	public double getLeftEncVel() {
		return -leftA.getEncVelocity();
	}
	
	public double getRightEncVel() {
		return rightA.getEncVelocity();
	}

	public void zeroEncoders() {
		leftA.setEncPosition(0);
		rightA.setEncPosition(0);
	}

	public CANTalon getleftA() {
		return leftA;
	}

	public CANTalon getrightA() {
		return rightA;
	}

	@Override
	public DriveTrainState record() {
		DriveTrainState out = new DriveTrainState(leftA.get(), rightA.get());
		return out;
	}

	@Override
	public void repeat(DriveTrainState action) {
		setSideRaw(action.left, action.right);
	}
}
