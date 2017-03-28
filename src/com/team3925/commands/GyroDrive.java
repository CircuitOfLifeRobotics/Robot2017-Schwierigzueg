package com.team3925.commands;

import com.team3925.robot.Constants;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroDrive extends Command{
	Navx ahrs = Navx.getInstance();
	double maxSpeed = 1;
	double minSpeed = 0.2;
	double speed;
	double angle;
	final double GAINA = 0.03;
	final double GAIND = 0.03;
	double ft2ticks = (12 * Constants.DRIVETRAIN_ENC_TICKS_PER_REV / (Math.PI * Constants.DRIVETRAIN_WHEEL_DIAMETER));
	double gain;
	double target = 10 * ft2ticks;
	double setpoint;
	double th = target / 2;
	double error;
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		ahrs.resetNavx();
		speed = maxSpeed;
		DriveTrain.getInstance().zeroEncoders();
	}
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Left Enc Position", DriveTrain.getInstance().getLeftEncRaw());
		SmartDashboard.putNumber("Right Enc Position", DriveTrain.getInstance().getRightEncRaw());
		SmartDashboard.putNumber("Error", error);
		SmartDashboard.putNumber("Target", target);
		SmartDashboard.putNumber("Th", th);
		SmartDashboard.putNumber("Speed", speed);
		error = (target - (DriveTrain.getInstance().getLeftEncRaw() ));
		if (speed <= minSpeed){
			speed = minSpeed;
		}else{
			if (error <= (th) ){
				speed = maxSpeed * (error / th);
			}
		}
		SmartDashboard.putNumber("HEADINGZ", ahrs.getHeading());
		if (ahrs.getHeading() > 180){
			angle = 180 - ahrs.getHeading();
		}else{
			angle = ahrs.getHeading();
		}
		DriveTrain.getInstance().setSideRaw(-1 * (speed - (GAINA* angle)), -1 * (speed + (GAINA* angle)));
	}

	@Override
	protected boolean isFinished() {
		if (error <= 0){
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	protected void end() {
		DriveTrain.getInstance().setSideRaw(0, 0);
		SmartDashboard.putNumber("Left Enc Position", DriveTrain.getInstance().getLeftEncRaw());
		SmartDashboard.putNumber("Right Enc Position", DriveTrain.getInstance().getRightEncRaw());
		SmartDashboard.putNumber("Error", error);
		SmartDashboard.putNumber("Target", target);
		SmartDashboard.putNumber("Th", th);
		SmartDashboard.putNumber("Speed", speed);
	}
	@Override
	protected void interrupted() {
		this.end();
	}
}