package com.team3925.subsystems;

import static com.team3925.robot.RobotMap.SOLENOID_PORT_A_CLIMB;
import static com.team3925.robot.RobotMap.SOLENOID_PORT_B_CLIMB;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberShifting extends Subsystem {

	private static ClimberShifting instance;

	private DoubleSolenoid climberShifter;

	public static ClimberShifting getInstance() {
		if (instance == null)
			instance = new ClimberShifting();
		return instance;
	}

	private ClimberShifting() {
		climberShifter = new DoubleSolenoid(SOLENOID_PORT_A_CLIMB, SOLENOID_PORT_B_CLIMB);
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
	
	public boolean getClimberEngaged() {
		return climberShifter.get().equals(Value.kForward);
	}

}
