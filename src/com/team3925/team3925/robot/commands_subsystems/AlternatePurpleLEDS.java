package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AlternatePurpleLEDS extends Command {

	private Solenoid lR, lB, rR, rB;
	private double lastTime;
	private double wait;
	private boolean teamIsRed;
	private boolean leftIsPurple;

	public AlternatePurpleLEDS() {
		lR = Robot.redLightsA;
		lB = Robot.blueLightsA;
		rR = Robot.redLightsB;
		rB = Robot.blueLightsB;
		wait = 0.3;
		teamIsRed = DriverStation.getInstance().getAlliance().equals(Alliance.Red);
		leftIsPurple = true;
	}

	@Override
	protected void initialize() {
		lastTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		if (Timer.getFPGATimestamp() >= lastTime + wait) {
			lastTime = Timer.getFPGATimestamp();
			leftIsPurple = !leftIsPurple;
			lR.set(leftIsPurple || teamIsRed);
			lB.set(leftIsPurple || !teamIsRed);
			rR.set(!leftIsPurple || teamIsRed);
			rB.set(!leftIsPurple || !teamIsRed);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		lR.set(teamIsRed);
		lB.set(!teamIsRed);
		rR.set(teamIsRed);
		rB.set(!teamIsRed);
	}

	@Override
	protected void interrupted() {
		lR.set(teamIsRed);
		lB.set(!teamIsRed);
		rR.set(teamIsRed);
		rB.set(!teamIsRed);
	}

}
