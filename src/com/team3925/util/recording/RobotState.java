package com.team3925.util.recording;

import java.io.Serializable;

public class RobotState implements Serializable {

	private static final long serialVersionUID = 302184217159295552L;
	public DriveTrainState driveTrainState;
	public FeederState feederState;
	public IntakeState intakeState;
	public ShooterState shooterState;

	public RobotState(DriveTrainState driveTrainState, FeederState feederState, IntakeState intakeState,
			ShooterState shooterState) {
		this.driveTrainState = driveTrainState;
		this.feederState = feederState;
		this.intakeState = intakeState;
		this.shooterState = shooterState;
	}

	public boolean equals(Object obj) {
		if (obj instanceof RobotState) {
			RobotState state = (RobotState) obj;
			return state.driveTrainState.equals(driveTrainState) && state.feederState.equals(feederState)
					&& state.intakeState.equals(intakeState) && state.shooterState.equals(shooterState);
		} else
			return false;
	}
	
}
