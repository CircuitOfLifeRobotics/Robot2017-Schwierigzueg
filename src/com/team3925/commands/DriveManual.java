package com.team3925.commands;

import com.team3925.robot.DriveManualInput;
import com.team3925.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveManual extends Command {
	
	private DriveTrain driveTrain;
	private DriveManualInput input;
	
	private static DriveManual instance;
	
	private double fwd, turn;
	private double prelimLeft, prelimRight;
	private double scale;
	private double left, right;
	
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
		if (input == null)
			input = new DriveManualInput() {
				@Override
				public double getRight() {
					return 0;
				}
				@Override
				public double getForward() {
					return 0;
				}
			};
	}
	
	@Override
	protected void execute() {
		fwd = input.getForward();
		turn = input.getRight();
		prelimLeft = fwd + turn;
		prelimRight = fwd - turn;
		scale = Math.max(Math.abs(fwd), Math.abs(turn))/Math.max(Math.abs(prelimLeft), Math.abs(prelimRight));
		left = scale*prelimLeft;
		right = scale*prelimRight;
		driveTrain.setSideRaw(left, right);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		driveTrain.setSideRaw(0, 0);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
	public void setInput(DriveManualInput input) {
		this.input = input;
	}
	
}
