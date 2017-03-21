package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

public class ResetSystems extends Command {
	private Intake intake;
	private Shooter shooter;
	private DriveTrain drivetrain;

    public ResetSystems() {
    	intake = Intake.getInstance();
    	shooter = Shooter.getInstance();
    	drivetrain = DriveTrain.getInstance();
    }

    protected void initialize() {
    	shooter.setSpeed(0);
    	intake.runIntake(0);
    }

    protected boolean isFinished() {
        return true;
    }

}
