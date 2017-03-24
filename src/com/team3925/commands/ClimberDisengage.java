package com.team3925.commands;

import com.team3925.subsystems.ClimberShifting;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberDisengage extends Command {
	
	private ClimberShifting climberShifting;
	
	private static ClimberDisengage instance;
	
	public static ClimberDisengage getInstance() {
		if (instance==null)
			instance = new ClimberDisengage();
		return instance;
	}
	
	private ClimberDisengage() {
		climberShifting = ClimberShifting.getInstance();
		requires(climberShifting);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}
	
}
