
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SetShifterHigh extends Command{
	
	private DriveTrainSubsystem driveTrain;
	private static SetShifterHigh instance;
	
	public static SetShifterHigh getInstance() {
		return instance == null ? instance = new SetShifterHigh() : instance;
	}
	
	private SetShifterHigh() {
		driveTrain = DriveTrainSubsystem.getInstance();
	}
	
	public void setHigh() {
		driveTrain.setShifter(false);
	}
	
	@Override
	protected void initialize() {
		setHigh();
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
