package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.Robot;
import org.usfirst.frc.team3925.robot.TimeoutAction;

import com.team3925.team3925.robot.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunShooter extends Command {

	Shooter shooter;
	Intake intake;
	Joystick xbox;
	
    public RunShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	shooter = Shooter.getInstance();
    	intake = Intake.getInstance();
    	xbox = Robot.xbox;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (xbox.getRawButton(2)){
    		if (shooter.getEncVelocity() > Constants.SHOOTER_TARGET_SPEED - 500 ){
    			intake.runIntake(0.7);
    		}
    		if (shooter.getEncVelocity() < Constants.SHOOTER_TARGET_SPEED ){
    			shooter.setSpeed(Constants.SHOOTER_SPEED + 0.10);
    		}else if (shooter.getEncVelocity() > Constants.SHOOTER_TARGET_SPEED ){
    			shooter.setSpeed(Constants.SHOOTER_SPEED - 0.10);
    		}else {
    			
    		}
    		SmartDashboard.putNumber("Speed", shooter.getPercent());
        	SmartDashboard.putNumber("ENCODERSPEEDZ", shooter.getEncVelocity());
    	}else if(xbox.getRawButton(1)){
    		intake.intakeBalls(1);
    	}
    	else{
    		shooter.setSpeed(0);
        	intake.intakeBalls(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	shooter.setSpeed(0);
    	intake.runIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
