package com.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoRelease extends CommandGroup {

    public AutoRelease() {
    	addSequential(new LowerGearIntake());
    	addSequential(new WaitForGear());
    	addSequential(new RaiseGearIntake());
    }
    
}
