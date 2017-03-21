
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3925.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

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
		drivetrain.setAutoControlModes();
		drivetrain.zeroEncoders();
	}
	
	@Override
	protected void execute() {
//		if (Robot.stick.getRawButton(4)){
//			System.out.println("LeftA");
//			drivetrain.setRawAll(1, 0, 0, 0, 0, 0);
//		}
//		
//		else if(Robot.stick.getRawButton(6)){
//			System.out.println("LeftB");
//			drivetrain.setRawAll(0, 1, 0, 0, 0, 0);
//		}
//		
//		else if(Robot.stick.getRawButton(7)){
//			drivetrain.setRawAll(0, 0, 1, 0, 0, 0);
//		}
//		
//		else if(Robot.stick.getRawButton(5)){
//			drivetrain.setRawAll(0, 0, 0, 1, 0, 0);
//		}
//		
//		else if(Robot.stick.getRawButton(11)){
//			drivetrain.setRawAll(0, 0, 0, 0, 1, 0);
//		}
//		
//		else if(Robot.stick.getRawButton(10)){
//			drivetrain.setRawAll(0, 0, 0, 0, 0, 1);
//		}
//		
//		else if(Robot.stick.getRawButton(1)){
//			shooter.setSpeed(1);
//		}
//		
//		else if (Robot.stick.getRawButton(2)){
//			intake.runIntake(1);
//		}
//		
//		else{
//			drivetrain.setRawAll(0, 0, 0, 0, 0, 0);
//			shooter.setSpeed(0);
//			intake.runIntake(0);
//		}
		
		drivetrain.setSetpointFeet(5);
		drivetrain.logEncoderVals();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	@Override
	protected void end() {
		
	}

}
