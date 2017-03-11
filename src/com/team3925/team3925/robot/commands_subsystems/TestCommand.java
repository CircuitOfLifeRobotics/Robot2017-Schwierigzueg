package com.team3925.team3925.robot.commands_subsystems;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestCommand extends Command{
	
	DriveTrain drivetrain;
	Shooter shooter;
	
	public TestCommand() {
		drivetrain = drivetrain.getInstance();
		shooter.getInstance();
	}
	@Override
	protected void initialize() {
		//drivetrain.setControlMode(TalonControlMode.PercentVbus);
		drivetrain.zeroEncoders();
		System.out.println("RUNNING");
	}
	
	@Override
	protected void execute() {
		System.out.println("GEAR_VOLTAGE" + drivetrain.getGearValue());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
