package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_AGITATOR;
import static com.team3925.robot.RobotMap.PORT_AGITATOR;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Agitator extends Subsystem {

	private CANTalon motor;

	private static Agitator instance;

	private Agitator() {
		motor = new CANTalon(PORT_AGITATOR);
		motor.setInverted(POLARITY_AGITATOR);
	}

	public static Agitator getInstance() {
		return instance == null ? instance = new Agitator() : instance;
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void set(double speed) {
		motor.set(speed);
	}
}
