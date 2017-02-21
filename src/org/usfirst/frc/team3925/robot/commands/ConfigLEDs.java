
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.subsystems.LightsSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class ConfigLEDs extends Command {
	
	private static ConfigLEDs instance;
	
	public static ConfigLEDs getInstance() {
		if (instance==null)
			instance = new ConfigLEDs();
		return instance;
	}
	
	private ConfigLEDs() {
	}
	
	@Override
	protected void initialize() {
		LightsSubsystem.getInstance().setLEDRing(true);
		switch (DriverStation.getInstance().getAlliance()) {
		case Blue:
			LightsSubsystem.getInstance().setRedLights(false);
			LightsSubsystem.getInstance().setBlueLights(true);
			break;
		case Invalid:
			LightsSubsystem.getInstance().setRedLights(true);
			LightsSubsystem.getInstance().setBlueLights(true);
			break;
		case Red:
			LightsSubsystem.getInstance().setRedLights(true);
			LightsSubsystem.getInstance().setBlueLights(false);
			break;
		default:
			LightsSubsystem.getInstance().setRedLights(false);
			LightsSubsystem.getInstance().setBlueLights(false);
			break;
		};
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
	
}
