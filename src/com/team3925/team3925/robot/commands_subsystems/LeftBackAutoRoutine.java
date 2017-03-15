package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftBackAutoRoutine extends CommandGroup {
	DriveDistance initialDrive = new DriveDistance(-4.5, 10, false);
	DriveTurn turn = new DriveTurn(64.5, 11);
	DriveDistance terminalDrive = new DriveDistance(-8,6, false);
	DriveDistance backUpDrive = new DriveDistance(1,11, false);
	DriveTurn reverseTurn = new DriveTurn(-20, 11);
	DriveDistance halfBack = new DriveDistance(9, 11, false);
	Shoot spool = new Shoot(0.8);
	IntakeIn intake = new IntakeIn(1);
	Shoot stopSpool = new Shoot(0);
	IntakeIn stopIntake = new IntakeIn(0);
	

    public LeftBackAutoRoutine() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(initialDrive,3);
    	addSequential(turn, 2);
    	addSequential(terminalDrive, 4);
    	addSequential(backUpDrive,1);
    	addSequential(reverseTurn, 1);
    	addSequential(spool, 10);
    	addSequential(halfBack,5);
    	addSequential(intake, 15);
    	addSequential(stopSpool);
    	addSequential(stopIntake, 0.1);
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
