
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SetShifterLow extends Command{
	
	private DriveTrainSubsystem driveTrain;
	private static SetShifterLow instance;
	
	public static SetShifterLow getInstance() {
		return instance == null ? instance = new SetShifterLow() : instance;
	}
	
	private SetShifterLow() {
		driveTrain = DriveTrainSubsystem.getInstance();
	}
	
	public void setLow() {
		driveTrain.setShifter(false);
	}
	
	@Override
	protected void initialize() {
		setLow();
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
	
}
