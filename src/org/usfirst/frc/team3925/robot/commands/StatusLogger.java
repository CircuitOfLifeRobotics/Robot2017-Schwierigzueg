
package org.usfirst.frc.team3925.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class StatusLogger extends Command {
	
	private static StatusLogger instance;
	
	private NetworkTable table;
	
	public static StatusLogger getInstance() {
		if (instance==null)
			instance = new StatusLogger();
		return instance;
	}
	
	private StatusLogger() {
		table = NetworkTable.getTable("Status");
		table.putBoolean("Running", false);
		//Camera Index: 0=turret, 1=gear
		table.putNumber("Camera Index", 0);
		table.putString("Controls Configuration", "none");
	}
	
	@Override
	protected void initialize() {
		table.putBoolean("Running", true);
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		table.putBoolean("Running", false);
	}
	
	@Override
	protected void interrupted() {
		table.putBoolean("Running", false);
	}
	
	public void setShooterStream() {
		table.putNumber("Camera Index", 0);
	}
	
	public void setGearStream() {
		table.putNumber("Camera Index", 1);
	}
	
	public void setControlsConfiguration(String configuration) {
		table.putString("Controls Configuration", configuration);
	}
	
}
