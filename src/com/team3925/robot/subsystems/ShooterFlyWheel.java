package com.team3925.robot.subsystems;

import static com.team3925.robot.RobotMap.POLARITY_SHOOTER_A;
import static com.team3925.robot.RobotMap.POLARITY_SHOOTER_B;
import static com.team3925.robot.RobotMap.PORT_SHOOTER_A;
import static com.team3925.robot.RobotMap.PORT_SHOOTER_B;

import com.ctre.CANTalon;
import com.team3925.util.MultiSpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterFlyWheel extends Subsystem {

	private MultiSpeedController flyWheel;

	private static ShooterFlyWheel instance;

	public static ShooterFlyWheel getInstance() {
		return instance == null ? instance = new ShooterFlyWheel() : instance;
	}

	private ShooterFlyWheel() {
		flyWheel = new MultiSpeedController();
		flyWheel.addController(new CANTalon(PORT_SHOOTER_A), POLARITY_SHOOTER_A);
		flyWheel.addController(new CANTalon(PORT_SHOOTER_B), POLARITY_SHOOTER_B);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setShooter(double speed) {
		flyWheel.set(speed);
	}
}
