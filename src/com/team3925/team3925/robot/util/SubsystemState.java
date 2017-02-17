
package com.team3925.team3925.robot.util;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SubsystemState {
	
	public abstract Subsystem getSubsystem();
	
	public abstract void timeProject(double t);
	
}
