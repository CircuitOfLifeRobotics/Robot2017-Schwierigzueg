package com.team3925.autoRoutines;

import java.util.LinkedList;

import com.team3925.commands.MPDrive;
import com.team3925.commands.Timeout;
import com.team3925.commands.compound.TurnShoot;
import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.robot.Constants;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterShootAuto extends CommandGroup {
	
	double turn;
	


    public CenterShootAuto(String side) {
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
    	addSequential(new PlaceGear(.5));

    	addSequential(new MPDrive(new ChangePoint(-75, -.4*turn, 1.5)));
    	addSequential(new MPDrive(new ChangePoint(50, 1*turn, 2.75)));
    	addSequential(new TurnShoot(0));    	

    }
    
//    			OLD SEQUENCE
//  addSequential(new GyroDrive(5.51));
//	addSequential(new GyroTurn(60 * turn));
//	addSequential(new GyroDrive(7.22 ));
//	addSequential(new PlaceGear());
//    
    
    
}
