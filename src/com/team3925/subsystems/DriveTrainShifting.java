package com.team3925.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrainShifting extends Subsystem {
	
	private static DriveTrainShifting driveTrain;
	private DoubleSolenoid solenoid;
	public static DriveTrainShifting getInstance() {
		if (driveTrain==null)
			driveTrain = new DriveTrainShifting();
		return driveTrain;
	}
	
	private DriveTrainShifting() {
		//TODO: fix values
		solenoid = new DoubleSolenoid(0, 1);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub		
	}
	
	public void setLowGear() {
		solenoid.set(Value.kReverse);
	}
	
	public void setHighGear() {
		solenoid.set(Value.kForward);
	}

	public void setGear(boolean isLow) {
		if (isLow)
			setLowGear();
		else
			setHighGear();
	}	
}