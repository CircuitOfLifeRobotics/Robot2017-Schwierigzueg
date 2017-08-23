package com.team3925.robot.commands;

import java.util.function.Supplier;

import com.team3925.robot.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberManual extends Command {
	
	private final Supplier<Double> supplier;
	
	public ClimberManual(Supplier<Double> supplier) {
		this.supplier = supplier;
	}

	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		Climber.getInstance().setRaw(supplier.get());
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
		end();
	}
	
}
