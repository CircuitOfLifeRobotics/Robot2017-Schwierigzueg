
package com.team3925.team3925.robot.commands_subsystems;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * Controls the intake using input from a ManualIntakeInput (intended: joysticks from DriverStation)
 *
 */
public class ManualIntake extends Command {
	
	public interface ManualIntakeInput {
		double getOuter();
		double getInner();
		double getLower();
		double getUpper();
	}
	
	private static ManualIntake instance;
	private static ManualIntakeInput input;
	
	private Intake intake;
	
	public ManualIntake setInput(ManualIntakeInput input) {
		this.input = input;
		return this;
	}
	
	public static ManualIntake getInstance() {
		return (instance == null) ? instance = new ManualIntake() : instance;
	}
	
	private ManualIntake() {
		intake = Intake.getInstance();
		requires(intake);
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void execute() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
	}
	
}