package com.team3925.robot;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.team3925.robot.commands.DriveManual;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.GearIntake;
import com.team3925.robot.subsystems.Gyro;
import com.team3925.util.RIOConfigs;
import com.team3925.util.recordable.Record;
import com.team3925.util.recordable.RecordCommand;
import com.team3925.util.recordable.RepeatCommand;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// RecordHelper recHelper;

	private SendableChooser<ArrayList<Record>> chooser;
	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
	public static Record recordOfDriveTrain;
	public static Record recordOfGearIntake;
	public static RecordCommand recordCommand;
	public static RecordCommand recordCommandForGearIntake;
	private RepeatCommand repeatCommand;
	private RepeatCommand repeatCommandForGearIntake;

	public Robot() {
		recordCommand = new RecordCommand(DriveTrain.getInstance());
		repeatCommand = new RepeatCommand(DriveTrain.getInstance(), 0.1, 10);
		recordCommandForGearIntake = new RecordCommand(GearIntake.getInstance());
		repeatCommandForGearIntake = new RepeatCommand(GearIntake.getInstance(), 0.1, 10);
		Gyro.getInstance().setAngleToZero();

		chooser = new SendableChooser<>();
		ArrayList<Record> recs;
		recs = new ArrayList<>();
		recs.add(Record.recall(new File("/home/lvuser/autos/drive center peg autos.txt")).get());
		recs.add(Record.recall(new File("/home/lvuser/autos/gears center peg autos.txt")).get());
		chooser.addDefault("CENTER", recs);
		recs = new ArrayList<>();
		recs.add(Record.recall(new File("/home/lvuser/autos/drive side auto.txt")).get());
		recs.add(Record.recall(new File("/home/lvuser/autos/gears side auto.txt")).get());
		chooser.addDefault("SIDE", recs);
		recs = new ArrayList<>();
		recs.add(new Record("NONE"));
		chooser.addDefault("NONE", recs);
		recs = new ArrayList<>();
		recs.add(new Record("LAST RECORDED"));
		chooser.addObject("LAST RECORDED", recs);

		// recHelper = new RecordHelper(new File("/home/lvuser/autos"),
		// DriveTrain.getInstance(),
		// GearIntake.getInstance());
	}

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		// SmartDashboard.putData(recHelper);
		SmartDashboard.putData("auto chooser", chooser);
		SmartDashboard.putBoolean("Do save", false);
		DriveTrain.getInstance().setRaw(0, 0);
		GearIntake.getInstance().setMotor(0);
		GearIntake.getInstance().setSolenoid(true);
	}

	@Override
	public void disabledInit() {
		DriveTrain.getInstance().setRaw(0, 0);
		GearIntake.getInstance().setMotor(0);
		GearIntake.getInstance().setSolenoid(true);
		if (SmartDashboard.getBoolean("Do save", true)) {
			recordOfDriveTrain = recordCommand.get();
			recordOfGearIntake = recordCommandForGearIntake.get();
			System.out.println("saving");
			if (recordOfDriveTrain.size() > 0 && recordOfGearIntake.size() > 0) {
				recordCommand.get().save(new File(
						"/home/lvuser/autos/drive auto_" + GregorianCalendar.getInstance().getTimeInMillis() + ".txt"));
				recordCommandForGearIntake.get().save(new File(
						"/home/lvuser/autos/gears auto_" + GregorianCalendar.getInstance().getTimeInMillis() + ".txt"));
			}
		} else
			System.out.println("not saving");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		RIOConfigs.getInstance().reloadConfigs();
		Gyro.getInstance().setAngleToZero();
		DriveTrain.getInstance().setRaw(0, 0);
		GearIntake.getInstance().setMotor(0);
		GearIntake.getInstance().setSolenoid(true);

		ArrayList<Record> recs = chooser.getSelected();
		if (recs.size() > 0) {
			if (recs.get(0).getName().equals("NONE")) {
				recordOfDriveTrain = null;
				recordOfGearIntake = null;
			} else if (recs.get(0).getName().equals("LAST RECORDED"))
				;
			else if (recs.size() > 1) {
				recordOfDriveTrain = recs.get(0);
				recordOfGearIntake = recs.get(1);
			}
		}
		if (recordOfGearIntake != null) {
			repeatCommandForGearIntake.set(recordOfGearIntake);
			repeatCommandForGearIntake.reset();
			repeatCommandForGearIntake.start();
		}
		if (recordOfDriveTrain != null) {
			repeatCommand.set(recordOfDriveTrain);
			repeatCommand.reset();
			repeatCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		if (!repeatCommand.isRunning() && !repeatCommandForGearIntake.isRunning()) {
			DriveTrain.getInstance().setRaw(0, 0);
			GearIntake.getInstance().setMotor(0);
			GearIntake.getInstance().setSolenoid(true);
		}
	}

	@Override
	public void teleopInit() {
		Gyro.getInstance().setAngleToZero();
		DRIVE_MANUAL.start();
		recordCommandForGearIntake.reset();
		recordCommandForGearIntake.start();
		recordCommand.reset();
		recordCommand.start();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Left percent", DriveTrain.getInstance().getLeftSetpoint());
		SmartDashboard.putNumber("Right percent", DriveTrain.getInstance().getRightSetpoint());
		SmartDashboard.putNumber("Gyro heading", Gyro.getInstance().getFusedHeading());
		SmartDashboard.putNumber("Gyro X velocity", Gyro.getInstance().getXVelocity());
		SmartDashboard.putNumber("Gyro Y velocity", Gyro.getInstance().getYVelocity());
		SmartDashboard.putNumber("Gyro Z velocity", Gyro.getInstance().getZVelocity());
		SmartDashboard.putNumber("Gyro X accel", Gyro.getInstance().getXAccel());
		SmartDashboard.putNumber("Gyro Y accel", Gyro.getInstance().getYAccel());
		SmartDashboard.putNumber("Gyro Z accel", Gyro.getInstance().getZAccel());
		SmartDashboard.putNumber("Gyro mag accel", Gyro.getInstance().getFusedFloorAccel());
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
