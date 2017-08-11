package com.team3925.util;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command turns on a thing, waits some time, and then turns that thing
 * off. Useful for buttons and stuff.
 * 
 * @author Adam Mendenhall
 */
public class CommandTimedOnOff extends Command {

	private double initTime;
	private double time = 0.2;
	private Consumer<Boolean> thingToToggle;

	public CommandTimedOnOff(double time, Consumer<Boolean> thingToToggleForSomeTime) {
		thingToToggle = thingToToggleForSomeTime;
		this.time = time;
	}

	@Override
	protected void initialize() {
		initTime = Timer.getFPGATimestamp();
		thingToToggle.accept(true);
	}

	@Override
	protected boolean isFinished() {
		return Timer.getFPGATimestamp() - initTime > time;
	}

	@Override
	protected void end() {
		thingToToggle.accept(false);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
