package autoRoutines;

import com.team3925.commands.GyroDrive;
import com.team3925.commands.IntakeGoDown;
import com.team3925.commands.IntakeGoUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReplaceGear extends CommandGroup {

	public ReplaceGear() {
		addSequential(new GyroDrive(2));
		addSequential(new IntakeGoDown());
		addSequential(new GyroDrive(-2));
		addSequential(new IntakeGoUp());
	}
}
