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

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private static DriveTrain driveTrain;

	private CANTalon leftA, leftB, leftC;
	private CANTalon rightA, rightB, rightC;

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

		// configure encoders
		leftA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightA.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		// configure brake mode and switches
		configureTalon(leftA, false, false, false, false, true);
		configureTalon(leftB, false, false, false, false, true);
		configureTalon(leftC, false, false, false, false, true);
		configureTalon(rightA, false, false, false, false, true);
		configureTalon(rightB, false, false, false, false, true);
		configureTalon(rightC, false, false, false, false, true);

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

		// change polarity configs
		leftA.setEncPosition(0);
		leftA.setInverted(true);
		leftA.reverseSensor(true);
		leftB.reverseOutput(true);
		leftC.reverseOutput(false);

		rightA.setEncPosition(0);
		rightA.setInverted(true);
		rightA.reverseSensor(true);
		rightB.reverseOutput(true);
		rightC.reverseOutput(true);

		// set electrical maximums
		leftA.configMaxOutputVoltage(10);
		rightA.configMaxOutputVoltage(10);
		leftA.setVoltageRampRate(50);
		rightA.setVoltageRampRate(50);

		leftA.setPID(2, 130, 150, 0, 1, 50, 0);
		rightA.setPID(2, 130, 150, 0, 1, 50, 0);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

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
		leftA.set(left);
		rightA.set(right);
	}

	public double getLeftEncRaw() {
		return leftA.getEncPosition();
	}

	public double getRightEncRaw() {
		return rightA.getEncPosition();
	}

	private void configureTalon(CANTalon talon, boolean fwdLimitSwitchOpen, boolean revLimitSwitchOpen,
			boolean fwdLimitSwitchEnabled, boolean revLimitSwitchEnabled, boolean brakeModeEnabled) {
		talon.ConfigFwdLimitSwitchNormallyOpen(fwdLimitSwitchOpen);
		talon.ConfigRevLimitSwitchNormallyOpen(revLimitSwitchOpen);
		talon.enableLimitSwitch(fwdLimitSwitchEnabled, revLimitSwitchEnabled);
		talon.enableBrakeMode(brakeModeEnabled);
	}
}
