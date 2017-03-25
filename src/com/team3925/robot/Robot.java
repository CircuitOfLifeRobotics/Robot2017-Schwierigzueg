
package com.team3925.robot;

import com.team3925.commands.ClimberDisengage;
import com.team3925.commands.ClimberEngage;
import com.team3925.commands.DriveTrainShiftHigh;
import com.team3925.commands.DriveTrainShiftLow;
import com.team3925.commands.IntakeGoDown;
import com.team3925.commands.IntakeGoUp;
import com.team3925.commands.IntakeWheelsIn;
import com.team3925.commands.IntakeWheelsOff;

import edu.wpi.first.wpilibj.IterativeRobot;
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

	@Override
	public void robotInit() {
		oi = OI.getInstance();
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
		// intake controls
		oi.whenXboxButtonPressed(0, IntakeGoDown.getInstance());
		oi.whenXboxButtonPressed(0, IntakeWheelsIn.getInstance());
		oi.whenXboxButtonReleased(0, IntakeGoUp.getInstance());
		oi.whenXboxButtonReleased(0, IntakeWheelsOff.getInstance());

		oi.whenXboxButtonPressed(0, IntakeGoDown.getInstance());
		oi.whenXboxButtonReleased(0, IntakeGoUp.getInstance());

		oi.whenXboxButtonPressed(0, IntakeWheelsIn.getInstance());
		oi.whenXboxButtonReleased(0, IntakeWheelsOff.getInstance());

		// shifting controls
		oi.whenWheelButtonPressed(0, DriveTrainShiftHigh.getInstance());
		oi.whenWheelButtonPressed(0, DriveTrainShiftLow.getInstance());

		// climbing controls
		oi.whenWheelButtonPressed(0, ClimberEngage.getInstance());
		oi.whenWheelButtonPressed(0, ClimberDisengage.getInstance());
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
