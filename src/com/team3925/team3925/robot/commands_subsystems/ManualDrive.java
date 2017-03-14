package com.team3925.team3925.robot.commands_subsystems;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ManualDrive extends Command{
	
	Joystick wheel;
	Joystick stick;
	Joystick xbox;
	DriveTrain drivetrain;
	double l;
	double r;
	
	public ManualDrive() {
		wheel = new Joystick(0);
		stick = new Joystick(1);
		xbox = new Joystick(2);
		drivetrain = drivetrain.getInstance();
	}
	
	@Override
	protected void initialize() {
		drivetrain.setControlMode(TalonControlMode.PercentVbus);
		drivetrain.clearTalonInversionSettings();
		drivetrain.zeroEncoders();
		drivetrain.setTeleopControlModes();
	}
	@Override
	protected void execute() {
		l = (-1.5 * wheel.getRawAxis(1)) + stick.getRawAxis(1);
		r = stick.getRawAxis(1) - (-1.5 *wheel.getRawAxis(0));
		drivetrain.setTeleopSpeed(l,r);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
