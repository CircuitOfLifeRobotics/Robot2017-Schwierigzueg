package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	
	private static DriveTrain driveTrain;
	
	public static DriveTrain getInstance() {
		if (driveTrain==null)
			driveTrain = new DriveTrain();
		return driveTrain;
	}
	
	private DriveTrain() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setEachMotorRaw(double leftA, double leftB, double leftC, double rightA, double rightB, double rightC) {
		
	}
	
	public void setSideRaw(double left, double right) {
		setEachMotorRaw(left, left, left, right, right, right);
	}
	
	public double getLeftEncRaw() {
		return 0;
	}
	
	public double getRightEncRaw() {
		return 0;
	}
	
	
	
}
