package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3925.robot.Robot;
import org.usfirst.frc.team3925.robot.TimeoutAction;

/**
 *
 */
public class ToggleClimber extends Command {
	DriveTrain drivetrain;
	boolean isSet= false;
	TimeoutAction timeout;
	double time = 1;
	

    public ToggleClimber() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivetrain = drivetrain.getInstance();
    	timeout = new TimeoutAction();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.xbox.getRawButton(3) && timeout.isFinished()){
    		if (isSet){
    			drivetrain.setClimber(false);
    			isSet = false;
    			Robot.xbox.setRumble(RumbleType.kLeftRumble, 0);
    			Robot.xbox.setRumble(RumbleType.kRightRumble, 0);
    		}else if(!isSet){
    			drivetrain.setClimber(true);
    			isSet = true;
    			Robot.xbox.setRumble(RumbleType.kLeftRumble, 0.3);
    			Robot.xbox.setRumble(RumbleType.kRightRumble, 0.3);
    		}
    		timeout.config(time);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setClimber(isSet);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
