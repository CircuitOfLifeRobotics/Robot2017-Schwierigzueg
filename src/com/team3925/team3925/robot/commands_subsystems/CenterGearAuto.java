package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.TimeoutAction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearAuto extends CommandGroup {
	TimeoutAction timeout;
	DriveDistance drivedistance;
	

    public CenterGearAuto() {
    	timeout = new TimeoutAction(2);
    	drivedistance = new DriveDistance(6.3, 3, false);
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(timeout);
    	addSequential(drivedistance);

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
