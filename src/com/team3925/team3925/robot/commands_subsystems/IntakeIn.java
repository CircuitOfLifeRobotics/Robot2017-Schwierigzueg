package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeIn extends Command {
	
	Intake intake;
	double speed;
	boolean finished;
    public IntakeIn(double percentage) {
        intake = intake.getInstance();
        speed = percentage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.runIntake(speed);
    	finished = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
