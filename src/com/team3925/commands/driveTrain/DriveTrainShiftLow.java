package com.team3925.commands.driveTrain;

import com.team3925.subsystems.DriveTrainShifting;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrainShiftLow extends Command {

	private DriveTrainShifting driveTrainShifting;

	public DriveTrainShiftLow() {
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
