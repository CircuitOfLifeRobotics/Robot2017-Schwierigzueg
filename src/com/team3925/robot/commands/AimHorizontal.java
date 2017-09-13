package com.team3925.robot.commands;

import com.team3925.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AimHorizontal extends Command {
	
	private GyroTurn gyroTurn;
	private double startTime;
	private static final double INIT_WAIT_TIME = 0.3;
	
	public AimHorizontal() {
		gyroTurn = new GyroTurn();
		startTime = 0;
	}
	
	@Override
	protected void initialize() {
		Robot.CAMERA_COMMAND.start();
		startTime = Timer.getFPGATimestamp();
	}
	
	@Override
	protected void execute() {
		System.out.println("HorizOffset = "+Robot.CAMERA_COMMAND.getHorizontalOffsetAngle());
		System.out.println("Gyro turn running?"+gyroTurn.isRunning());
		if (!gyroTurn.isRunning() && Timer.getFPGATimestamp()-startTime>INIT_WAIT_TIME)
			gyroTurn.start();
	}
	
	@Override
	protected boolean isFinished() {
		return gyroTurn.isFinished() && Timer.getFPGATimestamp()-startTime>INIT_WAIT_TIME;
	}
	
	@Override
	protected void end() {
		Robot.CAMERA_COMMAND.cancel();
		
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
}
