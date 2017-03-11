
package com.team3925.team3925.robot.commands_subsystems;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Drives robot along path
 * Ends when robot is at endpoint of path
 * 
 */
public class DrivePath extends Command {
	
	private DriveTrain driveTrain;
	private Navx navx;
	
	public DrivePath(LinkedList<Double[]> waypoints) {
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
