package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForward extends PIDCommand {
	
	private DriveTrain driveTrain;
	private Navx navx;
	
	private double initYaw;
	private double initDist;
	private double goalOffsetDist;
	private final double distance;
	
	private double fwd, turn;
	private double prelimLeft, prelimRight;
	private double scale;
	private double left, right;
	private final double powerIncrement;
	private double maxPower;
	
	public DriveForward(double distance) {
		super(-0.01, 0, 0);
		driveTrain = DriveTrain.getInstance();
		navx = Navx.getInstance();
		requires(driveTrain);
		this.distance = distance;
		powerIncrement = 0.02;
		maxPower = 0.3;
	}
	
	@Override
	protected void initialize() {
		initYaw = navx.getHeading();
		initDist = getAvgEncPosition();
		goalOffsetDist = distance + initDist;
		fwd = 0;
		setSetpoint(0);
	}
	
	@Override
	protected void execute() {
		if ((getAvgEncPosition() - initDist) < 0.5 * distance)
			fwd = Math.min(1, fwd + powerIncrement);
		else
			fwd = Math.max(0, fwd - powerIncrement);
	}
	
	@Override
	protected boolean isFinished() {
		return Math.abs(getAvgEncPosition() - initDist - distance) < 10;
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
		return getOffsetYaw_neg180to180();
	}
	
	@Override
	protected void usePIDOutput(double output) {
		turn = output;
		prelimLeft = fwd - turn;
		prelimRight = fwd + turn;
		scale = Math.max(Math.abs(fwd), Math.abs(turn)) / Math.max(Math.abs(prelimLeft), Math.abs(prelimRight));
		left = scale * prelimLeft;
		right = scale * prelimRight;
		driveTrain.setSideRaw(left, right);
	}
	
	private double getAvgEncPosition() {
		return -0.5 * (driveTrain.getLeftEncRaw() + driveTrain.getRightEncRaw());
	}
	
	private double getOffsetYaw_neg180to180() {
		return ((navx.getHeading() - initYaw) + 180) % 360 - 180;
	}
	
}
