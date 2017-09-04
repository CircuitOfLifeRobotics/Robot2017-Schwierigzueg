package com.team3925.robot.commands;

import com.team3925.robot.Robot;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.Gyro;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class GyroTurn extends PIDCommand implements Recordable<Double> {

	private double startTime, endTime;
	private double startSet, endSet, deltaSet, currSet;
	private final static double estimatedDt = 0.2;

	public GyroTurn(double relativeAngle) {
		super(0.5, 0.000, 0);
		relativeAngle = 90;
		deltaSet = relativeAngle;
		requires(DriveTrain.getInstance());
	}

	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
		endTime = startTime + 5;
		startSet = Gyro.getInstance().getFusedHeading();
		endSet = startSet + deltaSet;
		Robot.DRIVE_MANUAL.cancel();
	}

	@Override
	protected void execute() {
		currSet += (endSet - currSet) / ((endTime - Timer.getFPGATimestamp() + startSet) / estimatedDt);
		setSetpoint(currSet);
	}

	@Override
	protected double returnPIDInput() {
		System.out.println("here ya go pid " + Gyro.getInstance().getFusedHeading());
		return Gyro.getInstance().getFusedHeading();
	}

	@Override
	protected void usePIDOutput(double output) {
		System.out.println("error " + getPIDController().getAvgError());
		System.out.println("Drive train ya bein used " + output);
		DriveTrain.getInstance().setRaw(output, -output);
	}

	@Override
	protected boolean isFinished() {
		return getPIDController().getAvgError() < 1 && Timer.getFPGATimestamp() - startTime > 1;
	}

	@Override
	protected void end() {
		System.out.println("ended");
		Robot.DRIVE_MANUAL.start();
	}

	@Override
	protected void interrupted() {
		System.out.println("interruped");
		Robot.DRIVE_MANUAL.start();
	}

	@Override
	public Double record() {
		return 0d;
	}

	@Override
	public void repeat(Double snapshot) {
		setSetpoint(snapshot);
	}

}
