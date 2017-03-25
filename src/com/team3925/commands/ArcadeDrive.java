package com.team3925.commands;

import com.team3925.robot.DriveManualInput;
import com.team3925.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDrive extends Command {
	
	private DriveTrain driveTrain;
	private DriveManualInput input;
	
	
	private double fwd, turn;
	private double prelimLeft, prelimRight;
	private double scale;
	private double left, right;
	

	
	public ArcadeDrive() {
		driveTrain = DriveTrain.getInstance();
		requires(driveTrain);
	}
	
	@Override
	protected void initialize() {
		if (input == null)
			input = new DriveManualInput() {
				@Override
				public double getLeft() {
					return 0;
				}
				@Override
				public double getForward() {
					return 0;
				}
			};
	}
	
	@Override
	protected void execute() {		
		driveTrain.setSideRaw(.5*(input.getForward() - input.getLeft()), .5*(input.getForward() + input.getLeft()));
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		driveTrain.setSideRaw(0, 0);
	}
	
	@Override
	protected void interrupted() {
		end();
	}
	
	public void setInput(DriveManualInput input) {
		this.input = input;
	}
	
}
