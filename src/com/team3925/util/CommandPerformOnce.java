package com.team3925.util;

import edu.wpi.first.wpilibj.command.Command;

public class CommandPerformOnce extends Command {
	
	private final Runnable runnable;
	private boolean hasRun;
	
	public CommandPerformOnce(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	protected void initialize() {
		hasRun = false;
	}
	
	@Override
	protected void execute() {
		if (!hasRun) {
			runnable.run();
			hasRun = true;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return hasRun;
	}
	
	@Override
	protected void end() {
		execute();
	}
	
	@Override
	protected void interrupted() {
		execute();
	}
	
}
