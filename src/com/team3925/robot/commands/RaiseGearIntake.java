package com.team3925.robot.commands;

import com.team3925.robot.RobotMap;
import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseGearIntake extends Command {
	
	private double startTime;

    public RaiseGearIntake() {
        requires(GearIntake.getInstance());
    }

    protected void initialize() {
    	GearIntake.getInstance().setSolenoid(true);
    	startTime = Timer.getFPGATimestamp();
    }
    
    protected boolean isFinished() {
        return (Timer.getFPGATimestamp() > startTime + RobotMap.INTAKE_PICKUP_TIMEOUT);
    }
    
    protected void end(){
    	GearIntake.getInstance().setMotor(0);
    }
    
    protected void interrupted(){
    	this.end();
    }
}
