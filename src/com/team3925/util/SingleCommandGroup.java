package com.team3925.util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SingleCommandGroup extends CommandGroup {
	
	public SingleCommandGroup(Command command) {
		addSequential(command);
	}
	
	public SingleCommandGroup() {}
	
}
