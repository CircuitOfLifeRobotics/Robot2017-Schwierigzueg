package com.team3925.commands.driveTrain;

import com.team3925.subsystems.DriveTrain;
import static com.team3925.robot.Constants.*;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends PIDCommand {

	double angle, deltaAngle, setPoint,initAngle,time,initTime;

	public GyroTurn(double setPoint) {
		super(GYRO_TURN_KP, GYRO_TURN_KI, GYRO_TURN_KD);
		this.setPoint = setPoint;
		requires(DriveTrain.getInstance());
		getPIDController().setInputRange(-180, 180);
		getPIDController().setOutputRange(GYRO_TURN_OUTPUT_RANGE_LOWER, GYRO_TURN_OUTPUT_RANGE_UPPER);
		getPIDController().setAbsoluteTolerance(GYRO_TURN_TOLERANCE_DEGREES);
		getPIDController().setContinuous(true);
	}

	@Override
	protected void initialize() {
		Navx.getInstance().resetNavx();
		DriveTrain.getInstance().enablePercentVbus();
		setSetpoint(setPoint);
		initTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		if (Timer.getFPGATimestamp() - initTime > .1){
		SmartDashboard.putNumber("GyroTurn Error", getPIDController().getError());
		SmartDashboard.putNumber("GyroTurn DeltaError", deltaAngle);
		deltaAngle = angle - (((Navx.getInstance().getHeading() + 180) % 360) - 180);
		angle = ((Navx.getInstance().getHeading() + 180) % 360) - 180;
		}
	}

	@Override
	protected boolean isFinished() {
		boolean val = (Math.abs(getPIDController().getError()) < GYRO_TURN_TOLERANCE_DEGREES) &&Math.abs(DriveTrain.getInstance().getleftA().getEncVelocity()) < GYRO_TURN_TOLERANCE_DELTA;
		System.out.println("GyroTurn.isFinished(), returned "+val);
		return val;
	}

	@Override
	protected void end() {
		System.out.println("Turn Ended; Error: " + getPIDController().getError());
		DriveTrain.getInstance().setSideRaw(0, 0);
	}

	@Override
	protected void interrupted() {
		this.end();
	}

	@Override
	protected double returnPIDInput() {
		return angle;
	}

	@Override
	protected void usePIDOutput(double output) {
		DriveTrain.getInstance().setSideRaw(-output, output);
	}
}