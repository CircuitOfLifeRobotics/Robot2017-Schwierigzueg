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
public class HopperAuto extends CommandGroup {
	
	double turn;
	


    public HopperAuto(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		turn = 1;
    	} else {
    		turn = -1;
    	}

    	addSequential(new Timeout(.2));

    	LinkedList<ChangePoint> cps = new LinkedList<ChangePoint>();
    	cps.add(new ChangePoint(53.3		,0,(2.7)));
    	cps.add(new ChangePoint(53.3		,-0.01*turn,(5.850)));
    	cps.add(new ChangePoint(26.6		,0,(7)));
    	
    	addSequential(new MPDrive(cps));
    	addSequential(new Timeout(1));
    	addSequential(new MPDrive(new ChangePoint(-26.6, 0, .5)));
    	addSequential(new TurnShoot(-90*turn));
    	

    }
    
//    			OLD SEQUENCE
//  addSequential(new GyroDrive(5.51));
//	addSequential(new GyroTurn(60 * turn));
//	addSequential(new GyroDrive(7.22 ));
//	addSequential(new PlaceGear());
//    
    
    
}
