package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoDriveTimed extends Command {
	
	private DriveTrain driveTrain;
	
	private static AutoDriveTimed instance;
	private double startTime;
	private double timeToDrive;
	
	public static AutoDriveTimed getInstance() {
		return (instance==null) ? instance=new AutoDriveTimed():instance;
	}
	
	private AutoDriveTimed() {
		driveTrain = DriveTrain.getInstance();
		driveTrain.shiftLow(true);
		driveTrain.setTeleopControlModes();
		driveTrain.setMaxVoltage(50);
	}
	
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
		timeToDrive = 7;
		driveTrain.setRaw(.3, .3);
		System.out.println("Timed Drive");
	}
	
	@Override
	protected boolean isFinished() {
		return Timer.getFPGATimestamp()>=startTime+timeToDrive;
	}
	
	@Override
	protected void end() {
		System.out.println("finsihdiedd");
		driveTrain.setRaw(0, 0);
	}
	@Override
	protected void interrupted() {
		this.end();
	}
	
}
