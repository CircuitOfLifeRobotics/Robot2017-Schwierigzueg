
package com.team3925.robot;

import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
import com.team3925.robot.subsystems.Agitator;
import com.team3925.robot.subsystems.ShooterFlyWheel;
import com.team3925.robot.subsystems.ShooterLoader;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {

	private final ProcessAndSendTargetCameraFeed cameraCommand;

	public Robot() {
		cameraCommand = new ProcessAndSendTargetCameraFeed();
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledInit() {
		cameraCommand.cancel();
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
		cameraCommand.start();
		Agitator.getInstance().set(0.4);
		ShooterFlyWheel.getInstance().setShooter(0.9);
		ShooterLoader.getInstance().set(0.3);
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		cameraCommand.start();
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
