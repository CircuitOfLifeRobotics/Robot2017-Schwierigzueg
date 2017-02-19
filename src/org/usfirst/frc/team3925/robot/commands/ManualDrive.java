
package org.usfirst.frc.team3925.robot.commands;

import org.usfirst.frc.team3925.robot.OI;
import org.usfirst.frc.team3925.robot.subsystems.DriveTrainSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualDrive extends Command {
	
	private static ManualDrive instance;
	
	private double responsiveness, scaleConstant, fwd, turn, prelimL, prelimR, l, r;
	private OI controls;
	private DriveTrainSubsystem driveTrain;
	
	public static ManualDrive getInstance() {
		if (instance == null)
			instance = new ManualDrive();
		return instance;
	}
	
	private ManualDrive() {
		driveTrain = DriveTrainSubsystem.getInstance();
		controls = OI.getInstance();
		responsiveness = 2;
	}
	
	@Override
	protected void initialize() {
		driveTrain.setRaw(0, 0);
		driveTrain.setBrake(true);
	}
	
	@Override
	protected void execute() {
		responsiveness = controls.getSensitivity();
		responsiveness = (double)((int)(responsiveness * 5)) / 5;
		SmartDashboard.putNumber("Sensitivity", responsiveness);
		
		fwd = -controls.getForward();
		// TODO: do proper inversion
		turn = -controls.getTurn();
		
		SmartDashboard.putNumber("fwd",fwd);
		SmartDashboard.putNumber("turn",turn);
		
		if (responsiveness == 1) {
			prelimL = fwd + turn;
			prelimR = fwd - turn;
		} else {
			prelimL = Math.signum(fwd) * Math.pow(Math.abs(fwd), responsiveness)
					+ Math.signum(turn) * Math.pow(Math.abs(turn), responsiveness);
			prelimR = Math.signum(fwd) * Math.pow(Math.abs(fwd), responsiveness)
					- Math.signum(turn) * Math.pow(Math.abs(turn), responsiveness);
			SmartDashboard.putNumber("prelimL",prelimL);
			SmartDashboard.putNumber("prelimR",prelimR);
		}
		
		if (Math.abs(prelimL) > 1) {
			scaleConstant = 1 / Math.abs(prelimL);
			l = scaleConstant * prelimL;
			r = scaleConstant * prelimR;
		} else if (Math.abs(prelimR) > 1) {
			scaleConstant = 1 / Math.abs(prelimR);
			l = scaleConstant * prelimL;
			r = scaleConstant * prelimR;
		} else {
			l = prelimL;
			r = prelimR;
		}
		
		SmartDashboard.putNumber("Left power", l);
		SmartDashboard.putNumber("Right power", l);
		
		driveTrain.setRaw(l, r);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		driveTrain.setRaw(0, 0);
		driveTrain.setBrake(true);
	}
	
	@Override
	protected void interrupted() {
		driveTrain.setRaw(0, 0);
		driveTrain.setBrake(true);
	}
	
}
