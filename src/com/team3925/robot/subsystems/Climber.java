package com.team3925.robot.subsystems;

import com.ctre.CANTalon;
import com.team3925.util.MiscMath;

import static com.team3925.robot.RobotMap.PORT_CLIMBER_A;
import static com.team3925.robot.RobotMap.PORT_CLIMBER_B;
import static com.team3925.robot.RobotMap.POLARITY_CLIMBER_A;
import static com.team3925.robot.RobotMap.POLARITY_CLIMBER_B;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
	
	private CANTalon talonA, talonB;
	private static Climber instance;

	private Climber() {
		talonA = new CANTalon(PORT_CLIMBER_A);
		talonA.setInverted(POLARITY_CLIMBER_A);
		talonA.enableBrakeMode(true);
		talonB = new CANTalon(PORT_CLIMBER_B);
		talonB.setInverted(POLARITY_CLIMBER_B);
		talonB.enableBrakeMode(true);
	}

	public static Climber getInstance() {
		return instance == null ? instance = new Climber() : instance;
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void setRaw(double value) {
		talonA.set(MiscMath.capRange(-1, 1, value));
		talonB.set(MiscMath.capRange(-1, 1, value));
	}
	
}
