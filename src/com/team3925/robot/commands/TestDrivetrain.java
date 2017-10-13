package com.team3925.robot.commands;

import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.Gyro;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TestDrivetrain extends Command {

	public TestDrivetrain() {
		// TODO Auto-generated constructor stub
		requires(DriveTrain.getInstance());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		DriveTrain.getInstance().setRaw(1, 1);
		
	}

	@Override
	protected void execute() {
		System.out.println(Gyro.getInstance().getFusedHeading());
		DriveTrain.getInstance().setRaw(Math.sin(Timer.getFPGATimestamp()), Math.cos(Timer.getFPGATimestamp()));
	}

}
