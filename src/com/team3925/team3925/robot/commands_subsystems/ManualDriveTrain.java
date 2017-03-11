
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Controls the drivetrain using input from a ManualDriveTrainInput (intended: joysticks from DriverStation)
 * 
 */
public class ManualDriveTrain extends Command {
	
	public interface ManualDriveTrainInput {
		double getForward();
		double getTurn();
	}
	
	private static ManualDriveTrain instance;
	private static ManualDriveTrainInput input;
	
	private DriveTrain driveTrain;
	
	public ManualDriveTrain setInput(ManualDriveTrainInput input) {
		this.input = input;
		return this;
	}
	
	public static ManualDriveTrain getInstance() {
		return (instance == null) ? instance = new ManualDriveTrain() : instance;
	}
	
	private ManualDriveTrain() {
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