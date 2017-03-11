
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Moves the turret from limit to limit
 * 
 */
public class PanTurret extends Command {
	
	private static PanTurret instance;
	
	private Turret turret;
	private double period;
	
	public static PanTurret getInstance() {
		return (instance == null) ? instance = new PanTurret() : instance;
	}
	
	private PanTurret() {
		turret = Turret.getInstance();
		requires(turret);
	}
	
	public void setPeriod(double period) {
		this.period = period;
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
