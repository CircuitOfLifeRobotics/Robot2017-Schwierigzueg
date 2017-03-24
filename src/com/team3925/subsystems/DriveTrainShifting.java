package com.team3925.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrainShifting extends Subsystem {
	
	private static DriveTrainShifting driveTrain;
	
	public static DriveTrainShifting getInstance() {
		if (driveTrain==null)
			driveTrain = new DriveTrainShifting();
		return driveTrain;
	}
	
	private DriveTrainShifting() {
		
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setLowGear() {
		
	}
	
	public void setHighGear() {
		
	}
	
	public void setGear(boolean isLow) {
		if (isLow)
			setLowGear();
		else
			setHighGear();
	}
	
}
