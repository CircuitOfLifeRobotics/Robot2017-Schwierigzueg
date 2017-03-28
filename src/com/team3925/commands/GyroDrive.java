package com.team3925.commands;

import com.team3925.robot.Constants;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroDrive extends Command{
	Navx ahrs = Navx.getInstance();
	double maxSpeed = 0.3;
	double angle;
	final double GAIN = 0.03;
	double gain;
	double target = 5 * (12 * Constants.DRIVETRAIN_ENC_TICKS_PER_REV / (Math.PI * Constants.DRIVETRAIN_WHEEL_DIAMETER));
	double setpoint;
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		ahrs.resetNavx();
		target = 5;
	}
	@Override
	protected void execute() {
		SmartDashboard.putNumber("HEADINGZ", ahrs.getHeading());
		if (ahrs.getHeading() > 180){
			angle = 180 - ahrs.getHeading();
		}else{
			angle = ahrs.getHeading();
		}
		DriveTrain.getInstance().setSideRaw(-1 * (maxSpeed - (GAIN* angle)), -1 * (maxSpeed + (GAIN* angle)));
	}

	@Override
	protected boolean isFinished() {
		return ((DriveTrain.getInstance().getLeftEncRaw() + DriveTrain.getInstance().getRightEncRaw()) / 2) >= target;
	}

}
