package com.team3925.robot.commands;

import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.util.DriveTrainInput;

import edu.wpi.first.wpilibj.command.Command;

public class DriveManual extends Command {

	private DriveTrainInput input;
	private double prelimLeft, prelimRight;
	private double fwd, turn;
	private double scale;

	public DriveManual(DriveTrainInput input) {
		this.input = input;
		requires(DriveTrain.getInstance());
	}

	@Override
	protected void initialize() {
		DriveTrain.getInstance().setRaw(0, 0);
	}

	@Override
	protected void execute() {
		fwd = input.getFwd();
		turn = input.getLeft();
		if (Math.abs(fwd) < 0.1)
			fwd = 0;
		if (Math.abs(turn) < 0.1)
			turn = 0;
		prelimLeft = fwd + turn;
		prelimRight = fwd - turn;
		if (prelimLeft != 0 || prelimRight != 0) {
			scale = Math.max(Math.abs(fwd), Math.abs(turn)) / Math.max(Math.abs(prelimLeft), Math.abs(prelimRight));
			prelimLeft *= scale;
			prelimRight *= scale;
		}
		DriveTrain.getInstance().setRaw(prelimLeft, prelimRight);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		DriveTrain.getInstance().setRaw(0, 0);
	}

	@Override
	protected void interrupted() {
		DriveTrain.getInstance().setRaw(0, 0);
	}

}
