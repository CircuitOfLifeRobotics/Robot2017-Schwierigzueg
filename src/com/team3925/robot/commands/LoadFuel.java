package com.team3925.robot.commands;

import static com.team3925.robot.RobotMap.CONFIGS;

import com.team3925.robot.subsystems.Agitator;
import com.team3925.robot.subsystems.ShooterLoader;

import edu.wpi.first.wpilibj.command.Command;

public class LoadFuel extends Command {

	public LoadFuel() {
		requires(Agitator.getInstance());
		requires(ShooterLoader.getInstance());
	}

	@Override
	protected void initialize() {
		ShooterLoader.getInstance().set(CONFIGS.getConfigOrAdd("power shooter loader", 0.5));
		Agitator.getInstance().set(CONFIGS.getConfigOrAdd("power agitator", 0.5));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		interrupted();
	}

	@Override
	protected void interrupted() {
		ShooterLoader.getInstance().set(0);
		Agitator.getInstance().set(0);
	}

}
