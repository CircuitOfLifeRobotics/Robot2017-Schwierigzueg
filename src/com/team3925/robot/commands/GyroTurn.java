package com.team3925.robot.commands;

import com.team3925.robot.Robot;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.Gyro;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class GyroTurn extends PIDCommand {

	private double startTime;
	private double startAngle, endAngle, currAngle, lastAngle, deltaAngle, rotations;
	private double setpoint;
	private static final double DELTA_SETPOINT = 3, TOLERANCE = 1;
	private boolean doUpdateAngle;

	public GyroTurn(double relativeAngle) {
		super(1/4d, 0.0004, 0);
		deltaAngle = relativeAngle;
		requires(DriveTrain.getInstance());
		doUpdateAngle = false;
	}

	public GyroTurn() {
		super(1/4d, 0.0004, 0);
		deltaAngle = Robot.CAMERA_COMMAND.getHorizontalOffsetAngle().orElse(0d);
		requires(DriveTrain.getInstance());
		doUpdateAngle = true;
	}

	@Override
	protected void initialize() {
		if (doUpdateAngle)
			deltaAngle = Robot.CAMERA_COMMAND.getHorizontalOffsetAngle().orElse(0d);
		System.out.println("GYRO TURN INITTED WITH " + deltaAngle + " DEGREES OF TURN");
		startTime = Timer.getFPGATimestamp();
		startAngle = Gyro.getInstance().getFusedHeading();
		endAngle = startAngle + deltaAngle;
		setpoint = startAngle;
	}

	@Override
	protected void execute() {
		if (endAngle - setpoint < 0) {
			setpoint = Math.max(setpoint - Math.abs(DELTA_SETPOINT), endAngle);
		} else {
			setpoint = Math.min(setpoint + Math.abs(DELTA_SETPOINT), endAngle);
		}
		setSetpoint(setpoint);
		lastAngle = currAngle;
	}

	@Override
	protected double returnPIDInput() {
		currAngle = Gyro.getInstance().getFusedHeading();
		if (Math.abs(currAngle - lastAngle) > 180) {
			rotations -= Math.signum(currAngle - lastAngle);
		}
		return currAngle + rotations * 360;
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.println("GYRO TURN error " + getPIDController().getAvgError());
		DriveTrain.getInstance().setRaw(output, -output);
	}

	@Override
	protected boolean isFinished() {
		return getPIDController().getAvgError() < TOLERANCE && Timer.getFPGATimestamp() - startTime > 1;
	}

	@Override
	protected void end() {
		System.out.println("GYRO TURN ended");
		Robot.DRIVE_MANUAL.start();
	}

	@Override
	protected void interrupted() {
		System.out.println("GYRO TURN interruped");
		Robot.DRIVE_MANUAL.start();
	}

}
