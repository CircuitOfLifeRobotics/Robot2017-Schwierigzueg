package com.team3925.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	
	DoubleSolenoid upDown;
	
	CANTalon wheels;
	
	private static Intake instance;
	
	public static Intake getInstance() {
		if (instance==null)
			instance = new Intake();
		return instance;
	}
	
	private Intake() {
		upDown = new DoubleSolenoid(0, 1);
		wheels = new CANTalon(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setWheels(double percent) {
		wheels.set(percent);
	}
	
	public void setDown() {
		upDown.set(Value.kForward);
	}
	
	public void setUp() {
		upDown.set(Value.kReverse);
	}
	
	public void setPiston(boolean isUp) {
		if (isUp)
			setUp();
		else
			setDown();
	}
	
}
