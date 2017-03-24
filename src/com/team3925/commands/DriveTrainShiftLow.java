package com.team3925.commands;

import com.team3925.subsystems.DriveTrainShifting;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrainShiftLow extends Command {
	
	private DriveTrainShifting driveTrainShifting;
	
	private static DriveTrainShiftLow instance;
	
	public static DriveTrainShiftLow getInstance() {
		if (instance==null)
			instance = new DriveTrainShiftLow();
		return instance;
	}
	
	private DriveTrainShiftLow() {
		driveTrainShifting = DriveTrainShifting.getInstance();
		requires(driveTrainShifting);
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
