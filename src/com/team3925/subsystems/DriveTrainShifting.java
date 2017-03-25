package com.team3925.subsystems;

import static com.team3925.robot.RobotMap.SOLENOID_PORT_A_SHIFT;
import static com.team3925.robot.RobotMap.SOLENOID_PORT_B_SHIFT;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrainShifting extends Subsystem {

	private static DriveTrainShifting driveTrain;
	private DoubleSolenoid solenoid;

	public static DriveTrainShifting getInstance() {
		if (driveTrain == null)
			driveTrain = new DriveTrainShifting();
		return driveTrain;
	}

	private DriveTrainShifting() {
		solenoid = new DoubleSolenoid(SOLENOID_PORT_A_SHIFT, SOLENOID_PORT_B_SHIFT);
	}

	@Override
	protected void initDefaultCommand() {
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