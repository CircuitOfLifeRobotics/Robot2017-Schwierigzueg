package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAutoRight extends CommandGroup {
	
	DriveDistance shiftTime;
	DriveDistance drive;
	DriveDistance backUp;
	DriveTurn turn;
	DriveDistance toBoiler;
	DriveDistance terminalDrive;
	DriveDistance backBoiler;
	Shoot spool;
	KillShooter stopSpool;
	DriveTurn boilerAlign;
	IntakeIn runIntake;
	IntakeIn stopIntake;
	DriveDistance finalDrive;

    public CenterAutoRight() {
    	shiftTime = new DriveDistance(-0.1, 0, false);
    	drive = new DriveDistance(-7, 6, false);
    	finalDrive = new DriveDistance(-1, 6, false);
    	backUp = new DriveDistance(3.3333, 11,  false);
    	turn = new DriveTurn(-93, 11);
    	toBoiler = new DriveDistance(9.5, 10, false);
    	boilerAlign = new DriveTurn(55, 11);
    	spool = new Shoot(0.97);
    	terminalDrive = new DriveDistance(3.5, 9, false);
    	backBoiler = new DriveDistance(-0.4, 5, false);
    	runIntake = new IntakeIn(1);
    	stopIntake = new IntakeIn(0);
    	stopSpool = new KillShooter();
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order
    	addSequential(shiftTime, 0.25);
    	addSequential(drive, 4);
    	addSequential(backUp, 2);
    	addSequential(turn, 1.5);
    	addSequential(toBoiler, 4.5);
    	addSequential(spool);
    	addSequential(boilerAlign, 1);
    	addSequential(terminalDrive, 1);
    	addSequential(runIntake, 10);
    	addSequential(stopSpool);
    	addSequential(stopIntake, 0.1);
    	

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
