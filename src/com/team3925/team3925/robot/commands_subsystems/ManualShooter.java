
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 * Controls the shooter using input from a ManualShooterInput (intended: joysticks from DriverStation)
 * 
 */
public class ManualShooter extends Command {
	
	public interface ManualShooterInput {
		double getSpeed();
	}
	
	private static ManualShooter instance;
	private static ManualShooterInput input;
	
	private Shooter shooter;
	
	public ManualShooter setInput(ManualShooterInput input) {
		this.input = input;
		return this;
	}
	
	public static ManualShooter getInstance() {
		return (instance == null) ? instance = new ManualShooter() : instance;
	}
	
	private ManualShooter() {
		shooter = Shooter.getInstance();
		requires(shooter);
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