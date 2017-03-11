
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Controls the turret using input from a ManualTurretInput (intended: joysticks from DriverStation)
 * 
 */
public class ManualTurret extends Command {
	
	public interface ManualTurretInput {
		double getForward();
		double getTurn();
	}
	
	private static ManualTurret instance;
	private static ManualTurretInput input;
	
	private Turret turret;
	
	public ManualTurret setInput(ManualTurretInput input) {
		this.input = input;
		return this;
	}
	
	public static ManualTurret getInstance() {
		return (instance == null) ? instance = new ManualTurret() : instance;
	}
	
	private ManualTurret() {
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