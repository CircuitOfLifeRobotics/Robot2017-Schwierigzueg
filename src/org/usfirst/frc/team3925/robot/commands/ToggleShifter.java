
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleShifter extends Command {
	
	private DriveTrainSubsystem driveTrain;
	private static ToggleShifter instance;
	private boolean engaged;
	
	public static ToggleShifter getInstance() {
		return instance == null ? instance = new ToggleShifter() : instance;
	}
	
	private ToggleShifter() {
		driveTrain = DriveTrainSubsystem.getInstance();
		engaged = false;
	}
	
	public void toggle() {
		engaged = !engaged;
		driveTrain.setShifter(engaged);
		SmartDashboard.putBoolean("Shifter Engaged", engaged);
	}
	
	@Override
	protected void initialize() {
		toggle();
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
		toggle();
	}
	
	@Override
	protected void interrupted() {
	}
	
}
