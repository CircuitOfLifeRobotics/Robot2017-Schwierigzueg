
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Moves the turret to the limit switch and resets the encoder
 * 
 */
public class ZeroTurret extends Command {
	
	private static ZeroTurret instance;
	
	private Turret turret;
	
	public static ZeroTurret getInstance() {
		return (instance == null) ? instance = new ZeroTurret() : instance;
	}
	
	private ZeroTurret() {
		turret = Turret.getInstance();
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
