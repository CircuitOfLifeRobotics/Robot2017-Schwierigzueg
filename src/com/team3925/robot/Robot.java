////
////package com.team3925.robot;
////
////import java.io.File;
////import java.io.FileWriter;
////import java.io.IOException;
////import java.util.ArrayList;
////
////import com.team3925.robot.commands.DriveManual;
////import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
////import com.team3925.robot.commands.ShootFuelStatic;
////import com.team3925.robot.subsystems.DriveTrain;
////import com.team3925.robot.subsystems.DriveTrain.PolarDriveTrainState;
////import com.team3925.robot.subsystems.GearIntake;
////import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
////import com.team3925.robot.subsystems.Gyro;
////import com.team3925.util.RIOConfigs;
////import com.team3925.util.recordable.Record;
////import com.team3925.util.recordable.RecordCommand;
////import com.team3925.util.recordable.RepeatCommand;
////
////import edu.wpi.first.wpilibj.DriverStation;
////import edu.wpi.first.wpilibj.IterativeRobot;
////import edu.wpi.first.wpilibj.command.Scheduler;
////import edu.wpi.first.wpilibj.livewindow.LiveWindow;
////import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
////import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
////
////public class Robot extends IterativeRobot {
////
////	private SendableChooser<ArrayList<File>> autoChooser;
////
////	public static final ProcessAndSendTargetCameraFeed CAMERA_COMMAND = new ProcessAndSendTargetCameraFeed();
////	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
////	public static Record<PolarDriveTrainState> recordOfDriveTrain;
////	public static Record<GearIntakeState> recordOfGearIntake;
////	public static RecordCommand<PolarDriveTrainState> recordCommand;
////	public static RecordCommand<GearIntakeState> recordCommandForGearIntake;
////	private RepeatCommand<PolarDriveTrainState> repeatCommand;
////	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;
////	private ShootFuelStatic shootCommand;
////	private FileWriter fw;
////	private double lastSet;
////
////	public Robot() {
////		recordCommand = new RecordCommand<PolarDriveTrainState>(DriveTrain.getInstance());
////		repeatCommand = new RepeatCommand<PolarDriveTrainState>(DriveTrain.getInstance(), 0.1, 10);
////		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
////		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
////		shootCommand = new ShootFuelStatic();
////		Gyro.getInstance();
////		autoChooser = new SendableChooser<>();
////		lastSet = 0;
////
////		ArrayList<File> autoCenterNoLine = new ArrayList<>();
////		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER NO LINE auto.txt"));
////		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER NO LINE auto.txt"));
////		ArrayList<File> autoCenter = new ArrayList<>();
////		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER auto.txt"));
////		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER auto.txt"));
////		ArrayList<File> autoSide = new ArrayList<>();
////		autoSide.add(new File("/home/lvuser/autos/drive BOILER auto.txt"));
////		autoSide.add(new File("/home/lvuser/autos/gears BOILER auto.txt"));
////		autoChooser.addDefault("Auto do nothing", new ArrayList<>());
////		autoChooser.addDefault("Auto center gear place | no line    | no shoot", autoCenterNoLine);
////		autoChooser.addDefault("Auto center gear place | cross line | no shoot", autoCenter);
////		autoChooser.addDefault("Auto side gear place   | cross line | no shoot", autoSide);
////		SmartDashboard.putData("Autonomous Chooser", autoChooser);
////		try {
////			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
////
////	@Override
////	public void robotInit() {
////	}
////
////	@Override
////	public void disabledInit() {
////		try {
////			fw.close();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
////
////	@Override
////	public void disabledPeriodic() {
////		Scheduler.getInstance().run();
////	}
////
////	@Override
////	public void autonomousInit() {
////		Gyro.getInstance().setAngleToZero();
////		// recordOfDriveTrain = Record.recall(new
////		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
////		// "/home/lvuser/autos/drive default auto.txt")),
////		// DriveTrainState::fromString).orElse(null);
////		// recordOfGearIntake = Record.recall(new
////		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEAR SAVE PATH",
////		// "/home/lvuser/autos/gear default auto.txt")),
////		// GearIntakeState::fromString).orElse(null);
////
////		recordOfDriveTrain = recordCommand.get();
////		recordOfGearIntake = recordCommandForGearIntake.get();
////
////		RIOConfigs.getInstance().reloadConfigs();
////		if (RIOConfigs.getInstance().getConfigOrAdd("DO SAVE AUTO", false)) {
////			System.out.println("WE SAVED");
////			recordCommand.get().save(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
////					"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::toString);
////			recordCommandForGearIntake.get().save(new File(RIOConfigs.getInstance()
////					.getConfigOrAdd("AUTO GEARS SAVE PATH", "/home/lvuser/autos/gears default auto.txt")),
////					GearIntakeState::toString);
////		}
////		if (RIOConfigs.getInstance().getConfigOrAdd("DO LOAD AUTO", false)) {
////			System.out.println("WE LOADED");
////			recordOfDriveTrain = Record
////					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE RECALL PATH",
////							"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::fromString)
////					.orElse(null);
////			recordOfGearIntake = Record
////					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEARS RECALL PATH",
////							"/home/lvuser/autos/gears default auto.txt")), GearIntakeState::fromString)
////					.orElse(null);
////		} else {
////			// recordOfDriveTrain = null;
////			// recordOfGearIntake = null;
////			// ArrayList<File> auto = autoChooser.getSelected();
////			// if (auto !=null) {
////			// if (auto.size()>1) {
////			// if (auto.get(0)!=null)
////			// recordOfDriveTrain = Record.recall(auto.get(0),
////			// DriveTrainState::fromString).orElse(null);
////			// if (auto.get(1)!=null)
////			// recordOfGearIntake= Record.recall(auto.get(1),
////			// GearIntakeState::fromString).orElse(null);
////			// }else if (auto.size()>0){
////			// if (auto.get(0)!=null)
////			// recordOfDriveTrain = Record.recall(auto.get(0),
////			// DriveTrainState::fromString).orElse(null);
////			// }else {
////			// }
////			// }
////		}
////		if (recordOfGearIntake != null) {
////			repeatCommandForGearIntake.set(recordOfGearIntake);
////			repeatCommandForGearIntake.reset();
////			repeatCommandForGearIntake.start();
////		}
////		if (recordOfDriveTrain != null) {
////			repeatCommand.set(recordOfDriveTrain);
////			repeatCommand.reset();
////			repeatCommand.start();
////		}
////	}
////
////	@Override
////	public void autonomousPeriodic() {
////		Scheduler.getInstance().run();
////		if (!repeatCommand.isRunning() && !repeatCommandForGearIntake.isRunning()
////				&& RIOConfigs.getInstance().getConfigOrAdd("DO SHOOT AT END OF AUTO", true)) {
////			shootCommand.start();
////		}
////	}
////
////	@Override
////	public void teleopInit() {
////		Gyro.getInstance().setAngleToZero();
////		// DRIVE_MANUAL.start();
////		// recordCommandForGearIntake.reset();
////		// recordCommandForGearIntake.start();
////		// recordCommand.reset();
////		// recordCommand.start();
////		try {
////			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	}
////
////	@Override
////	public void teleopPeriodic() {
////		Scheduler.getInstance().run();
////		SmartDashboard.putNumber("intake motor voltage", GearIntake.getInstance().getMotorVoltage());
////		SmartDashboard.putNumber("intake motor current", GearIntake.getInstance().getMotorCurrent());
////
////		SmartDashboard.putNumber("Tune drive train value", DriveTrain.getInstance().getRightSetpoint());
////		SmartDashboard.putNumber("Tune drive train target speed", DriveTrain.getInstance().getRightSpeed());
////		System.out.println(DriveTrain.getInstance().getRightSpeed());
////		if (OI.getInstance().getIsBtn8Pressed()) {
////			lastSet = OI.getInstance().getWheelAxis2();
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn11Pressed()) {
////			lastSet += 0.005;
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn10Pressed()) {
////			lastSet -= 0.005;
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn6Pressed()) {
////			System.out.println("btn 6 rpesed");
////			if (fw != null) {
////				System.out.println("appneding");
////				try {
////					fw.append(DriverStation.getInstance().getBatteryVoltage() + "`"
////							+ DriveTrain.getInstance().getRightSpeed() + "`"
////							+ DriveTrain.getInstance().getRightSetpoint() + "\n");
////				} catch (IOException e) {
////					e.printStackTrace();
////					System.out.println("failed to save");
////				}
////				System.out.println("saved");
////			} else {
////				System.out.println("failed to save");
////			}
////		}
////	}
////
////	@Override
////	public void testInit() {
////	}
////
////	@Override
////	public void testPeriodic() {
////		LiveWindow.run();
////	}
////}
//
//
//
//package com.team3925.robot;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import com.team3925.robot.commands.DriveManual;
//import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
//import com.team3925.robot.commands.ShootFuelStatic;
//import com.team3925.robot.subsystems.DriveTrain;
//import com.team3925.robot.subsystems.DriveTrain.PolarDriveTrainState;
//import com.team3925.robot.subsystems.GearIntake;
//import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
//import com.team3925.robot.subsystems.Gyro;
//import com.team3925.util.RIOConfigs;
//import com.team3925.util.recordable.Record;
//import com.team3925.util.recordable.RecordCommand;
//import com.team3925.util.recordable.RepeatCommand;
//
//import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.command.Scheduler;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class Robot extends IterativeRobot {
//
//	private SendableChooser<ArrayList<File>> autoChooser;
//
//	public static final ProcessAndSendTargetCameraFeed CAMERA_COMMAND = new ProcessAndSendTargetCameraFeed();
//	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
//	public static Record<PolarDriveTrainState> recordOfDriveTrain;
//	public static Record<GearIntakeState> recordOfGearIntake;
//	public static RecordCommand<PolarDriveTrainState> recordCommand;
//	public static RecordCommand<GearIntakeState> recordCommandForGearIntake;
//	private RepeatCommand<PolarDriveTrainState> repeatCommand;
//	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;
//	private ShootFuelStatic shootCommand;
//	// private FileWriter fw;
//	// private double lastSet;
//
//	public Robot() {
//		recordCommand = new RecordCommand<PolarDriveTrainState>(DriveTrain.getInstance());
//		repeatCommand = new RepeatCommand<PolarDriveTrainState>(DriveTrain.getInstance(), 0.1, 10);
//		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
//		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
//		shootCommand = new ShootFuelStatic();
//		Gyro.getInstance();
//		autoChooser = new SendableChooser<>();
////		lastSet = 0;
//
//		ArrayList<File> autoCenterNoLine = new ArrayList<>();
//		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER NO LINE auto.txt"));
//		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER NO LINE auto.txt"));
//		ArrayList<File> autoCenter = new ArrayList<>();
//		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER auto.txt"));
//		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER auto.txt"));
//		ArrayList<File> autoSide = new ArrayList<>();
//		autoSide.add(new File("/home/lvuser/autos/drive BOILER auto.txt"));
//		autoSide.add(new File("/home/lvuser/autos/gears BOILER auto.txt"));
//		autoChooser.addDefault("Auto do nothing", new ArrayList<>());
//		autoChooser.addDefault("Auto center gear place | no line    | no shoot", autoCenterNoLine);
//		autoChooser.addDefault("Auto center gear place | cross line | no shoot", autoCenter);
//		autoChooser.addDefault("Auto side gear place   | cross line | no shoot", autoSide);
//		SmartDashboard.putData("Autonomous Chooser", autoChooser);
////		try {
////			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
//
//	@Override
//	public void robotInit() {
//	}
//
//	@Override
//	public void disabledInit() {
////		try {
////			fw.close();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
//
//	@Override
//	public void disabledPeriodic() {
//		Scheduler.getInstance().run();
//	}
//
//	@Override
//	public void autonomousInit() {
//		Gyro.getInstance().setAngleToZero();
//		// recordOfDriveTrain = Record.recall(new
//		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
//		// "/home/lvuser/autos/drive default auto.txt")),
//		// DriveTrainState::fromString).orElse(null);
//		// recordOfGearIntake = Record.recall(new
//		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEAR SAVE PATH",
//		// "/home/lvuser/autos/gear default auto.txt")),
//		// GearIntakeState::fromString).orElse(null);
//
//		recordOfDriveTrain = recordCommand.get();
//		recordOfGearIntake = recordCommandForGearIntake.get();
//
//		RIOConfigs.getInstance().reloadConfigs();
//		if (RIOConfigs.getInstance().getConfigOrAdd("DO SAVE AUTO", false)) {
//			System.out.println("WE SAVED");
//			recordCommand.get().save(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
//					"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::toString);
//			recordCommandForGearIntake.get().save(new File(RIOConfigs.getInstance()
//					.getConfigOrAdd("AUTO GEARS SAVE PATH", "/home/lvuser/autos/gears default auto.txt")),
//					GearIntakeState::toString);
//		}
//		if (RIOConfigs.getInstance().getConfigOrAdd("DO LOAD AUTO", false)) {
//			System.out.println("WE LOADED");
//			recordOfDriveTrain = Record
//					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE RECALL PATH",
//							"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::fromString)
//					.orElse(null);
//			recordOfGearIntake = Record
//					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEARS RECALL PATH",
//							"/home/lvuser/autos/gears default auto.txt")), GearIntakeState::fromString)
//					.orElse(null);
//		} else {
//			// recordOfDriveTrain = null;
//			// recordOfGearIntake = null;
//			// ArrayList<File> auto = autoChooser.getSelected();
//			// if (auto !=null) {
//			// if (auto.size()>1) {
//			// if (auto.get(0)!=null)
//			// recordOfDriveTrain = Record.recall(auto.get(0),
//			// DriveTrainState::fromString).orElse(null);
//			// if (auto.get(1)!=null)
//			// recordOfGearIntake= Record.recall(auto.get(1),
//			// GearIntakeState::fromString).orElse(null);
//			// }else if (auto.size()>0){
//			// if (auto.get(0)!=null)
//			// recordOfDriveTrain = Record.recall(auto.get(0),
//			// DriveTrainState::fromString).orElse(null);
//			// }else {
//			// }
//			// }
//		}
//		if (recordOfGearIntake != null) {
//			repeatCommandForGearIntake.set(recordOfGearIntake);
//			repeatCommandForGearIntake.reset();
//			repeatCommandForGearIntake.start();
//		}
//		if (recordOfDriveTrain != null) {
//			repeatCommand.set(recordOfDriveTrain);
//			repeatCommand.reset();
//			repeatCommand.start();
//		}
//	}
//
//	@Override
//	public void autonomousPeriodic() {
//		Scheduler.getInstance().run();
//		if (!repeatCommand.isRunning() && !repeatCommandForGearIntake.isRunning()
//				&& RIOConfigs.getInstance().getConfigOrAdd("DO SHOOT AT END OF AUTO", true)) {
//			shootCommand.start();
//		}
//	}
//
//	@Override
//	public void teleopInit() {
//		Gyro.getInstance().setAngleToZero();
//		 DRIVE_MANUAL.start();
//		 recordCommandForGearIntake.reset();
//		 recordCommandForGearIntake.start();
//		 recordCommand.reset();
//		 recordCommand.start();
////		try {
////			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//	}
//
//	@Override
//	public void teleopPeriodic() {
//		Scheduler.getInstance().run();
//		SmartDashboard.putNumber("intake motor voltage", GearIntake.getInstance().getMotorVoltage());
//		SmartDashboard.putNumber("intake motor current", GearIntake.getInstance().getMotorCurrent());
//
////		SmartDashboard.putNumber("Tune drive train value", DriveTrain.getInstance().getRightSetpoint());
////		SmartDashboard.putNumber("Tune drive train target speed", DriveTrain.getInstance().getRightSpeed());
////		System.out.println(DriveTrain.getInstance().getRightSpeed());
////		if (OI.getInstance().getIsBtn8Pressed()) {
////			lastSet = OI.getInstance().getWheelAxis2();
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn11Pressed()) {
////			lastSet += 0.005;
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn10Pressed()) {
////			lastSet -= 0.005;
////			DriveTrain.getInstance().setRaw(0, lastSet);
////		}
////		if (OI.getInstance().getIsBtn6Pressed()) {
////			System.out.println("btn 6 rpesed");
////			if (fw != null) {
////				System.out.println("appneding");
////				try {
////					fw.append(DriverStation.getInstance().getBatteryVoltage() + "`"
////							+ DriveTrain.getInstance().getRightSpeed() + "`"
////							+ DriveTrain.getInstance().getRightSetpoint() + "\n");
////				} catch (IOException e) {
////					e.printStackTrace();
////					System.out.println("failed to save");
////				}
////				System.out.println("saved");
////			} else {
////				System.out.println("failed to save");
////			}
////		}
//	}
//
//	@Override
//	public void testInit() {
//	}
//
//	@Override
//	public void testPeriodic() {
//		LiveWindow.run();
//	}
//}

//
//package com.team3925.robot;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import com.team3925.robot.commands.DriveManual;
//import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
//import com.team3925.robot.commands.ShootFuelStatic;
//import com.team3925.robot.subsystems.DriveTrain;
//import com.team3925.robot.subsystems.DriveTrain.PolarDriveTrainState;
//import com.team3925.robot.subsystems.GearIntake;
//import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
//import com.team3925.robot.subsystems.Gyro;
//import com.team3925.util.RIOConfigs;
//import com.team3925.util.recordable.Record;
//import com.team3925.util.recordable.RecordCommand;
//import com.team3925.util.recordable.RepeatCommand;
//
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.command.Scheduler;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class Robot extends IterativeRobot {
//
//	private SendableChooser<ArrayList<File>> autoChooser;
//
//	public static final ProcessAndSendTargetCameraFeed CAMERA_COMMAND = new ProcessAndSendTargetCameraFeed();
//	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
//	public static Record<PolarDriveTrainState> recordOfDriveTrain;
//	public static Record<GearIntakeState> recordOfGearIntake;
//	public static RecordCommand<PolarDriveTrainState> recordCommand;
//	public static RecordCommand<GearIntakeState> recordCommandForGearIntake;
//	private RepeatCommand<PolarDriveTrainState> repeatCommand;
//	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;
//	private ShootFuelStatic shootCommand;
//	private FileWriter fw;
//	private double lastSet;
//
//	public Robot() {
//		recordCommand = new RecordCommand<PolarDriveTrainState>(DriveTrain.getInstance());
//		repeatCommand = new RepeatCommand<PolarDriveTrainState>(DriveTrain.getInstance(), 0.1, 10);
//		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
//		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
//		shootCommand = new ShootFuelStatic();
//		Gyro.getInstance();
//		autoChooser = new SendableChooser<>();
//		lastSet = 0;
//
//		ArrayList<File> autoCenterNoLine = new ArrayList<>();
//		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER NO LINE auto.txt"));
//		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER NO LINE auto.txt"));
//		ArrayList<File> autoCenter = new ArrayList<>();
//		autoCenterNoLine.add(new File("/home/lvuser/autos/drive CENTER auto.txt"));
//		autoCenterNoLine.add(new File("/home/lvuser/autos/gears CENTER auto.txt"));
//		ArrayList<File> autoSide = new ArrayList<>();
//		autoSide.add(new File("/home/lvuser/autos/drive BOILER auto.txt"));
//		autoSide.add(new File("/home/lvuser/autos/gears BOILER auto.txt"));
//		autoChooser.addDefault("Auto do nothing", new ArrayList<>());
//		autoChooser.addDefault("Auto center gear place | no line    | no shoot", autoCenterNoLine);
//		autoChooser.addDefault("Auto center gear place | cross line | no shoot", autoCenter);
//		autoChooser.addDefault("Auto side gear place   | cross line | no shoot", autoSide);
//		SmartDashboard.putData("Autonomous Chooser", autoChooser);
//		try {
//			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void robotInit() {
//	}
//
//	@Override
//	public void disabledInit() {
//		try {
//			fw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void disabledPeriodic() {
//		Scheduler.getInstance().run();
//	}
//
//	@Override
//	public void autonomousInit() {
//		Gyro.getInstance().setAngleToZero();
//		// recordOfDriveTrain = Record.recall(new
//		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
//		// "/home/lvuser/autos/drive default auto.txt")),
//		// DriveTrainState::fromString).orElse(null);
//		// recordOfGearIntake = Record.recall(new
//		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEAR SAVE PATH",
//		// "/home/lvuser/autos/gear default auto.txt")),
//		// GearIntakeState::fromString).orElse(null);
//
//		recordOfDriveTrain = recordCommand.get();
//		recordOfGearIntake = recordCommandForGearIntake.get();
//
//		RIOConfigs.getInstance().reloadConfigs();
//		if (RIOConfigs.getInstance().getConfigOrAdd("DO SAVE AUTO", false)) {
//			System.out.println("WE SAVED");
//			recordCommand.get().save(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
//					"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::toString);
//			recordCommandForGearIntake.get().save(new File(RIOConfigs.getInstance()
//					.getConfigOrAdd("AUTO GEARS SAVE PATH", "/home/lvuser/autos/gears default auto.txt")),
//					GearIntakeState::toString);
//		}
//		if (RIOConfigs.getInstance().getConfigOrAdd("DO LOAD AUTO", false)) {
//			System.out.println("WE LOADED");
//			recordOfDriveTrain = Record
//					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE RECALL PATH",
//							"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::fromString)
//					.orElse(null);
//			recordOfGearIntake = Record
//					.recall(new File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEARS RECALL PATH",
//							"/home/lvuser/autos/gears default auto.txt")), GearIntakeState::fromString)
//					.orElse(null);
//		} else {
//			// recordOfDriveTrain = null;
//			// recordOfGearIntake = null;
//			// ArrayList<File> auto = autoChooser.getSelected();
//			// if (auto !=null) {
//			// if (auto.size()>1) {
//			// if (auto.get(0)!=null)
//			// recordOfDriveTrain = Record.recall(auto.get(0),
//			// DriveTrainState::fromString).orElse(null);
//			// if (auto.get(1)!=null)
//			// recordOfGearIntake= Record.recall(auto.get(1),
//			// GearIntakeState::fromString).orElse(null);
//			// }else if (auto.size()>0){
//			// if (auto.get(0)!=null)
//			// recordOfDriveTrain = Record.recall(auto.get(0),
//			// DriveTrainState::fromString).orElse(null);
//			// }else {
//			// }
//			// }
//		}
//		if (recordOfGearIntake != null) {
//			repeatCommandForGearIntake.set(recordOfGearIntake);
//			repeatCommandForGearIntake.reset();
//			repeatCommandForGearIntake.start();
//		}
//		if (recordOfDriveTrain != null) {
//			repeatCommand.set(recordOfDriveTrain);
//			repeatCommand.reset();
//			repeatCommand.start();
//		}
//	}
//
//	@Override
//	public void autonomousPeriodic() {
//		Scheduler.getInstance().run();
//		if (!repeatCommand.isRunning() && !repeatCommandForGearIntake.isRunning()
//				&& RIOConfigs.getInstance().getConfigOrAdd("DO SHOOT AT END OF AUTO", true)) {
//			shootCommand.start();
//		}
//	}
//
//	@Override
//	public void teleopInit() {
//		Gyro.getInstance().setAngleToZero();
//		// DRIVE_MANUAL.start();
//		// recordCommandForGearIntake.reset();
//		// recordCommandForGearIntake.start();
//		// recordCommand.reset();
//		// recordCommand.start();
//		try {
//			fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void teleopPeriodic() {
//		Scheduler.getInstance().run();
//		SmartDashboard.putNumber("intake motor voltage", GearIntake.getInstance().getMotorVoltage());
//		SmartDashboard.putNumber("intake motor current", GearIntake.getInstance().getMotorCurrent());
//
//		SmartDashboard.putNumber("Tune drive train value", DriveTrain.getInstance().getRightSetpoint());
//		SmartDashboard.putNumber("Tune drive train target speed", DriveTrain.getInstance().getRightSpeed());
//		System.out.println(DriveTrain.getInstance().getRightSpeed());
//		if (OI.getInstance().getIsBtn8Pressed()) {
//			lastSet = OI.getInstance().getWheelAxis2();
//			DriveTrain.getInstance().setRaw(0, lastSet);
//		}
//		if (OI.getInstance().getIsBtn11Pressed()) {
//			lastSet += 0.005;
//			DriveTrain.getInstance().setRaw(0, lastSet);
//		}
//		if (OI.getInstance().getIsBtn10Pressed()) {
//			lastSet -= 0.005;
//			DriveTrain.getInstance().setRaw(0, lastSet);
//		}
//		if (OI.getInstance().getIsBtn6Pressed()) {
//			System.out.println("btn 6 rpesed");
//			if (fw != null) {
//				System.out.println("appneding");
//				try {
//					fw.append(DriverStation.getInstance().getBatteryVoltage() + "`"
//							+ DriveTrain.getInstance().getRightSpeed() + "`"
//							+ DriveTrain.getInstance().getRightSetpoint() + "\n");
//				} catch (IOException e) {
//					e.printStackTrace();
//					System.out.println("failed to save");
//				}
//				System.out.println("saved");
//			} else {
//				System.out.println("failed to save");
//			}
//		}
//	}
//
//	@Override
//	public void testInit() {
//	}
//
//	@Override
//	public void testPeriodic() {
//		LiveWindow.run();
//	}
//}

package com.team3925.robot;

import java.io.File;
import java.util.ArrayList;

import com.team3925.robot.commands.DriveManual;
import com.team3925.robot.commands.ProcessAndSendTargetCameraFeed;
import com.team3925.robot.commands.ShootFuelStatic;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.DriveTrain.PolarDriveTrainState;
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
	public static Record<PolarDriveTrainState> recordOfDriveTrain;
	public static Record<GearIntakeState> recordOfGearIntake;
	public static RecordCommand<PolarDriveTrainState> recordCommand;
	public static RecordCommand<GearIntakeState> recordCommandForGearIntake;
	private RepeatCommand<PolarDriveTrainState> repeatCommand;
	private RepeatCommand<GearIntakeState> repeatCommandForGearIntake;
	private ShootFuelStatic shootCommand;
	// private FileWriter fw;
	// private double lastSet;

	public Robot() {
		recordCommand = new RecordCommand<PolarDriveTrainState>(DriveTrain.getInstance());
		repeatCommand = new RepeatCommand<PolarDriveTrainState>(DriveTrain.getInstance(), 0.1, 10);
		recordCommandForGearIntake = new RecordCommand<GearIntakeState>(GearIntake.getInstance());
		repeatCommandForGearIntake = new RepeatCommand<GearIntakeState>(GearIntake.getInstance(), 0.1, 10);
		shootCommand = new ShootFuelStatic();
		Gyro.getInstance();
		autoChooser = new SendableChooser<>();
		// lastSet = 0;

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
		// try {
		// fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void disabledInit() {
		// try {
		// fw.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		Gyro.getInstance().setAngleToZero();
		// recordOfDriveTrain = Record.recall(new
		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO DRIVE SAVE PATH",
		// "/home/lvuser/autos/drive default auto.txt")),
		// DriveTrainState::fromString).orElse(null);
		// recordOfGearIntake = Record.recall(new
		// File(RIOConfigs.getInstance().getConfigOrAdd("AUTO GEAR SAVE PATH",
		// "/home/lvuser/autos/gear default auto.txt")),
		// GearIntakeState::fromString).orElse(null);

		RIOConfigs.getInstance().reloadConfigs();

		recordOfDriveTrain = recordCommand.get();
		recordOfGearIntake = recordCommandForGearIntake.get();

		if (SmartDashboard.getBoolean("DO SAVE AUTO", false)) {
			System.out.println("WE SAVED");
			recordCommand.get().save(new File(
					SmartDashboard.getString("AUTO DRIVE SAVE PATH", "/home/lvuser/autos/drive default auto.txt")),
					PolarDriveTrainState::toString);
			recordCommandForGearIntake.get().save(new File(
					SmartDashboard.getString("AUTO GEARS SAVE PATH", "/home/lvuser/autos/gears default auto.txt")),
					GearIntakeState::toString);
		}
		if (SmartDashboard.getBoolean("DO LOAD AUTO", false)) {
			System.out.println("WE LOADED");
			recordOfDriveTrain = Record
					.recall(new File(SmartDashboard.getString("AUTO DRIVE RECALL PATH",
							"/home/lvuser/autos/drive default auto.txt")), PolarDriveTrainState::fromString)
					.orElse(null);
			recordOfGearIntake = Record.recall(new File(
					SmartDashboard.getString("AUTO GEARS RECALL PATH", "/home/lvuser/autos/gears default auto.txt")),
					GearIntakeState::fromString).orElse(null);
		} else {
			// recordOfDriveTrain = null;
			// recordOfGearIntake = null;
			// ArrayList<File> auto = autoChooser.getSelected();
			// if (auto !=null) {
			// if (auto.size()>1) {
			// if (auto.get(0)!=null)
			// recordOfDriveTrain = Record.recall(auto.get(0),
			// DriveTrainState::fromString).orElse(null);
			// if (auto.get(1)!=null)
			// recordOfGearIntake= Record.recall(auto.get(1),
			// GearIntakeState::fromString).orElse(null);
			// }else if (auto.size()>0){
			// if (auto.get(0)!=null)
			// recordOfDriveTrain = Record.recall(auto.get(0),
			// DriveTrainState::fromString).orElse(null);
			// }else {
			// }
			// }
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
		Gyro.getInstance().setAngleToZero();
		DRIVE_MANUAL.start();
		recordCommandForGearIntake.reset();
		recordCommandForGearIntake.start();
		recordCommand.reset();
		recordCommand.start();
		// try {
		// fw = new FileWriter(new File("/home/lvuser/tuningdrivetrain.txt"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("intake motor voltage", GearIntake.getInstance().getMotorVoltage());
		SmartDashboard.putNumber("intake motor current", GearIntake.getInstance().getMotorCurrent());

		// SmartDashboard.putNumber("Tune drive train value",
		// DriveTrain.getInstance().getRightSetpoint());
		// SmartDashboard.putNumber("Tune drive train target speed",
		// DriveTrain.getInstance().getRightSpeed());
		// System.out.println(DriveTrain.getInstance().getRightSpeed());
		// if (OI.getInstance().getIsBtn8Pressed()) {
		// lastSet = OI.getInstance().getWheelAxis2();
		// DriveTrain.getInstance().setRaw(0, lastSet);
		// }
		// if (OI.getInstance().getIsBtn11Pressed()) {
		// lastSet += 0.005;
		// DriveTrain.getInstance().setRaw(0, lastSet);
		// }
		// if (OI.getInstance().getIsBtn10Pressed()) {
		// lastSet -= 0.005;
		// DriveTrain.getInstance().setRaw(0, lastSet);
		// }
		// if (OI.getInstance().getIsBtn6Pressed()) {
		// System.out.println("btn 6 rpesed");
		// if (fw != null) {
		// System.out.println("appneding");
		// try {
		// fw.append(DriverStation.getInstance().getBatteryVoltage() + "`"
		// + DriveTrain.getInstance().getRightSpeed() + "`"
		// + DriveTrain.getInstance().getRightSetpoint() + "\n");
		// } catch (IOException e) {
		// e.printStackTrace();
		// System.out.println("failed to save");
		// }
		// System.out.println("saved");
		// } else {
		// System.out.println("failed to save");
		// }
		// }
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
