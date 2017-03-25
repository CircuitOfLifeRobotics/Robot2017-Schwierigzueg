package com.team3925.autoRoutines;

import java.util.LinkedList;

import com.team3925.commands.MPDrive;
import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.commands.intake.IntakeGoUp;
import com.team3925.robot.Constants;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceGear extends CommandGroup {

	public PlaceGear(double driveBack) {
		addSequential(new IntakeGoDown());
		addSequential(new MPDrive(new ChangePoint(-150, 0, driveBack)));
		addSequential(new IntakeGoUp());
	}
}
