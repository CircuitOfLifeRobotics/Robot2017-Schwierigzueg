package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KillShooter extends Command {
	
	Shooter shooter;
    public KillShooter() {
        shooter = shooter.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.setSpeed(0);
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
