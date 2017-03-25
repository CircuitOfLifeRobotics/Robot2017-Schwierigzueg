package com.team3925.commands.shooter;

import com.team3925.commands.Vision;
import com.team3925.subsystems.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class UpdateShooterSpeed extends Command {

	private double period;
	private double lastExecute;

	public UpdateShooterSpeed(double period) {
		requires(Shooter.getInstance());
		this.period = period;
	}

	@Override
	protected void initialize() {
		lastExecute = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		if (Timer.getFPGATimestamp() - lastExecute >= period) {
			Shooter.getInstance().setShooterVel(Vision.getInstance().getSpeed());
			lastExecute = Timer.getFPGATimestamp();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
