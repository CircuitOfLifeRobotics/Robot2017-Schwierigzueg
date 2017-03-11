
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Drives robot to middle of gear peg using values computed by vision system on RaspberryPi
 * Ends when robot is 4 inches from airship
 *
 */
public class DriveGear extends Command {
	
	private static DriveGear instance;
	
	private DriveTrain driveTrain;
	private PInterface pi;
	private Navx navx;
	
	public static DriveGear getInstance() {
		return (instance == null) ? instance = new DriveGear() : instance;
	}
	
	private DriveGear() {
		driveTrain = DriveTrain.getInstance();
		pi = PInterface.getInstance();
		navx = Navx.getInstance();
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
