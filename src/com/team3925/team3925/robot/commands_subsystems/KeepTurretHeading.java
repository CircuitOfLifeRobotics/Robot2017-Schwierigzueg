
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Keeps the turret aimed at the current heading using the navx
 * If the required position is past limit, turret stays as close to target as possible
 * 
 */
public class KeepTurretHeading extends Command {
	
	private static KeepTurretHeading instance;
	
	private Turret turret;
	private Navx navx;
	
	public static KeepTurretHeading getInstance() {
		return (instance == null) ? instance = new KeepTurretHeading() : instance;
	}
	
	private KeepTurretHeading() {
		turret = Turret.getInstance();
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
