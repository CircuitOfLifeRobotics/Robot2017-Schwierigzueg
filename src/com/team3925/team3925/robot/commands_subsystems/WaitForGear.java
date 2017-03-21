
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Ends when the gear leaves the robot (as detected by the ultrasonic sensor on the drivetrain)
 * 
 */
public class WaitForGear extends Command {
	
	private static WaitForGear instance;
	
	private DriveTrain driveTrain;
	
	public static WaitForGear getInstance() {
		return (instance == null) ? instance = new WaitForGear() : instance;
	}
	
	private WaitForGear() {
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
