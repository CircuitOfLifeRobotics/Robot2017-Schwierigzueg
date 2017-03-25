
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
		IntakeGoUp.getInstance().start();
	}

	@Override
	public void disabledInit() {
		DriveManual.getInstance().cancel();
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
		oi.whenXboxButtonPressed(3, IntakeGoDown.getInstance());
		oi.whenXboxButtonPressed(3, IntakeWheelsIn.getInstance());
		oi.whenXboxButtonReleased(3, IntakeGoUp.getInstance());
		oi.whenXboxButtonReleased(3, IntakeWheelsOff.getInstance());

		oi.whenXboxButtonPressed(1, IntakeGoDown.getInstance());
		oi.whenXboxButtonReleased(1, IntakeGoUp.getInstance());

		oi.whenXboxButtonPressed(2, IntakeWheelsIn.getInstance());
		oi.whenXboxButtonReleased(2, IntakeWheelsOff.getInstance());

		// shifting controls
		oi.whenWheelButtonPressed(5, DriveTrainShiftHigh.getInstance());
		oi.whenWheelButtonReleased(5, DriveTrainShiftLow.getInstance());

		// climbing controls
		oi.whenWheelButtonPressed(3, ClimberToggle.getInstance());
		
		DriveManual.getInstance().setInput(OI.getInstance());
		DriveManual.getInstance().start();
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
