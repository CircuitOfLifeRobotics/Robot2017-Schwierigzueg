package com.team3925.subsystems;

import static com.team3925.robot.RobotMap.SOLENOID_PORT_A_INTAKE;
import static com.team3925.robot.RobotMap.SOLENOID_PORT_B_INTAKE;
import static com.team3925.robot.RobotMap.TALON_ID_INTAKE;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private DoubleSolenoid upDown;

	private CANTalon wheels;

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}

	private Intake() {
		upDown = new DoubleSolenoid(SOLENOID_PORT_A_INTAKE, SOLENOID_PORT_B_INTAKE);
		wheels = new CANTalon(TALON_ID_INTAKE);
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
