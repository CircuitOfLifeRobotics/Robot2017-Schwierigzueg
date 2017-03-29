package com.team3925.commands;

import com.team3925.robot.Constants;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroDrive extends Command {
	Navx ahrs;
	double ft2ticks = (12 * Constants.DRIVETRAIN_ENC_TICKS_PER_REV / (Math.PI * Constants.DRIVETRAIN_WHEEL_DIAMETER));
	double maxSpeed, minSpeed, speed, angle, gain, target, th, error, zone;
	final double GAINA = 0.03;

	public GyroDrive(double setpoint) {
		ahrs = Navx.getInstance();
		maxSpeed = .8;
		minSpeed = 0.25;
		target = setpoint * ft2ticks;
		zone = 0.2 * ft2ticks;
	}

	@Override
	protected void initialize() {
		DriveTrain.getInstance().zeroEncoders();
		ahrs.resetNavx();
		speed = maxSpeed;
	}

	@Override
	protected void execute() {
		error = Math.abs(target - (DriveTrain.getInstance().getLeftEncRaw()));

		if (ahrs.getHeading() > 180) {
			angle = 180 - ahrs.getHeading();
		} else {
			angle = ahrs.getHeading();
		}

		speed = error * ((maxSpeed - minSpeed) / Math.abs(target)) + minSpeed;

		if (target > 0) {
			DriveTrain.getInstance().setSideRaw(1 * (speed - (GAINA * angle)), 1 * (speed + (GAINA * angle)));
		} else {
			System.out.println("CALLING ELSE");
			DriveTrain.getInstance().setSideRaw(1 * (-speed - (GAINA * angle)), 1 * (-speed + (GAINA * angle)));
		}
	}

	@Override
	protected boolean isFinished() {
		if (error <= zone) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void end() {
		DriveTrain.getInstance().setSideRaw(0, 0);
	}

	@Override
	protected void interrupted() {
		this.end();
	}
}