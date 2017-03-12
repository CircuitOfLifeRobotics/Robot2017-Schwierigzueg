package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RESET_AUTO extends CommandGroup {
	DriveDistance initialDrive = new DriveDistance(4.75, 5, false);
	DriveTurn turn = new DriveTurn(-65, 10);
	DriveDistance terminalDrive = new DriveDistance(6,5, false);
	

    public RESET_AUTO() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(terminalDrive);
    	addSequential(turn);
    	addSequential(initialDrive);

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
