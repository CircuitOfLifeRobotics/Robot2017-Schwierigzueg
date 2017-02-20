
package org.usfirst.frc.team3925.robot.commands;

import javax.net.ssl.SSLEngineResult.Status;

import edu.wpi.first.wpilibj.command.Command;

public class SetShooterCam extends Command {
	
	private static SetShooterCam instance;
	
	public static SetShooterCam getInstance() {
		if (instance==null)
			instance = new SetShooterCam();
		return instance;
	}
	
	private SetShooterCam() {
		
	}
	
	@Override
	protected void initialize() {
		StatusLogger.getInstance().setShooterStream();
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
