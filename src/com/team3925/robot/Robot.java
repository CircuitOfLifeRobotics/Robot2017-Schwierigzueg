
package com.team3925.robot;

import com.team3925.autoRoutines.BoilerAuto;
import com.team3925.autoRoutines.BoilerShootAuto;
import com.team3925.autoRoutines.CenterAuto;
import com.team3925.autoRoutines.CenterShootAuto;
import com.team3925.autoRoutines.FeederAuto;
import com.team3925.autoRoutines.GearHopperAuto;
import com.team3925.autoRoutines.HopperAuto;
import com.team3925.autoRoutines.TestAuto;
import com.team3925.autoRoutines.TwoGearCenter;
import com.team3925.autoRoutines.recorded.BoilerGearShootRecorded;
import com.team3925.autoRoutines.recorded.CenterGearShootRecorded;
import com.team3925.autoRoutines.recorded.ExampleRecorded;
import com.team3925.autoRoutines.recorded.FeederGearShootRecorded;
import com.team3925.commands.SaveRecordSmartDashboard;
import com.team3925.commands.Timeout;
import com.team3925.commands.Vision;
import com.team3925.commands.climber.ClimberToggle;
import com.team3925.commands.compound.TurnShoot;
import com.team3925.commands.driveTrain.DriveManual;
import com.team3925.commands.driveTrain.DriveTrainShiftHigh;
import com.team3925.commands.driveTrain.DriveTrainShiftLow;
import com.team3925.commands.driveTrain.ToggleManualDriveOnlyForward;
import com.team3925.commands.feeder.SetFeeder;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.commands.intake.IntakeGoUp;
import com.team3925.commands.intake.IntakeWheelsIn;
import com.team3925.commands.intake.IntakeWheelsOff;
import com.team3925.commands.shooter.SetShooter;
import com.team3925.commands.shooter.SetShooterSmartDashboard;
import com.team3925.commands.shooter.TrimShooterDown;
import com.team3925.commands.shooter.TrimShooterUp;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;
import com.team3925.subsystems.Shooter;
import com.team3925.util.SingleCommandGroup;
import com.team3925.util.recording.Recorder;
import com.team3925.util.recording.RobotRecorder;
import com.team3925.util.recording.RobotState;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	//As of 7:12 AM 4/21/20
	private SendableChooser<String> sideChooser;
	private SendableChooser<String> autoChooser;
	private String chosenSide;

	private DriveManual driveManual;

	public static final String recordFilePath = "/home/lvuser/robotRecords/";
	public static final String recordExtension = ".record";

	public static NetworkTable visionData;

	private Recorder<RobotState> botRecorder;

	@Override
	public void robotInit() {
		Navx.getInstance().resetNavx();
		DriveTrain.getInstance().zeroEncoders();
		SmartDashboard.putString("Record Auto File Name", recordFilePath + "none" + recordExtension);

		botRecorder = new Recorder<>(new RobotRecorder());

		visionData = NetworkTable.getTable("GRIP/Vision");
		DriveTrain.getInstance().zeroEncoders();
		new IntakeGoUp().start();

		sideChooser = new SendableChooser<>();
		sideChooser.addDefault("Red", "RED");
		sideChooser.addObject("Blue", "BLUE");
		SmartDashboard.putData("SIDE PICK", sideChooser);

		autoChooser = new SendableChooser<>();
		autoChooser.addObject(" Center ", "CENTER");
		autoChooser.addObject("center Shoot", "CENTER SHOOT");
		autoChooser.addObject("Boiler Gear", "BOILER");
		autoChooser.addObject("Boiler Gear shootz", "BOILER SHOOT");
		autoChooser.addObject("Feeder Gear", "FEEDER");
		autoChooser.addObject("hopper", "HOPPER");
		autoChooser.addObject("Test Auto", "TEST");
		SmartDashboard.putData("AUTO PICK", autoChooser);

		driveManual = new DriveManual(OI.getInstance());
		Vision.getInstance();
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void disabledInit() {
		driveManual.cancel();
		botRecorder.cancel();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		DriveTrain.getInstance().zeroEncoders();
		chosenSide = sideChooser.getSelected();
		CommandGroup chosenAuto = null;

		switch (autoChooser.getSelected()) {
		case "CENTER":
			chosenAuto = new CenterAuto(chosenSide);
			break;
		case "CENTER SHOOT":
			chosenAuto = new CenterShootAuto(chosenSide);
			break;
		case "BOILER":
			chosenAuto = new BoilerAuto(chosenSide);
			break;
		case "BOILER SHOOT":
			chosenAuto = new BoilerShootAuto(chosenSide);
			break;
		case "FEEDER":
			chosenAuto = new FeederAuto(chosenSide);
			break;
		case "HOPPER":
			chosenAuto = new HopperAuto(chosenSide);
			break;
		case "GEAR HOPPER":
			chosenAuto = new GearHopperAuto(chosenSide);
			break;
		case "TWO GEAR":
			chosenAuto = new TwoGearCenter(chosenSide);
			break;
		case "TEST":
			chosenAuto = new TestAuto();
			break;
		case "RECORD":
			DriveTrain.getInstance().enableSpeed();
			botRecorder.cancel();
			botRecorder.setModePlayback();
			botRecorder.start();
			chosenAuto = new SingleCommandGroup();
			break;
		case "EXAMPLE_RECORD":
			chosenAuto = new ExampleRecorded();
		case "REC_BGS":
			chosenAuto = new BoilerGearShootRecorded();
		case "REC_CGS":
			chosenAuto = new CenterGearShootRecorded();
		case "REC_FGS":
			chosenAuto = new FeederGearShootRecorded();
			break;
		}
		chosenAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Drivetrain left enc pos", DriveTrain.getInstance().getLeftEncRaw());
		SmartDashboard.putNumber("Drivetrain right enc pos", DriveTrain.getInstance().getRightEncRaw());
		SmartDashboard.putNumber("Drivetrain left enc vel", DriveTrain.getInstance().getLeftEncVel()/4096*600);
		SmartDashboard.putNumber("Drivetrain right enc vel", DriveTrain.getInstance().getRightEncVel()/4096*600);
		
	}

	@Override
	public void teleopInit() {
		// Intake
		CommandGroup runIntake = new CommandGroup();
		CommandGroup stopIntake = new CommandGroup();
		{
			runIntake.addSequential(new IntakeWheelsIn());
			runIntake.addParallel(new IntakeGoDown());
			stopIntake.addSequential(new IntakeGoUp());
			stopIntake.addSequential(new Timeout(1));
			stopIntake.addSequential(new IntakeWheelsOff());
		}
		OI.getInstance().whenXboxButtonPressed(1, new IntakeGoDown());
		OI.getInstance().whenXboxButtonReleased(1, new IntakeGoUp());
		OI.getInstance().whenXboxButtonPressed(2, new IntakeWheelsIn());
		OI.getInstance().whenXboxButtonReleased(2, new IntakeWheelsOff());
		OI.getInstance().whenXboxButtonPressed(3, runIntake);
		OI.getInstance().whenXboxButtonReleased(3, stopIntake);

		// Shifting
		OI.getInstance().whenWheelButtonPressed(5, new DriveTrainShiftLow());
		OI.getInstance().whenWheelButtonReleased(5, new DriveTrainShiftHigh());

		// Climbing
		OI.getInstance().whenXboxButtonPressed(4, new ClimberToggle());
//		OI.getInstance().whenXboxButtonPressed(4, new ToggleManualDriveOnlyForward(driveManual));
		OI.getInstance().whenStickButtonPressed(1, new ClimberToggle());
//		OI.getInstance().whenStickButtonPressed(1, new ToggleManualDriveOnlyForward(driveManual));

		// Auto Shooting
		CommandGroup turnShootEnd = new CommandGroup();
		turnShootEnd.addSequential(new SetFeeder(0));
		turnShootEnd.addSequential(new SetShooter(0));
		turnShootEnd.addSequential(new Command() {
			@Override
			protected void initialize() {
				driveManual.start();
			}

			@Override
			protected boolean isFinished() {
				return true;
			}
		});
		OI.getInstance().whenXboxButtonPressed(8, new TurnShoot(0));
		OI.getInstance().whenXboxButtonReleased(8, turnShootEnd);

		// Shooting

		OI.getInstance().whenXboxButtonPressed(5, new TrimShooterUp());
		OI.getInstance().whenXboxButtonPressed(6, new TrimShooterDown());

		driveManual.start();

		OI.getInstance().whenXboxButtonPressed(9, new SaveRecordSmartDashboard(botRecorder, "Record Auto File Name"));
		botRecorder.cancel();
		botRecorder.setModeRecord();
		botRecorder.start();

	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Drivetrain left enc pos", DriveTrain.getInstance().getLeftEncRaw());
		SmartDashboard.putNumber("Drivetrain right enc pos", DriveTrain.getInstance().getRightEncRaw());
		SmartDashboard.putNumber("Drivetrain left enc vel", DriveTrain.getInstance().getLeftEncVel());
		SmartDashboard.putNumber("Drivetrain right enc vel", DriveTrain.getInstance().getRightEncVel());
		
		SmartDashboard.putNumber("Distance", Vision.getInstance().getDistance());
		SmartDashboard.putNumber("Shooter enc vel", Shooter.getInstance().getShooterVel()*60*10/4098);
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

}
