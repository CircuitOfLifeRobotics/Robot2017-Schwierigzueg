
package org.usfirst.frc.team3925.robot.subsystems;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

import org.usfirst.frc.team3925.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainSubsystem extends Subsystem {
	
	private static DriveTrainSubsystem instance;
	private CANTalon leftA, leftB, leftC, rightA, rightB, rightC;
	private DoubleSolenoid shifter, climber;
	
	public static DriveTrainSubsystem getInstance() {
		if (instance==null)
			instance = new DriveTrainSubsystem();
		return instance;
	}
	
	private DriveTrainSubsystem() {
		leftA = new CANTalon(TALON_ID_DRIVE_LEFT_A);
		leftB = new CANTalon(TALON_ID_DRIVE_LEFT_B);
		leftC = new CANTalon(TALON_ID_DRIVE_LEFT_C);
		rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_A);
		rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_B);
		rightC = new CANTalon(TALON_ID_DRIVE_RIGHT_C);
		
		leftA.changeControlMode(TalonControlMode.PercentVbus);
		leftB.changeControlMode(TalonControlMode.PercentVbus);
		leftC.changeControlMode(TalonControlMode.PercentVbus);
		rightA.changeControlMode(TalonControlMode.PercentVbus);
		rightB.changeControlMode(TalonControlMode.PercentVbus);
		rightC.changeControlMode(TalonControlMode.PercentVbus);
		
		SmartDashboard.putData("Left A", leftA);
		SmartDashboard.putData("Left B", leftB);
		SmartDashboard.putData("Left C", leftC);
		SmartDashboard.putData("Right A", rightC);
		SmartDashboard.putData("Right B", rightC);
		SmartDashboard.putData("Right C", rightC);
		
		shifter = new DoubleSolenoid(SOLENOID_PORT_A_SHIFT, SOLENOID_PORT_B_SHIFT);
		climber = new DoubleSolenoid(SOLENOID_PORT_A_CLIMB, SOLENOID_PORT_B_CLIMB);
		
		
		leftA.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		leftA.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		leftA.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		leftA.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
		
		
		leftB.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		leftB.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		leftB.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		leftB.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
		
		
		leftC.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		leftC.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		leftC.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		leftC.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
		
		rightA.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		rightA.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		rightA.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		rightA.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
		
		
		rightB.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		rightB.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		rightB.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		rightB.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
		
		
		
		rightC.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[2]);
		rightC.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_DRIVETRAIN[3]);
		
		rightC.enableLimitSwitch(TALON_BOOLCONSTANTS_DRIVETRAIN[0], TALON_BOOLCONSTANTS_DRIVETRAIN[1]);
		
		rightC.enableBrakeMode(TALON_BOOLCONSTANTS_DRIVETRAIN[4]);
	
		
		
		
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void setRaw(double left, double right) {
		leftA.set(left);
		leftB.set(left);
		leftC.set(left);
		rightC.set(right);
		rightC.set(right);
		rightC.set(right);
	}
	
	public void setShifter(boolean engaged) {
		shifter.set(engaged ? DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse);
	}
	
	public void setClimber(boolean engaged) {
		climber.set(engaged ? DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse);
	}
	
	public void setBrake(boolean engaged) {
		leftA.enableBrakeMode(engaged);
		leftB.enableBrakeMode(engaged);
		leftC.enableBrakeMode(engaged);
		rightC.enableBrakeMode(engaged);
		rightC.enableBrakeMode(engaged);
		rightC.enableBrakeMode(engaged);
	}
	
}
