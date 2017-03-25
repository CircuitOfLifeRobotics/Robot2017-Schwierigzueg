package com.team3925.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Timeout extends Command {

	private double startTime;
	private double timeout;

	public Timeout(double seconds) {
		timeout = seconds;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (Timer.getFPGATimestamp() > startTime + timeout);
	}
}
