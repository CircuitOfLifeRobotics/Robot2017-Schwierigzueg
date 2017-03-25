package com.team3925.commands;

import com.team3925.subsystems.DriveTrainShifting;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrainShiftHigh extends Command {

	private DriveTrainShifting driveTrainShifting;

	private static DriveTrainShiftHigh instance;

	public static DriveTrainShiftHigh getInstance() {
		if (instance == null)
			instance = new DriveTrainShiftHigh();
		return instance;
	}

	private DriveTrainShiftHigh() {
		driveTrainShifting = DriveTrainShifting.getInstance();
		requires(driveTrainShifting);
	}

	@Override
	protected void initialize() {
		driveTrainShifting.setHighGear();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
