package com.team3925.autoRoutines;

import java.util.LinkedList;

import com.team3925.commands.MPDrive;
import com.team3925.commands.Timeout;
import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.robot.Constants;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {
	
	double offset,turn;

    public CenterAuto(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		turn = 1;
    	} else {
    		turn = -1;
    	}

    	addSequential(new Timeout(.2));

    	LinkedList<ChangePoint> cps = new LinkedList<ChangePoint>();
    	cps.add(new ChangePoint(50		,0,(4)));
    	cps.add(new ChangePoint(25		,0,(6)));
    	
    	addSequential(new MPDrive(cps));
    	addSequential(new PlaceGear(2));
    	
    }
}
