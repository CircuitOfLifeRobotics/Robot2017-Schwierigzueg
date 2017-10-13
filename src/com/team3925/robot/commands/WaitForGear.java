
package com.team3925.robot.commands;

import static com.team3925.robot.RobotMap.MIN_TIME_GEAR_DETECTED_FOR;

import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForGear extends Command {

	private double timeDetected;

	@Override
	protected void initialize() {
		timeDetected = GearIntake.getInstance().gearDetected() ? Timer.getFPGATimestamp() : -1;
	}

	@Override
	protected void execute() {
		if (timeDetected >= Timer.getFPGATimestamp() && !GearIntake.getInstance().gearDetected())
			timeDetected = -1;
		System.out.println(timeDetected);
	}

	protected boolean isFinished() {
		return timeDetected >= 0 && Timer.getFPGATimestamp() - timeDetected > MIN_TIME_GEAR_DETECTED_FOR;
	}

}
