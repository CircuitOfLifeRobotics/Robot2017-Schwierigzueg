package com.team3925.util;

import com.team3925.robot.Constants;

public class ChangePoint {
	
	public double vel;
	public double turnRatio;
	public double encoderChangePoint;
	
	public ChangePoint(double vel, double turnRatio, double encoderChangePointFeet){
		this.vel = vel;
		this.turnRatio = turnRatio;
		this.encoderChangePoint = encoderChangePointFeet*Constants.DRIVETRAIN_FT_2_ENC_TICKS;
	}
	
	public double getEncoderChangePoint() {
		return encoderChangePoint;
	}
	public double getTurnRatio() {
		return turnRatio;
	}
	public double getVelcity(){
		return vel;
	}
}
