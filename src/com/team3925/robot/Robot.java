
package com.team3925.robot;

import com.team3925.robot.commands.ClimberManual;
import com.team3925.robot.commands.DriveManual;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {

	// private final ProcessAndSendTargetCameraFeed cameraCommand;
	private final DriveManual manualDrive;
	private final ClimberManual manualClimb;

	public Robot() {
		manualDrive = new DriveManual(OI.getInstance());
		manualClimb = new ClimberManual(OI.getInstance());
		// cameraCommand = new ProcessAndSendTargetCameraFeed();
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledInit() {
		// cameraCommand.cancel();
		// ShooterLoader.getInstance().set(0);
		// Agitator.getInstance().set(0);
		// ShooterFlyWheel.getInstance().setShooter(0);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// cameraCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// cameraCommand.start();
		manualDrive.start();
		manualClimb.start();
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
		LiveWindow.run();
	}
}
