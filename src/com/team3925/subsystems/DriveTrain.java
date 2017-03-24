package com.team3925.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

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
		leftA = new CANTalon(40);
		leftB = new CANTalon(32);
		leftC = new CANTalon(2);
		rightA = new CANTalon(55);
		rightB = new CANTalon(10);
		rightC = new CANTalon(11);

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

		// change polarity configs
		leftA.setEncPosition(0);
		leftA.setInverted(false);
		leftA.reverseSensor(true);
		leftB.reverseOutput(true);
		leftC.reverseOutput(true);

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
		setEachMotorRaw(left, left, left, right, right, right);
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

	public void setControlModes(TalonControlMode controlMode) {
		leftA.changeControlMode(controlMode);
		rightA.changeControlMode(controlMode);
	}

}
