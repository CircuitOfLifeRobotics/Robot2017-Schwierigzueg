
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Ends when the turret can see the boiler (as detected by the vision system on RaspberryPi)
 * 
 */
public class WaitForTarget extends Command {
	
	private static WaitForTarget instance;
	
	private Navx navx;
	private PInterface pi;
	
	public static WaitForTarget getInstance() {
		return (instance == null) ? instance = new WaitForTarget() : instance;
	}
	
	private WaitForTarget() {
		pi = PInterface.getInstance();
		navx = Navx.getInstance();
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
