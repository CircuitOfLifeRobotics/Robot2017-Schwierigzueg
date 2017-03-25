package com.team3925.autoRoutines;

import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {
	
	double offset;

    public CenterAuto(String side) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	if (side.equalsIgnoreCase("BLUE")){
    		offset = Constants.AUTO_BLUE_OFFSET;
    	} else {
    		offset = Constants.AUTO_RED_OFFSET;
    	}
    	
    	addSequential(new GyroDrive(6.08 + offset));
    	addSequential(new PlaceGear(2));
    	
    }
}
