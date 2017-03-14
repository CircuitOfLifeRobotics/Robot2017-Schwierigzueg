package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ToggleClimber extends Command{
	private DriveTrain driveTrain;
	private static ToggleClimber instance;
	private boolean engaged;
	
	public static ToggleClimber getInstance() {
		return instance == null ? instance = new ToggleClimber() : instance;
	}
	
	private ToggleClimber() {
		driveTrain = DriveTrain.getInstance();
		engaged = false;
	}
	
	public void toggle() {
		engaged = !engaged;
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
