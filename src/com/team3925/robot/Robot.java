
package com.team3925.robot;

import com.team3925.robot.commands.DriveManual;
import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.DriveTrain.DriveTrainState;
import com.team3925.robot.subsystems.GearIntake;
import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
import com.team3925.robot.subsystems.Gyro;
import com.team3925.util.recordable.Record;
import com.team3925.util.recordable.RecordCommand;
import com.team3925.util.recordable.RepeatCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private final ProcessAndSendTargetCameraFeed cameraCommand;
	public static final DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
	private Record<DriveTrainState> record;
	private RecordCommand<DriveTrainState> recordCommand;
	private RepeatCommand<DriveTrainState> repeatCommand;
	private Record<GearIntakeState> recordOfGearIntake;
	private RecordCommand<GearIntakeState> recordCommandForGearIntake;
	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;

	public Robot() {
		cameraCommand = new ProcessAndSendTargetCameraFeed();
		recordCommand = new RecordCommand<DriveTrainState>(DriveTrain.getInstance());
		repeatCommand = new RepeatCommand<>(DriveTrain.getInstance(), 0.1, 10);
		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
		Gyro.getInstance();
	}

	@Override
	public void robotInit() {
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
		record = recordCommand.get();
		recordOfGearIntake = recordCommandForGearIntake.get();
		repeatCommandForGearIntake.set(recordOfGearIntake);
		repeatCommandForGearIntake.reset();
		repeatCommandForGearIntake.start();
		if (record != null) {
			repeatCommand.set(record);
			repeatCommand.reset();
			repeatCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// cameraCommand.start();
		DRIVE_MANUAL.start();
		// manualClimb.start();
		recordCommandForGearIntake.reset();
		recordCommandForGearIntake.start();
		recordCommand.reset();
		recordCommand.start();
		DriveTrain.getInstance().resetEncoders();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("intake motor voltage", GearIntake.getInstance().getMotorVoltage());
		SmartDashboard.putNumber("intake motor current", GearIntake.getInstance().getMotorCurrent());
	}

	

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
