
package com.team3925.robot;

import com.team3925.commands.ArcadeDrive;
import com.team3925.commands.ClimberDisengage;
import com.team3925.commands.ClimberEngage;
import com.team3925.commands.ClimberToggle;
import com.team3925.commands.DriveManual;
import com.team3925.commands.DriveTrainShiftHigh;
import com.team3925.commands.DriveTrainShiftLow;
import com.team3925.commands.IntakeGoDown;
import com.team3925.commands.IntakeGoUp;
import com.team3925.commands.IntakeWheelsIn;
import com.team3925.commands.IntakeWheelsOff;
import com.team3925.commands.Timeout;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private OI oi;
	private DriveManual driveManual;

	@Override
	public void robotInit() {
		oi = OI.getInstance();
		new IntakeGoUp().start();
		driveManual = new DriveManual(OI.getInstance());
	}

	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
		CommandGroup runIntake = new CommandGroup();
		runIntake.addParallel(new IntakeWheelsIn());
		runIntake.addParallel(new IntakeGoDown());
		
		CommandGroup stopIntake = new CommandGroup();
		stopIntake.addParallel(new IntakeGoUp());
		stopIntake.addParallel(new Timeout(1));
		stopIntake.addSequential(new IntakeGoDown());
		
		// intake controls
		oi.whenXboxButtonPressed(3, runIntake);
		oi.whenXboxButtonReleased(3, stopIntake);

		oi.whenXboxButtonPressed(1, new IntakeGoDown());
		oi.whenXboxButtonReleased(1, new IntakeGoUp());

		oi.whenXboxButtonPressed(2, new IntakeWheelsIn());
		oi.whenXboxButtonReleased(2, new IntakeWheelsOff());

		// shifting controls
		oi.whenWheelButtonPressed(5, new DriveTrainShiftHigh());
		oi.whenWheelButtonReleased(5, new DriveTrainShiftLow());

		// climbing controls
		oi.whenWheelButtonPressed(3, new ClimberToggle());
		
		driveManual.start();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}
}
