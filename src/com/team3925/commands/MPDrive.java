package com.team3925.commands;

import java.util.LinkedList;

import com.team3925.subsystems.DriveTrain;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MPDrive extends Command {
	private double startEncPos = 0;
	private double curEncPos = 0;
	private double finalDist = 0;

	public LinkedList<ChangePoint> cps = new LinkedList<ChangePoint>();

	ChangePoint curCp = new ChangePoint(0, 0, 0);

	public MPDrive(LinkedList<ChangePoint> cps) {

		this.cps = cps;
	}

	public MPDrive(ChangePoint cp) {

		this.cps = null;
		curCp = cp;
	}

	@Override
	protected void initialize() {
		startEncPos = Math
				.abs((DriveTrain.getInstance().getLeftEncRaw() + DriveTrain.getInstance().getRightEncRaw()) / 2);
		DriveTrain.getInstance().enableSpeed();

		super.initialize();
	}

	@Override
	protected void execute() {
		curEncPos = Math.abs((DriveTrain.getInstance().getLeftEncRaw() + DriveTrain.getInstance().getRightEncRaw()) / 2
				- startEncPos);

		SmartDashboard.putNumber("encoderPos", curEncPos);
		SmartDashboard.putNumber("changePoint", finalDist);
		if (Math.abs(curEncPos) >= Math.abs(curCp.getEncoderChangePoint()) && !cps.isEmpty()) {
			curCp = cps.removeFirst();
		}

		double outputVel = curCp.getVelcity();

		if (curCp.getTurnRatio() == 0) {
			DriveTrain.getInstance().setSideRaw(outputVel, outputVel);
		} else if (curCp.getTurnRatio() > 0) {
			DriveTrain.getInstance().setSideRaw(outputVel * (1 / (Math.abs(curCp.getTurnRatio()))),
					outputVel * Math.abs(curCp.getTurnRatio()));
		} else {
			DriveTrain.getInstance().setSideRaw(outputVel * Math.abs(curCp.getTurnRatio()),
					outputVel * (1 / (Math.abs(curCp.getTurnRatio()))));

		}
		super.execute();
	}

	@Override
	protected void interrupted() {
		DriveTrain.getInstance().setSideRaw(0, 0);
		// TODO Auto-generated method stub
		super.interrupted();
	}

	@Override
	protected boolean isFinished() {
		return curEncPos >= curCp.getEncoderChangePoint() && cps.isEmpty();
	}

	@Override
	protected void end() {
		DriveTrain.getInstance().setSideRaw(0, 0);
		super.end();
	}

}
