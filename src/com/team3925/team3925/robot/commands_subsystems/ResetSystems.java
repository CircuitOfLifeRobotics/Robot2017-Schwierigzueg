package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetSystems extends Command {
	Intake intake;
	Shooter shooter;
	DriveTrain drivetrain;

    public ResetSystems() {
    	intake = intake.getInstance();
    	shooter = shooter.getInstance();
    	drivetrain = DriveTrain.getInstance();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.setSpeed(0);
    	intake.runIntake(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}