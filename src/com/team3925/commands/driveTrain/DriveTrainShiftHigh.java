package com.team3925.commands.driveTrain;

import com.team3925.subsystems.DriveTrainShifting;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrainShiftHigh extends Command {

	private DriveTrainShifting driveTrainShifting;

	public DriveTrainShiftHigh() {
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
