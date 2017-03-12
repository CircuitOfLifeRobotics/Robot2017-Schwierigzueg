package com.team3925.team3925.robot.commands_subsystems;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestCommand extends Command{
	
	DriveTrain drivetrain;
	Shooter shooter;
	Turret turret;
	
	public TestCommand() {
		drivetrain = drivetrain.getInstance();
		shooter = shooter.getInstance();
		turret = turret.getInstance();
	}
	@Override
	protected void initialize() {
		shooter.setSpeed(300);
	}
	
	@Override
	protected void execute() {
		System.out.println("RUUNINF");
		SmartDashboard.putBoolean("TURRET_SWITCH", turret.getSwitch());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	@Override
	protected void end() {
		turret.setSpeed(0);
	}

}
