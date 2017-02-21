package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.Robot;
import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class POST extends Command{
	DriveTrainSubsystem drivetrain;
	public POST() {
		drivetrain = Robot.drivetrain;
	}
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
