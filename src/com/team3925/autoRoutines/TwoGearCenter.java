package com.team3925.autoRoutines;

import com.team3925.commands.driveTrain.GyroDrive;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.commands.intake.IntakeGoUp;
import com.team3925.commands.intake.IntakeWheelsIn;
import com.team3925.commands.intake.IntakeWheelsOff;
import com.team3925.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoGearCenter extends CommandGroup {
	
	double offset;
	double turn;

    public TwoGearCenter(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		offset = Constants.AUTO_BLUE_OFFSET;
    		turn = 1;
    	} else {
    		offset = Constants.AUTO_RED_OFFSET;
    		turn = -1;
    	}
    	
    	addSequential(new GyroDrive(6.08 + offset));
    	addSequential(new IntakeGoDown());
    	
    	addSequential(new GyroDrive(-1.84 - offset));
    		addSequential(new GyroTurn(90*turn));
    			addSequential(new GyroDrive(2.5));
    				addSequential(new GyroTurn(90*turn));
    					addSequential(new IntakeWheelsIn());
    						addSequential(new GyroDrive(2.49));
    							addSequential(new IntakeGoUp());	
    						addSequential(new GyroDrive(-2.49));
    					addSequential(new IntakeWheelsOff());
    				addSequential(new GyroTurn(-90*turn));
    			addSequential(new GyroDrive(-2.2));
    		addSequential(new GyroTurn(-90*turn));
    	addSequential(new GyroDrive(2.5 + offset));
    	
    	addSequential(new PlaceGear(2));
    	
    }
}
