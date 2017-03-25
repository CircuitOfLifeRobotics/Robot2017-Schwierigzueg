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
		driveTrainShifting.setLowGear();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
}
