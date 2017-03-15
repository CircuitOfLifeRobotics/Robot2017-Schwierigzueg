package com.team3925.team3925.robot.commands_subsystems;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3925.robot.Robot;

/**
 *
 */
public class ShiftLow extends Command {
	DriveTrain drivetrain;
	Joystick wheel;
	
    public ShiftLow() {
        drivetrain = DriveTrain.getInstance();
        wheel = Robot.wheel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (wheel.getRawButton(5)){
    		drivetrain.shiftLow(true);
    	}else{
    		drivetrain.shiftLow(false);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.shiftLow(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
