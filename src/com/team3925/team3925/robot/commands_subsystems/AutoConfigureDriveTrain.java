
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Automatically configures motor reversing for drivetrain subsystem
 * Uses encoders if available or voltage draw if no encoder feedback
 * Ends when configuration is done
 * 
 */
public class AutoConfigureDriveTrain extends Command {
	
	private static AutoConfigureDriveTrain instance;
	
	private DriveTrain driveTrain;
	
	public static AutoConfigureDriveTrain getInstance() {
		return (instance == null) ? instance = new AutoConfigureDriveTrain() : instance;
	}
	
	private AutoConfigureDriveTrain() {
		driveTrain = DriveTrain.getInstance();
		requires(driveTrain);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}
	
}
