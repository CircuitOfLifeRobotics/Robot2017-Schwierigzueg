package com.team3925.robot.commands;

import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.util.RIOConfigs;

import edu.wpi.first.wpilibj.command.Command;

public class DriveManual extends Command {

	public interface DriveManualInput {
		public abstract double getFwd();

		public abstract double getLeft();
	}

	private final boolean doReverseWhenReversing;
	private DriveManualInput input;
	private double prelimLeft, prelimRight;
	private double fwd, turn;
	private double scale;

	public DriveManual(DriveManualInput input) {
		this.input = input;
		doReverseWhenReversing = RIOConfigs.getInstance().getConfigOrAdd("DRIVING DO REVERSE WHEN REVERSING", false);
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
		if (doReverseWhenReversing && fwd != 0)
			turn *= Math.signum(fwd);
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
