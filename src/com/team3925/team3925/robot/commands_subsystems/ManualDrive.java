package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualDrive extends Command {
	DriveTrain drivetrain;
	double l;
	double r;

    public ManualDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivetrain = DriveTrain.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.setTeleopControlModes();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	l = Robot.stick.getRawAxis(1) + (-0.75 *Robot.wheel.getRawAxis(0));
    	r = Robot.stick.getRawAxis(1) - (-0.75 *Robot.wheel.getRawAxis(0));
    	
    	drivetrain.setRaw(l, r);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setRaw(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
