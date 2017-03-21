package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveManual extends Command {
	
	private DriveTrain driveTrain;
	
	private static DriveManual instance;
	
	public static DriveManual getInstance() {
		if (instance==null)
			instance = new DriveManual();
		return instance;
	}
	
	private DriveManual() {
		driveTrain = DriveTrain.getInstance();
		requires(driveTrain);
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
