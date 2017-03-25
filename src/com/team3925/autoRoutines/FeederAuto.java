package com.team3925.autoRoutines;

import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FeederAuto extends CommandGroup {
	
	double offset;
	double turn;


    public FeederAuto(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		offset = Constants.AUTO_BLUE_OFFSET;
    		turn = 1;
    	} else {
    		offset = Constants.AUTO_RED_OFFSET;
    		turn = -1;
    	}
    	
    	addSequential(new GyroDrive(6.77 + (0.448 * offset)));
    	addSequential(new GyroTurn(-90 * turn));
    	addSequential(new GyroDrive(7.34 + (0.866 * offset)));
    	addSequential(new PlaceGear(2));

    }
}
