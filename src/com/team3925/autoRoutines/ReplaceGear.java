package com.team3925.autoRoutines;

import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.commands.intake.IntakeGoUp;
import com.team3925.commands.intake.IntakeWheelsIn;
import com.team3925.commands.intake.IntakeWheelsOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReplaceGear extends CommandGroup {

	public ReplaceGear(int replaceAmount) {
		
		if (replaceAmount >= 1){
			replace();
		}
		if (replaceAmount >= 2){
			replace();
		}
		if (replaceAmount >= 3){
			replace();
		}
		
	}
	public void replace() {
		addSequential(new IntakeWheelsIn());
		addSequential(new GyroDrive(2));
		addSequential(new IntakeWheelsOff());
		addSequential(new IntakeGoDown());
		addSequential(new GyroDrive(-2));
		addSequential(new IntakeGoUp());
	}
}
