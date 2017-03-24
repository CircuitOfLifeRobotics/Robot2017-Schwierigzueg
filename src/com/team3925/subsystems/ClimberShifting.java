package com.team3925.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberShifting extends Subsystem {
	
	private static ClimberShifting instance;
	
	DoubleSolenoid climberShifter;
	
	public static ClimberShifting getInstance() {
		if (instance==null)
			instance = new ClimberShifting();
		return instance;
	}
	
	private ClimberShifting() {
		climberShifter = new DoubleSolenoid(0, 1);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setClimberEngaged() {
		climberShifter.set(Value.kForward);
	}
	
	public void setClimberDisengaged() {
		climberShifter.set(Value.kReverse);
	}
	
	public void setClimber(boolean engaged) {
		if (engaged)
			setClimberEngaged();
		else
			setClimberDisengaged();
	}

}
