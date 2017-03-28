package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends Command{
	
	Navx ahrs;
	double target, maxSpeed, angle, error, speed, minSpeed;
	final double GAINA = 0.03;
	
	public GyroTurn(double degrees) {
		ahrs = Navx.getInstance();
		target = degrees;
		maxSpeed = 1;
		minSpeed = 0.3;
		speed = maxSpeed;
		ahrs.resetNavx();
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		angle = ahrs.getHeading();
		error = (target - angle);
		
		SmartDashboard.putNumber("Angle A", angle);
		
		if (ahrs.getHeading() > 180)
			angle =  angle - 180;
		
//		if (speed <= minSpeed){
//			speed = minSpeed;
//		}else{
			speed = maxSpeed * (error/target);
			speed = Math.max(speed, minSpeed);
//		}
		
		
		DriveTrain.getInstance().setSideRaw(-speed, speed);
		SmartDashboard.putNumber("Error A", error);
		SmartDashboard.putNumber("Speed A", speed);
		SmartDashboard.putNumber("Target A", target);
	}

	@Override
	protected boolean isFinished() {
		return error <= 0;
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
