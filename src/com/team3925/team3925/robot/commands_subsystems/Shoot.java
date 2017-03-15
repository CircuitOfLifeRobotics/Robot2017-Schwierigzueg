package com.team3925.team3925.robot.commands_subsystems;

import com.team3925.team3925.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shoot extends Command {
	
	Shooter shooter;
	Intake intake;
	double speed;
	boolean finished;
    public Shoot(double percentage) {
        shooter = shooter.getInstance();
        speed = percentage;
        intake = intake.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shooter.setSpeed(speed);
    	finished = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (shooter.getEncVelocity() > Constants.SHOOTER_TARGET_SPEED - 500 ){
			intake.runIntake(0.7);
		}
		if (shooter.getEncVelocity() < Constants.SHOOTER_TARGET_SPEED ){
			shooter.setSpeed(Constants.SHOOTER_SPEED + 0.10);
		}else if (shooter.getEncVelocity() > Constants.SHOOTER_TARGET_SPEED ){
			shooter.setSpeed(Constants.SHOOTER_SPEED - 0.10);
		}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
