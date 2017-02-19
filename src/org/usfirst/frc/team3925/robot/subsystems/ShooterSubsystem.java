
package org.usfirst.frc.team3925.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

public class ShooterSubsystem extends Command {
	
	private static ShooterSubsystem instance;
	
	CANTalon flywheel;
	
	public static ShooterSubsystem getInstance() {
		if (instance==null)
			instance = new ShooterSubsystem();
		return instance;
	}
	
	public ShooterSubsystem() {
		flywheel = new CANTalon(TALON_ID_FLYWHEEL);
		flywheel.changeControlMode(TalonControlMode.Speed);
		flywheel.setPID(TALON_P_FLYWHEEL, TALON_I_FLYWHEEL, TALON_D_FLYWHEEL, TALON_F_FLYWHEEL, TALON_IZONE_FLYWHEEL, TALON_RAMPRATE_FLYWHEEL, TALON_PROFILE_FLYWHEEL);
		flywheel.enableBrakeMode(TALON_BOOLCONSTANTS_SHOOTER[4]);
		flywheel.enableLimitSwitch(TALON_BOOLCONSTANTS_SHOOTER[0],TALON_BOOLCONSTANTS_SHOOTER[1]);
		flywheel.ConfigFwdLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_SHOOTER[2]);
		flywheel.ConfigRevLimitSwitchNormallyOpen(TALON_BOOLCONSTANTS_SHOOTER[3]);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//TODO: change to RPM
	public void setSetpoint(double speed) {
		flywheel.set(speed);
	}
	
}
