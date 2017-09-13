
package com.team3925.robot;

import java.io.File;
import java.util.ArrayList;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.commands.DriveManual;
import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
import com.team3925.robot.commands.ShootFuelStatic;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.DriveTrain.DriveTrainState;
import com.team3925.robot.subsystems.GearIntake;
import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
import com.team3925.robot.subsystems.Gyro;
import com.team3925.util.RIOConfigs;
import com.team3925.util.recordable.Record;
import com.team3925.util.recordable.RecordCommand;
import com.team3925.util.recordable.RepeatCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private SendableChooser<ArrayList<File>> autoChooser;

	public static final ProcessAndSendTargetCameraFeed CAMERA_COMMAND = new ProcessAndSendTargetCameraFeed();
	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
	public static Record<DriveTrainState> recordOfDriveTrain;
	public static Record<GearIntakeState> recordOfGearIntake;
	public static RecordCommand<DriveTrainState> recordCommand;
	public static RecordCommand<GearIntakeState> recordCommandForGearIntake;
	private RepeatCommand<DriveTrainState> repeatCommand;
	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;
	private ShootFuelStatic shootCommand;

	public Robot() {
		recordCommand = new RecordCommand<DriveTrainState>(DriveTrain.getInstance());
		repeatCommand = new RepeatCommand<>(DriveTrain.getInstance(), 0.1, 10);
		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
		shootCommand = new ShootFuelStatic();
		Gyro.getInstance();
		autoChooser = new SendableChooser<>();
		
		ArrayList<File> autoCenterNoLine = new ArrayList<>();
		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER NO LINE auto.txt"));
		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER NO LINE auto.txt"));
		ArrayList<File> autoCenter = new ArrayList<>();
		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER auto.txt"));
		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER auto.txt"));
		ArrayList<File> autoSide = new ArrayList<>();
		autoSide.add(new File("/home/lvuser/autos/drive BOILER auto.txt"));
		autoSide.add(new File("/home/lvuser/autos/gears BOILER auto.txt"));
		autoChooser.addDefault("Auto do nothing", new ArrayList<>());
		autoChooser.addDefault("Auto center gear place | no line    | no shoot", autoCenterNoLine);
		autoChooser.addDefault("Auto center gear place | cross line | no shoot", autoCenter);
		autoChooser.addDefault("Auto side gear place   | cross line | no shoot", autoSide);
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
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
		// recordOfDriveTrain = Record.recall(new
		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
		// "/home/lvuser/autos/drive default auto.txt")),
		// DriveTrainState::fromString).orElse(null);
		// recordOfGearIntake = Record.recall(new
		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEAR SAVE PATH",
		// "/home/lvuser/autos/gear default auto.txt")),
		// GearIntakeState::fromString).orElse(null);

		recordOfDriveTrain = recordCommand.get();
		recordOfGearIntake = recordCommandForGearIntake.get();

		RIOConfigs.getInstance().reloadConfigs();
		if (RIOConfigs.getInstance().getConfigOrAdd("DO SAVE AUTO", false)) {
			System.out.println("WE SAVED");
			recordCommand.get().save(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
					"/home/lvuser/autos/drive default auto.txt")), DriveTrainState::toString);
			recordCommandForGearIntake.get().save(new File(RIOConfigs.getInstance()
					.getConfigOrAdd("AUTO GEARS SAVE PATH", "/home/lvuser/autos/gears default auto.txt")),
					GearIntakeState::toString);
		}
		if (RIOConfigs.getInstance().getConfigOrAdd("DO LOAD AUTO", false)) {
			System.out.println("WE LOADED");
			recordOfDriveTrain = Record
					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE RECALL PATH",
							"/home/lvuser/autos/drive default auto.txt")), DriveTrainState::fromString)
					.orElse(null);
			recordOfGearIntake = Record
					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEARS RECALL PATH",
							"/home/lvuser/autos/gears default auto.txt")), GearIntakeState::fromString)
					.orElse(null);
		}else {
			recordOfDriveTrain = null;
			recordOfGearIntake = null;
			ArrayList<File> auto = autoChooser.getSelected();
			if (auto !=null) {
				if (auto.size()>1) {
					if (auto.get(0)!=null)
						recordOfDriveTrain = Record.recall(auto.get(0), DriveTrainState::fromString).orElse(null);
					if (auto.get(1)!=null)
						recordOfGearIntake= Record.recall(auto.get(1), GearIntakeState::fromString).orElse(null);
				}else if (auto.size()>0){
					if (auto.get(0)!=null)
						recordOfDriveTrain = Record.recall(auto.get(0), DriveTrainState::fromString).orElse(null);
				}else {
				}
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
		if (!repeatCommand.isRunning() && !repeatCommandForGearIntake.isRunning()
				&& RIOConfigs.getInstance().getConfigOrAdd("DO SHOOT AT END OF AUTO", true)) {
			shootCommand.start();
		}
	}

	@Override
	public void teleopInit() {
		DRIVE_MANUAL.start();
		recordCommandForGearIntake.reset();
		recordCommandForGearIntake.start();
		recordCommand.reset();
		recordCommand.start();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		System.out.println("teleopPeriodic");
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
