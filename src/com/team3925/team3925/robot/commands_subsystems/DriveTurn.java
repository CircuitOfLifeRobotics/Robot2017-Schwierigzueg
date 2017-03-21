package com.team3925.team3925.robot.commands_subsystems;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

import com.team3925.team3925.robot.util.Constants;
import com.team3925.team3925.robot.util.Constants.*;

public class DriveTurn extends Command{
	
	
	DriveTrain drivetrain;
	double degrees;
	int interpolatorState;
	double voltage;
	
	public DriveTurn(double input, double maxVoltage) {
		drivetrain = drivetrain.getInstance();
		degrees = input;
		voltage = maxVoltage;
	}
	@Override
	protected void initialize() {
		drivetrain.setAutoControlModes();
		drivetrain.zeroEncoders();
		drivetrain.turnRobot(degrees);
		drivetrain.setMaxVoltage(voltage);
		interpolatorState = 0;
	}
	@Override
	protected void execute() {
		switch (interpolatorState) {
		case 0:
			drivetrain.turnRobot(1/16 * degrees);
			break;
		case 1:
			drivetrain.turnRobot(5/16 * degrees);
			break;
		case 2:
			drivetrain.turnRobot(12/16 * degrees);
			break;
		case 3:
			drivetrain.turnRobot(degrees);
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
		System.out.println("Stopped Turning");
	}
	public boolean atSetpoint(){
		return (drivetrain.isLeftAtSetpoint(Constants.DRIVETRAIN_DEADZONE)
				&& drivetrain.isRightAtSetpoint(Constants.DRIVETRAIN_DEADZONE));
	}
	

}
