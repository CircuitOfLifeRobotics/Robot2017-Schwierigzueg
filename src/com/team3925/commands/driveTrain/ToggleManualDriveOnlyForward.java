package com.team3925.commands.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleManualDriveOnlyForward extends Command {
	
	private DriveManual command;
	
	public ToggleManualDriveOnlyForward(DriveManual command) {
		this.command = command;
	}
	
	@Override
	protected void initialize() {
		command.onlyForward = !command.onlyForward;
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
