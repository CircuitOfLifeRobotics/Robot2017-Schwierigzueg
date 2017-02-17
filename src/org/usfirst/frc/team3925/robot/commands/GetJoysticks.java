
package org.usfirst.frc.team3925.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class GetJoysticks extends Command {
	
	private static GetJoysticks instance;
	
	public GetJoysticks getInstance() {
		if (instance == null)
			instance = new GetJoysticks();
		return instance;
	}
	
	private GetJoysticks() {
		
	}
	
	// TODO: ensure works
	public static boolean isWheel(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("thrustmaster");
	}
	
	// TODO: ensure works
	public static boolean isStick(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("attack 3");
	}
	
	// TODO: ensure works
	public static boolean isXbox(Joystick stick) {
		return stick.getIsXbox() || stick.getName().toLowerCase().contains("xbox");
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		super.end();
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		super.interrupted();
	}
	
}
