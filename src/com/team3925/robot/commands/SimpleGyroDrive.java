package com.team3925.robot.commands;

import com.team3925.robot.RobotMap;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.Gyro;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class SimpleGyroDrive extends PIDCommand {

	private static final double THRESHOLD_ERROR = 3;
	private static final double MAX_OUTPUT = 1;
	private double distanceInches;
	private double zeroGyro;
	private double zeroLeftEncoder, zeroRightEncoder, finalTime, startTime;
	private final double GYRO_P = 0.05;

	public SimpleGyroDrive(double distance, double timeInMillis) {
		super(0.015, 0, 0);

		distanceInches = distance;
		finalTime = timeInMillis;
		 requires(DriveTrain.getInstance());
	}

	protected void initialize() {
		zeroGyro();
		zeroEncoders();
		startTime = Timer.getFPGATimestamp();
		super.setSetpoint(distanceInches * getSmoothingFunction(Timer.getFPGATimestamp() - startTime, finalTime));
	}

	protected void execute() {
//		System.out.println("pid controller error" + 
//				super.getPIDController().getError());
//		System.out.println("right enc value = " + getRightDistance());
		super.setSetpoint(distanceInches * getSmoothingFunction(Timer.getFPGATimestamp() - startTime, finalTime));
	}

	protected boolean isFinished() {
		// Time >= AllottedTime && DriveTrain Speeds < Threshold
		return (super.getPIDController().getError() < THRESHOLD_ERROR && Timer.getFPGATimestamp() > startTime + finalTime);
	}

	protected void end() {
		System.out.println("Destination Reached");
		DriveTrain.getInstance().setRaw(0, 0);
	}

	protected void interrupted() {
		this.end();
	}

	@Override
	protected double returnPIDInput() {
		return -(getRightDistance());
	}

	@Override
	protected void usePIDOutput(double output) {
		DriveTrain.getInstance().setRaw(Math.min(output, MAX_OUTPUT) - (getGyroHeading() * GYRO_P),
				Math.min(output, MAX_OUTPUT) + (getGyroHeading() * GYRO_P));
	}

	public double getSmoothingFunction(double currentTime, double finalTime) {
		// Desmos Graph: https://www.desmos.com/calculator/3sls1cegnx
//		System.out.println("smoothing " + 0.5 * (1 - (Math.cos(Math.PI * (currentTime / finalTime)))));
		return (0.5 * (1 - (Math.cos(Math.PI * (currentTime / finalTime)))));
//		return 1;
	}

	// private double getLeftDistance() {
	// return ((DriveTrain.getInstance().getLeftEncoder() - zeroLeftEncoder) /
	// RobotMap.DRIVETRAIN_TICKS_PER_REV)
	// * (Math.PI * RobotMap.DRIVETRAIN_WHEEL_DIAMETER);
	// }
	private double getRightDistance() {
		return ((DriveTrain.getInstance().getRightTicks() - zeroRightEncoder) / (RobotMap.DRIVETRAIN_TICKS_PER_REV * 4 )) //*4 cuz its quadrature
				* (Math.PI * RobotMap.DRIVETRAIN_WHEEL_DIAMETER);
	}

	private void zeroEncoders() {
		zeroRightEncoder = DriveTrain.getInstance().getRightTicks();
	}

	private void zeroGyro() {
		Gyro.getInstance().setAngleToZero();
	}

	private double getGyroHeading() {
		double heading = Gyro.getInstance().getFusedHeading();
		if (heading > 180){
			heading = heading - 360;
		}
		return heading;
	}
}
