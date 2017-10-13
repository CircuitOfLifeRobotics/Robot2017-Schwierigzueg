package com.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPickup extends CommandGroup {

    public AutoPickup() {
    	addSequential(new LowerGearIntakeAndSetMotor());
    	addSequential(new WaitForGear());
    	addSequential(new RaiseGearIntake());
    }
    
}
