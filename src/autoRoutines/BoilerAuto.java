package autoRoutines;

import com.team3925.commands.GyroDrive;
import com.team3925.commands.GyroTurn;
import com.team3925.commands.IntakeGoDown;
import com.team3925.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BoilerAuto extends CommandGroup {
	
	double offset;
	double turn;


    public BoilerAuto(String side) {
    	if (side.equalsIgnoreCase("BLUE")){
    		offset = Constants.AUTO_BLUE_OFFSET;
    		turn = 1;
    	} else {
    		offset = Constants.AUTO_RED_OFFSET;
    		turn = -1;
    	}
    	
    	addSequential(new GyroDrive(5.51 + offset));
    	addSequential(new GyroTurn(60 * turn));
    	addSequential(new GyroDrive(7.22 + offset));
    	addSequential(new PlaceGear());
    	addSequential(new ReplaceGear());

    }
}
