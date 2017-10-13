package com.team3925.robot.commands;

import com.team3925.robot.RobotMap;
import com.team3925.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

	@Override
	protected void execute() {
		Climber.getInstance().setRaw(RobotMap.CLIMB_SPEED);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Climber.getInstance().setRaw(0);
	}

	@Override
	protected void interrupted() {
		this.end();
	}

}
