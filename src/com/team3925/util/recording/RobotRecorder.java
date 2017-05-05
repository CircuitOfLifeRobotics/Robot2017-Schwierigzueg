package com.team3925.util.recording;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Feeder;
import com.team3925.subsystems.Intake;
import com.team3925.subsystems.Shooter;

public class RobotRecorder implements Recordable<RobotState> {

	private static final long serialVersionUID = -1289309772229883073L;

	@Override
	public RobotState record() {
		return new RobotState(DriveTrain.getInstance().record(), Feeder.getInstance().record(), Intake.getInstance().record(), Shooter.getInstance().record());
	}

	@Override
	public void repeat(RobotState action) {
		DriveTrain.getInstance().repeat(action.driveTrainState);
		Feeder.getInstance().repeat(action.feederState);
		Intake.getInstance().repeat(action.intakeState);
		Shooter.getInstance().repeat(action.shooterState);
	}
	
}
