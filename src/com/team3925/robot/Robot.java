
package com.team3925.robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.team3925.autoRoutines.BoilerAuto;
import com.team3925.autoRoutines.BoilerShootAuto;
import com.team3925.autoRoutines.CenterAuto;
import com.team3925.autoRoutines.CenterShootAuto;
import com.team3925.autoRoutines.FeederAuto;
import com.team3925.autoRoutines.GearHopperAuto;
import com.team3925.autoRoutines.HopperAuto;
import com.team3925.autoRoutines.TestAuto;
import com.team3925.autoRoutines.TwoGearCenter;
import com.team3925.commands.Timeout;
import com.team3925.commands.Vision;
import com.team3925.commands.climber.ClimberToggle;
import com.team3925.commands.compound.TurnShoot;
import com.team3925.commands.compound.TurnShootEnd;
import com.team3925.commands.driveTrain.DriveManual;
import com.team3925.commands.driveTrain.DriveTrainShiftHigh;
import com.team3925.commands.driveTrain.DriveTrainShiftLow;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.commands.driveTrain.GyroTurnDynamic;
import com.team3925.commands.feeder.SetFeeder;
import com.team3925.commands.intake.IntakeGoDown;
import com.team3925.commands.intake.IntakeGoUp;
import com.team3925.commands.intake.IntakeWheelsIn;
import com.team3925.commands.intake.IntakeWheelsOff;
import com.team3925.commands.shooter.SetShooter;
import com.team3925.commands.shooter.SetShooterDynamic;
import com.team3925.commands.shooter.SetShooterSmartDashboard;
import com.team3925.commands.shooter.TrimShooterDown;
import com.team3925.commands.shooter.TrimShooterUp;
import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Feeder;
import com.team3925.subsystems.Intake;
import com.team3925.subsystems.Navx;
import com.team3925.subsystems.Shooter;
import com.team3925.util.SingleCommandGroup;
import com.team3925.util.recording.DriveTrainState;
import com.team3925.util.recording.Recordable;
import com.team3925.util.recording.Recorder;
import com.team3925.util.recording.RobotState;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot implements Recordable<RobotState> {
	private SendableChooser<String> sideChooser;
	private SendableChooser<String> autoChooser;
	private String chosenSide;

	private DriveManual driveManual;

	private String recordName = "none";
	private final String recordFilePath = "/home/lvuser/robotRecords/";
	private final String recordExtension = ".record";

	public static NetworkTable visionData;

	private Recorder<DriveTrainState> driveTrainRecorder;

	@Override
	public void robotInit() {
		Navx.getInstance().resetNavx();
		DriveTrain.getInstance().zeroEncoders();
		Vision.getInstance().start();
		SmartDashboard.putString("Record Auto File Name", recordName);

		driveTrainRecorder = new Recorder<>(DriveTrain.getInstance());

		visionData = NetworkTable.getTable("GRIP/Vision");
		DriveTrain.getInstance().zeroEncoders();
		new IntakeGoUp().start();

		sideChooser = new SendableChooser<>();
		sideChooser.addDefault("Red", "RED");
		sideChooser.addObject("Blue", "BLUE");
		SmartDashboard.putData("SIDE PICK", sideChooser);

		autoChooser = new SendableChooser<>();
		autoChooser.addObject("lit Center shoz", "CENTER SHOOT");
		autoChooser.addObject("Boiler Gear", "BOILER");
		autoChooser.addObject("Boiler Gear den da shotz", "BOILER SHOOT");
		autoChooser.addObject("Feeder Gear", "FEEDER");
		autoChooser.addObject("Tap da hopper boi shoz", "HOPPER");
		autoChooser.addObject("Test Auto", "TEST");
		SmartDashboard.putData("AUTO PICK", autoChooser);

		driveManual = new DriveManual(OI.getInstance());

	}

	@Override
	public void disabledInit() {
		driveTrainRecorder.cancel();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		if (!Vision.getInstance().isRunning())
			Vision.getInstance().start();
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
		case "Record Auto":
			driveTrainRecorder.cancel();
			driveTrainRecorder.setModePlayback();
			driveTrainRecorder.start();
			chosenAuto = new SingleCommandGroup();
			break;
		}
		System.out.println(chosenAuto.getName());
		chosenAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("LEFT VEL", DriveTrain.getInstance().getleftA().getEncVelocity());
		SmartDashboard.putNumber("RIGHT VEL", DriveTrain.getInstance().getrightA().getEncVelocity());
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// Intake

		if (!Vision.getInstance().isRunning())
			Vision.getInstance().start();
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
		OI.getInstance().whenStickButtonPressed(2, new SetFeeder(-.65));
		OI.getInstance().whenStickButtonReleased(2, new SetFeeder(0));
		OI.getInstance().whenStickButtonPressed(1, new SetShooterSmartDashboard());
		OI.getInstance().whenStickButtonReleased(1, new SetShooter(0));

		OI.getInstance().whenXboxButtonPressed(5, new TrimShooterUp());
		OI.getInstance().whenXboxButtonPressed(6, new TrimShooterDown());

		driveManual.start();

		OI.getInstance().whenXboxButtonPressed(9, new Command() {
			@Override
			protected void initialize() {
				try {
					Recorder.save(driveTrainRecorder,
							new FileOutputStream(recordFilePath + recordName + recordExtension));
				} catch (FileNotFoundException e) {
					System.out.println("the file was not found");
					e.printStackTrace();
					System.exit(0);
				} catch (IOException e) {
					System.out.println("the io was excepted");
					System.exit(0);
				}
			}

			@Override
			protected boolean isFinished() {
				return true;
			}
		});
		OI.getInstance().whenXboxButtonPressed(10, new Command() {
			@Override
			protected void initialize() {
				try {
					driveTrainRecorder = (Recorder<DriveTrainState>) Recorder
							.recall(new FileInputStream(recordFilePath + recordName + recordExtension));
					System.out.println("recalled!");
				} catch (ClassNotFoundException e) {
					System.out.println("the clazz was not found");
					System.exit(0);
				} catch (FileNotFoundException e) {
					System.out.println("the file was not fouhnd");
					System.exit(0);
				} catch (IOException e) {
					System.out.println("the io was excepted");
					System.exit(0);
				}
			}

			@Override
			protected boolean isFinished() {
				return true;
			}
		});
		driveTrainRecorder.cancel();
		driveTrainRecorder.setModeRecord();
		driveTrainRecorder.start();

	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Shooter Vel", Shooter.getInstance().getShooterVel() / 4089 * 10 * 60);
		SmartDashboard.putNumber("Feeder Vel", Feeder.getInstance().getFeederVel() / 4089 * 10 * 60);
		SmartDashboard.putNumber("Shooter Error", Shooter.getInstance().getError() / 4089 * 10 * 60);
		SmartDashboard.putNumber("Distance", Vision.getInstance().getDistance());
		SmartDashboard.putNumber("Offset Angle", Vision.getInstance().getTurnAngle());
		SmartDashboard.putNumber("Shooter Set", Vision.getInstance().getSpeed() - Shooter.getInstance().SHOOTER_TRIM);
		SmartDashboard.putNumber("Navx heading", Navx.getInstance().getHeading());
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

	@Override
	public RobotState record() {
		return new RobotState(DriveTrain.getInstance().record(), Feeder.getInstance().record(),
				Intake.getInstance().record(), Shooter.getInstance().record());
	}

	@Override
	public void repeat(RobotState action) {
		DriveTrain.getInstance().repeat(action.driveTrainState);
		Feeder.getInstance().repeat(action.feederState);
		Intake.getInstance().repeat(action.intakeState);
		Shooter.getInstance().repeat(action.shooterState);
	}
}
