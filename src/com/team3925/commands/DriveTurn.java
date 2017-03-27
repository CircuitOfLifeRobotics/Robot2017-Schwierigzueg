package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTurn extends PIDCommand {
	
	private DriveTrain driveTrain;
	private Navx navx;
	
	private double initYaw;
	private double goalOffsetYaw;
	private final double offsetYaw;
	private double lastYaw;
	private double currYaw;
	private double rotations;
	
	private double yawSetpointIncrement;
	private double yawSetpointIncrementIncrement;
	
	public DriveTurn(double deltaHeading) {
		super(0.01, 0, 0);
		driveTrain = DriveTrain.getInstance();
		navx = Navx.getInstance();
		requires(driveTrain);
		offsetYaw = deltaHeading;
		yawSetpointIncrementIncrement = 0.2;
	}
	
	@Override
	protected void initialize() {
		initYaw = navx.getHeading();
		goalOffsetYaw = offsetYaw + initYaw;
		rotations = 0;
		yawSetpointIncrement = 1;
		setSetpoint(initYaw);
	}
	
	@Override
	protected void execute() {
		currYaw = navx.getHeading();
		if (Math.abs(currYaw - lastYaw) >= 180)
			rotations += Math.signum(lastYaw - currYaw);
		
		if (getTotalYaw() - initYaw < 0.5 * offsetYaw)
			yawSetpointIncrement = Math.min(1, yawSetpointIncrement + yawSetpointIncrementIncrement);
		else
			yawSetpointIncrement = Math.max(0, yawSetpointIncrement - yawSetpointIncrementIncrement);
		setSetpoint(getTotalYaw() + yawSetpointIncrement);
		
		lastYaw = navx.getHeading();
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(getTotalYaw() - initYaw - offsetYaw) < 1;
	}
	
	@Override
	protected void end() {
		driveTrain.setSideRaw(0, 0);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
	@Override
	protected double returnPIDInput() {
		return getTotalYaw();
	}
	
	@Override
	protected void usePIDOutput(double output) {
		driveTrain.setSideRaw(Math.max(-1, Math.min(1, output)), -Math.max(-1, Math.min(1, output)));
		
	}
	
	private double getTotalYaw() {
		return currYaw + 360 * rotations;
	}
	
}
