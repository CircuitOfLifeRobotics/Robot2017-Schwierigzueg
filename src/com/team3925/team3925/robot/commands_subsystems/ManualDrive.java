package com.team3925.team3925.robot.commands_subsystems;

import org.usfirst.frc.team3925.robot.OI;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDrive extends Command{
	
	Joystick wheel;
	Joystick stick;
	Joystick xbox;
	OI controls;
	DriveTrain drivetrain;
	double responsiveness, scaleConstant, fwd, turn, prelimL, prelimR, l, r, hIn,vOut;
	
	public ManualDrive() {
		wheel = new Joystick(0);
		stick = new Joystick(1);
		xbox = new Joystick(2);
		drivetrain = drivetrain.getInstance();
		controls = OI.getInstance();
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
		l = (-1.35 * wheel.getRawAxis(0)) + stick.getRawAxis(1);
		r = stick.getRawAxis(1) - (-1.35 * wheel.getRawAxis(0));
		drivetrain.setTeleopSpeed(l,r);
		
		
//		responsiveness = (double)((int)(responsiveness * 5)) / 5;
//		SmartDashboard.putNumber("Sensitivity", responsiveness);
//		
//		fwd = -controls.getForward();
//		// TODO: do proper inversion
//		turn = wheel.getRawAxis(1));
//		
//		SmartDashboard.putNumber("fwd",fwd);
//		SmartDashboard.putNumber("turn",turn);
//		
//		if (responsiveness == 1) {
//			prelimL = fwd + turn;
//			prelimR = fwd - turn;
//		} else {
//			prelimL = Math.signum(fwd) * Math.pow(Math.abs(fwd), responsiveness)
//					+ Math.signum(turn) * Math.pow(Math.abs(turn), responsiveness);
//			prelimR = Math.signum(fwd) * Math.pow(Math.abs(fwd), responsiveness)
//					- Math.signum(turn) * Math.pow(Math.abs(turn), responsiveness);
//			SmartDashboard.putNumber("prelimL",prelimL);
//			SmartDashboard.putNumber("prelimR",prelimR);
//		}
//		
//		if (Math.abs(prelimL) > 1) {
//			scaleConstant = 1 / Math.abs(prelimL);
//			l = scaleConstant * prelimL;
//			r = scaleConstant * prelimR;
//		} else if (Math.abs(prelimR) > 1) {
//			scaleConstant = 1 / Math.abs(prelimR);
//			l = scaleConstant * prelimL;
//			r = scaleConstant * prelimR;
//		} else {
//			l = prelimL;
//			r = prelimR;
//		}
//		
//		SmartDashboard.putNumber("Left power", l);
//		SmartDashboard.putNumber("Right power", l);
//		
//		drivetrain.setTeleopSpeed(l, r);
//	}
//
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void end() {
		drivetrain.shiftLow(true);
	}
	@Override
	protected void interrupted() {
		this.end();
	}



}