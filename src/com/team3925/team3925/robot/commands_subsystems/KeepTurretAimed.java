
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Keeps turret pointed towards boiler using values computed by vision system on RaspberryPi
 * If the required position is past limit, turret stays as close to target as possible
 * Ends when values computed by vision system become invalid
 * 
 */
public class KeepTurretAimed extends Command {
	
	private static KeepTurretAimed instance;
	
	private Turret turret;
	private PInterface pi;
	private Navx navx;
	
	public static KeepTurretAimed getInstance() {
		return (instance == null) ? instance = new KeepTurretAimed() : instance;
	}
	
	private KeepTurretAimed() {
		turret = Turret.getInstance();
		pi = PInterface.getInstance();
		navx = Navx.getInstance();
		requires(turret);
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
