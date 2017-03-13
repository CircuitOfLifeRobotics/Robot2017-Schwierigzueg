package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.OI;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestCommand extends Command{
	
	DriveTrain drivetrain;
	Shooter shooter;
	Turret turret;
	Intake intake;
	Joystick xbox = new Joystick(0);
	
	public TestCommand() {
		drivetrain = drivetrain.getInstance();
		shooter = shooter.getInstance();
		turret = turret.getInstance();
		intake = intake.getInstance();
	}
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		shooter.setSpeed(0.80);
		System.out.println("Setpoint" + xbox.getRawAxis(2));
		System.out.println("Encoder Velocity" + shooter.getEncVelocity());
		
		
		if (xbox.getRawButton(1)){
			intake.runIntake(1);
		}else{
			intake.runIntake(0);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	@Override
	protected void end() {
		
	}

}
