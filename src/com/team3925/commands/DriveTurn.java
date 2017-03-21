package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTurn extends Command {
	
	private DriveTrain driveTrain;
	
	public DriveTurn(double angle) {
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
