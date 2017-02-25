
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleClimber extends Command {
	
	private DriveTrainSubsystem driveTrain;
	private static ToggleClimber instance;
	private boolean engaged;
	
	public static ToggleClimber getInstance() {
		return instance == null ? instance = new ToggleClimber() : instance;
	}
	
	private ToggleClimber() {
		driveTrain = DriveTrainSubsystem.getInstance();
		engaged = false;
	}
	
	public void toggle() {
		engaged = !engaged;
		driveTrain.setClimber(engaged);
		SmartDashboard.putBoolean("Climber Engaged", engaged);
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
