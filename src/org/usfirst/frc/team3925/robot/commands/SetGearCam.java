
package org.usfirst.frc.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class SetGearCam extends Command {
	
	private static SetGearCam instance;
	
	public static SetGearCam getInstance() {
		if (instance == null)
			instance = new SetGearCam();
		return instance;
	}
	
	private SetGearCam() {
		
	}
	
	@Override
	protected void initialize() {
		StatusLogger.getInstance().setGearStream();
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
	
}
