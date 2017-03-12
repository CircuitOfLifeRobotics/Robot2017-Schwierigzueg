package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.TimeoutAction;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearUltrasoundWait extends Command {
	
	DriveTrain drivetrain;
	TimeoutAction waitForGearOut;
	boolean gearStatus;

    public GearUltrasoundWait() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivetrain = drivetrain.getInstance();
    	gearStatus = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (drivetrain.getGearStatus()){
        	return true;
        }else{
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
