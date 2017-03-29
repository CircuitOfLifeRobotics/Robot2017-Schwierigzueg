package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurn extends Command{
	
	Navx ahrs;
	double target, maxSpeed, angle, error, speed, minSpeed,zone;
	final double GAINA = 0.03;
	
	public GyroTurn(double degrees) {
		ahrs = Navx.getInstance();
		target = degrees;
		maxSpeed = 1;
		minSpeed = 0.2;
		speed = maxSpeed;
		zone = .5;
	}
	
	@Override
	protected void initialize() {
		ahrs.resetNavx();
		DriveTrain.getInstance().zeroEncoders();
	}
	
	@Override
	protected void execute() {
		angle = ahrs.getHeading();
		
		
		if (ahrs.getHeading() > 180)
			angle =  180 - angle;
		error = Math.abs(target - angle);
		speed = error * ((maxSpeed - minSpeed) / 180) + minSpeed;
		
		if (target >= 0){
			DriveTrain.getInstance().setSideRaw(speed, -speed);
		}else{
			DriveTrain.getInstance().setSideRaw(-speed, speed);
		}
		
		
	}

	@Override
	protected boolean isFinished() {
		return  error <= zone;
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
