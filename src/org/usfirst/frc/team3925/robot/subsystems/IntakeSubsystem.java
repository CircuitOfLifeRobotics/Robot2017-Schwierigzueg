
package org.usfirst.frc.team3925.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeSubsystem extends Command {
	
	private static IntakeSubsystem instance;
	
	private CANTalon external, internalLo, internalHi;
	
	public static IntakeSubsystem getInstance() {
		if (instance==null)
			instance = new IntakeSubsystem();
		return instance;
	}
	
	private IntakeSubsystem() {
		external = new CANTalon(TALON_ID_INTAKE);
		internalLo = new CANTalon(TALON_ID_ELEVATOR_LOWER);
		internalHi = new CANTalon(TALON_ID_ELEVATOR_UPPER);
		
		external.changeControlMode(TalonControlMode.PercentVbus);
		internalLo.changeControlMode(TalonControlMode.PercentVbus);
		internalHi.changeControlMode(TalonControlMode.PercentVbus);
		
		external.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		internalLo.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		internalHi.enableBrakeMode(TALON_BOOLCONSTANTS_INTAKE[4]);
		
		external.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0], TALON_BOOLCONSTANTS_INTAKE[1]);
		internalLo.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0], TALON_BOOLCONSTANTS_INTAKE[1]);
		internalHi.enableLimitSwitch(TALON_BOOLCONSTANTS_INTAKE[0], TALON_BOOLCONSTANTS_INTAKE[1]);
		
		external.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		external.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
		internalLo.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		internalLo.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
		internalHi.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[2]);
		internalHi.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_INTAKE[3]);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setElevatorLo(double pwr) {
		internalLo.set(pwr);
	}
	
	public void setElevatorHi(double pwr) {
		internalHi.set(pwr);
	}
	
	public void setExternalIntake(double pwr) {
		external.set(pwr);
	}
	
}
