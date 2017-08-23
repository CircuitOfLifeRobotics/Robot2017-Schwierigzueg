package com.team3925.robot.subsystems;

import com.ctre.CANTalon;
import com.team3925.util.MiscMath;

import static com.team3925.robot.RobotMap.PORT_CLIMBER;
import static com.team3925.robot.RobotMap.POLARITY_CLIMBER;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	
	private CANTalon talon;
	private static Climber instance;

	private Climber() {
		talon = new CANTalon(PORT_CLIMBER);
		talon.setInverted(POLARITY_CLIMBER);
		talon.enableBrakeMode(true);
	}

	public static Climber getInstance() {
		return instance == null ? instance = new Climber() : instance;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void setRaw(double value) {
		talon.set(MiscMath.capRange(-1, 1, value));
	}
	
}
