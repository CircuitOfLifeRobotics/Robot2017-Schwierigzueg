package com.team3925.robot.commands;

import com.team3925.robot.subsystems.GearIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitForGear extends Command {
	
    protected boolean isFinished() {
        return (GearIntake.getInstance().gearDetected());
    }
    
}
