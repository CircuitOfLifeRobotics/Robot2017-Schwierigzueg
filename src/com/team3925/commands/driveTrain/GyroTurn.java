package com.team3925.commands.driveTrain;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends PIDCommand {

	Navx ahrs;

	static final double kP = 0.01;
	static final double kI = 0.0;
	static final double kD = 0.005;
	static final double kTOLERANCE_DEGREES = .5;
	static final double kTOLERANCE_DELTA = 0.000;
	static final double MAX_TIME = 1;

	double angle, deltaAngle, setPoint;

	public GyroTurn(double setPoint) {
		super(kP, kI, kD);
		this.setPoint = setPoint;
		requires(DriveTrain.getInstance());
		getPIDController().setInputRange(-180, 180);
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setAbsoluteTolerance(kTOLERANCE_DEGREES);
		getPIDController().setContinuous(true);
		ahrs = Navx.getInstance();
	}

	@Override
	protected void initialize() {
		System.out.println("GyroTurn.initialize()");
		ahrs.resetNavx();
		DriveTrain.getInstance().zeroEncoders();
		DriveTrain.getInstance().enablePercentVbus();
	}

	@Override
	protected void execute() {

		setSetpoint(setPoint);
		SmartDashboard.putNumber("GyroTurn Error", getPIDController().getError());
		deltaAngle = angle - (((ahrs.getHeading() + 180) % 360) - 180);
		angle = ((ahrs.getHeading() + 180) % 360) - 180;
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(getPIDController().getError()) < kTOLERANCE_DEGREES) && (deltaAngle == kTOLERANCE_DELTA);
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