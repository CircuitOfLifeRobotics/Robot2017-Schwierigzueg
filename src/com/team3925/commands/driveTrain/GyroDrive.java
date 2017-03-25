package com.team3925.commands.driveTrain;

import com.team3925.robot.Constants;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroDrive extends PIDCommand {

	Navx ahrs;

	static final double kP = 0.001;
	static final double kI = 0.0;
	static final double kD = 0.0002;
	static final double kTOLERANCE_TICKS = 75;
	static final double kTOLERANCE_DELTA = 0;
	static final double kP_ANGLE = 0.003;
	static final double MAX_TIME = 1;

	double angle, position, deltaPos, itr, setPoint, initTime, finalTime;

	public GyroDrive(double setPoint) {
		super(kP, kI, kD);
		this.setPoint = setPoint;
		itr = 0;
		getPIDController().setOutputRange(-1, 1);
		getPIDController().setAbsoluteTolerance(kTOLERANCE_TICKS * Constants.DRIVETRAIN_FT_2_ENC_TICKS);
		getPIDController().setContinuous(true);
		ahrs = Navx.getInstance();
		SmartDashboard.putData("GyroDrive", this.getPIDController());
		initTime = Timer.getFPGATimestamp();
		finalTime = initTime + MAX_TIME;
	}

	@Override
	protected void initialize() {
		DriveTrain.getInstance().zeroEncoders();
		DriveTrain.getInstance().enablePercentVbus();
		ahrs.resetNavx();
	}

	@Override
	protected void execute() {
		if (Timer.getFPGATimestamp() < finalTime) {
			setSetpoint((setPoint * Constants.DRIVETRAIN_FT_2_ENC_TICKS) * (Timer.getFPGATimestamp()) / finalTime);
		} else {
			setSetpoint(setPoint * Constants.DRIVETRAIN_FT_2_ENC_TICKS);
		}
		SmartDashboard.putDouble("GyroDrive Error", getPIDController().getError());
		angle = ((ahrs.getHeading() + 180) % 360) - 180;
		deltaPos = position
				- ((DriveTrain.getInstance().getLeftEncRaw() + DriveTrain.getInstance().getRightEncRaw()) / 2);
		position = (DriveTrain.getInstance().getLeftEncRaw() + DriveTrain.getInstance().getRightEncRaw()) / 2;
	}

	@Override
	protected boolean isFinished() {
		return (Math.abs(getPIDController().getError()) < kTOLERANCE_TICKS) && (deltaPos == kTOLERANCE_DELTA)
				&& Timer.getFPGATimestamp() > finalTime;
	}

	@Override
	protected void end() {
		System.out.println("Drive Ended; Error: " + getPIDController().getError());
		DriveTrain.getInstance().setSideRaw(0, 0);
	}

	@Override
	protected void interrupted() {
		this.end();
	}

	@Override
	protected double returnPIDInput() {
		return position;
	}

	@Override
	protected void usePIDOutput(double output) {
		DriveTrain.getInstance().setSideRaw(output - (angle * kP_ANGLE), output + (angle * kP_ANGLE));
	}
}