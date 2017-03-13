package com.team3925.team3925.robot.commands_subsystems;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.ctre.CANTalon.TalonControlMode;
import com.team3925.team3925.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveDistance extends Command{
	
	DriveTrain drivetrain;
	double distance;
	double voltage;
	int interpolatorState;
	boolean activateSensor;
	
	
	public DriveDistance(double feet, double maxVoltage, boolean sense) {

		drivetrain = drivetrain.getInstance();
		distance = feet;
		voltage = maxVoltage;
		activateSensor = sense;
	}
	@Override
	protected void initialize() {
		drivetrain.shiftLow(true);
		drivetrain.setControlMode(TalonControlMode.Position, TalonControlMode.Follower, TalonControlMode.Follower,
				TalonControlMode.Position, TalonControlMode.Follower, TalonControlMode.Follower);
		drivetrain.setMaxVoltage(voltage);
		drivetrain.zeroEncoders();
		interpolatorState = 0;
		System.out.println("Starting Drive");
	}
	@Override
	protected void execute() {
		switch (interpolatorState) {
		case 0:
			drivetrain.setSetpointFeet(1/8 * distance);
			break;
		case 1:
			drivetrain.setSetpointFeet(3/8 * distance);
			break;
		case 2:
			drivetrain.setSetpointFeet(6/8 * distance);
			break;
		case 3:
			drivetrain.setSetpointFeet(distance);
			break;
		default:
			break;
		}
		if (atSetpoint()){
			interpolatorState++;
		}
	}

	@Override
	protected boolean isFinished() {
		return interpolatorState > 3;
	}
	@Override
	protected void end() {
		System.out.println("Stopped Driving");
	}
	public boolean atSetpoint(){
		if (drivetrain.isLeftAtSetpoint(Constants.DRIVETRAIN_DEADZONE)
				&& drivetrain.isRightAtSetpoint(Constants.DRIVETRAIN_DEADZONE)){
			return true;
		}
		if (activateSensor && drivetrain.getGearStatus()){
			return true;
		}else{
			return false;
		}
	}
	@Override
	protected void interrupted() {
		this.end();
	}
}
