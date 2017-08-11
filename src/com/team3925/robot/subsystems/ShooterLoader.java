package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_LOADER;
import static com.team3925.robot.RobotMap.PORT_LOADER;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterLoader extends Subsystem {

	private CANTalon motor;

	private static ShooterLoader instance;

	public static ShooterLoader getInstance() {
		return instance == null ? instance = new ShooterLoader() : instance;
	}

	private ShooterLoader() {
		motor = new CANTalon(PORT_LOADER);
		motor.setInverted(POLARITY_LOADER);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void set(double speed) {
		motor.set(speed);
	}
}
