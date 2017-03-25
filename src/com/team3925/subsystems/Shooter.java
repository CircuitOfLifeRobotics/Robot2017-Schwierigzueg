package com.team3925.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.RobotMap;
import com.team3925.util.recording.Recordable;
import com.team3925.util.recording.ShooterState;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem implements Recordable<ShooterState> {

	private CANTalon shooterA, shooterB;

	private static Shooter instance;

	public double SHOOTER_TRIM;

	public static Shooter getInstance() {
		if (instance == null)
			instance = new Shooter();
		return instance;
	}

	private Shooter() {
		shooterA = new CANTalon(RobotMap.TALON_ID_SHOOTER_A);
		shooterB = new CANTalon(RobotMap.TALON_ID_SHOOTER_B);

		shooterA.changeControlMode(TalonControlMode.Speed);
		shooterB.changeControlMode(TalonControlMode.Follower);
		shooterB.set(shooterA.getDeviceID());

		shooterA.configNominalOutputVoltage(+0.0f, -0.0f);
		shooterA.configPeakOutputVoltage(+10.0f, -10.0f);

		shooterA.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterA.configEncoderCodesPerRev(4096);
		shooterA.setEncPosition(0);

		shooterA.enableBrakeMode(false);
		shooterB.enableBrakeMode(false);

		shooterA.setVoltageRampRate(0);
		shooterA.setPID(.03, 0.000, .3, 0.0318, 0, 10, 0);

		SmartDashboard.putNumber("Speed", 3800);
		SHOOTER_TRIM = 0;
		SmartDashboard.putNumber("Shooter Trim", SHOOTER_TRIM);
	}

	public void setShooterVel(double vel) {
		if (vel == 0) {
			shooterA.changeControlMode(TalonControlMode.PercentVbus);
			shooterA.set(0);
		} else {
			shooterA.changeControlMode(TalonControlMode.Speed);
			SHOOTER_TRIM = SmartDashboard.getNumber("Shooter Trim", 0);
			shooterA.set(vel + Math.signum(vel) * SHOOTER_TRIM);
		}
	}

	public void setShooterVelSmartDashboard() {
		shooterA.changeControlMode(TalonControlMode.Speed);
		shooterA.set(SmartDashboard.getNumber("Speed", 0));
	}

	public double getShooterVel() {
		return shooterA.getEncVelocity();
	}

	public double getError() {
		return shooterA.getError();
	}

	public void initDefaultCommand() {
	}

	@Override
	public ShooterState record() {
		return new ShooterState(shooterA.getSetpoint());
	}

	@Override
	public void repeat(ShooterState action) {
		setShooterVel(action.speed);
	}
}
