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
public class BoilerAuto extends CommandGroup {
	
	double turn;
	


    public BoilerAuto(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		turn = 1;
    	} else {
    		turn = -1;
    	}
    	

    	addSequential(new Timeout(.2));
    	
    	LinkedList<ChangePoint> cps = new LinkedList<ChangePoint>();
    	
    	cps.add(new ChangePoint(250		,0.70*turn,(120.87	-40		)/12));
    	cps.add(new ChangePoint(150	,0,(132.87		)/12));
    	
    	
    	addSequential(new MPDrive(cps));
    	
    	addSequential(new PlaceGear(2));
    	

    }
    
//    			OLD SEQUENCE
//  addSequential(new GyroDrive(5.51));
//	addSequential(new GyroTurn(60 * turn));
//	addSequential(new GyroDrive(7.22 ));
//	addSequential(new PlaceGear());
//    
    
    
}
