package autoRoutines;

import com.team3925.commands.GyroDrive;
import com.team3925.commands.GyroTurn;
import com.team3925.commands.IntakeGoDown;
import com.team3925.commands.IntakeGoUp;
import com.team3925.commands.IntakeWheelsIn;
import com.team3925.commands.IntakeWheelsOff;
import com.team3925.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoGearCenter extends CommandGroup {
	
	double offset;

    public TwoGearCenter(String side) {
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
    	addSequential(new IntakeGoDown());
    	addSequential(new GyroDrive(-1.84 - offset));
    	addSequential(new GyroTurn(90));
    	addSequential(new GyroDrive(2.5));
    	addSequential(new GyroTurn(90));
    	addSequential(new IntakeWheelsIn());
    	addSequential(new GyroDrive(2.49));
    	addSequential(new IntakeGoUp());
    	addSequential(new GyroDrive(-2.49));
    	addSequential(new IntakeWheelsOff());
    	addSequential(new GyroTurn(-90));
    	addSequential(new GyroDrive(-2.2));
    	addSequential(new GyroTurn(-90));
    	addSequential(new GyroDrive(2.5 + offset));
    	addSequential(new PlaceGear());
    //	addSequential(new ReplaceGear());
    	
    }
}
