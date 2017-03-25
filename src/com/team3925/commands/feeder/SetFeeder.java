package com.team3925.commands.feeder;

import com.team3925.subsystems.Feeder;

import edu.wpi.first.wpilibj.command.Command;

public class SetFeeder extends Command {
	double percent;

	public SetFeeder(double percent) {
		requires(Feeder.getInstance());
		this.percent = percent;
	}

	protected void initialize() {
		Feeder.getInstance().set(percent);
	}

	protected boolean isFinished() {
		return true;
	}
}
