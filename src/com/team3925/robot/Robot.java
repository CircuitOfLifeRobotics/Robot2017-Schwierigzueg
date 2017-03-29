
package com.team3925.robot;

import com.team3925.commands.ArcadeDrive;
import com.team3925.commands.ClimberDisengage;
import com.team3925.commands.ClimberEngage;
import com.team3925.commands.ClimberToggle;
import com.team3925.commands.DriveForward;
import com.team3925.commands.DriveManual;
import com.team3925.commands.DriveTrainShiftHigh;
import com.team3925.commands.DriveTrainShiftLow;
import com.team3925.commands.GyroDrive;
import com.team3925.commands.GyroTurn;
import com.team3925.commands.IntakeGoDown;
import com.team3925.commands.IntakeGoUp;
import com.team3925.commands.IntakeWheelsIn;
import com.team3925.commands.IntakeWheelsOff;
import com.team3925.commands.Timeout;
import com.team3925.subsystems.DriveTrain;

import autoRoutines.BoilerAuto;
import autoRoutines.CenterAuto;
import autoRoutines.FeederAuto;
import autoRoutines.TwoGearCenter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private OI oi;
	private DriveManual driveManual;
	private GyroDrive driveForward;
	private CenterAuto centerAuto;
	private SendableChooser<CommandGroup> autoChooser;
	private CommandGroup chosenAuto;

	@Override
	public void robotInit() {
		oi = OI.getInstance();
		new IntakeGoUp().start();
		driveManual = new DriveManual(OI.getInstance());
		driveForward = new GyroDrive(10);
		centerAuto = new CenterAuto("BLUE");
		autoChooser = new SendableChooser<>();
		
		autoChooser.addDefault("Blue Center Auto", new CenterAuto("BLUE"));
		autoChooser.addDefault("Red Center Auto", new CenterAuto("RED"));
		autoChooser.addObject("Blue Boiler Auto", new BoilerAuto("BLUE"));
		autoChooser.addObject("Red Boiler Auto", new BoilerAuto("RED"));
		autoChooser.addObject("Red Feeder Auto", new FeederAuto("RED"));
		autoChooser.addObject("Blue Feeder Auto", new FeederAuto("BLUE"));
		autoChooser.addObject("Blue Two Gear Auto", new TwoGearCenter("BLUE"));
		
		SmartDashboard.putData("AUTO CHOOSER", autoChooser);
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
		chosenAuto = autoChooser.getSelected();
		chosenAuto.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		
		CommandGroup runIntake = new CommandGroup();
		runIntake.addSequential(new IntakeWheelsIn());
		runIntake.addParallel(new IntakeGoDown());
		
		CommandGroup stopIntake = new CommandGroup();
		stopIntake.addSequential(new IntakeGoUp());
		stopIntake.addSequential(new Timeout(1));
		stopIntake.addSequential(new IntakeWheelsOff());
		
		// intake controls
		oi.whenXboxButtonPressed(3, runIntake);
		oi.whenXboxButtonReleased(3, stopIntake);

		oi.whenXboxButtonPressed(1, new IntakeGoDown());
		oi.whenXboxButtonReleased(1, new IntakeGoUp());

		oi.whenXboxButtonPressed(2, new IntakeWheelsIn());
		oi.whenXboxButtonReleased(2, new IntakeWheelsOff());

		// shifting controls
		oi.whenWheelButtonPressed(5, new DriveTrainShiftHigh());
		oi.whenWheelButtonReleased(5, new DriveTrainShiftLow());

		// climbing controls
		oi.whenXboxButtonPressed(4, new ClimberToggle());
		
		driveManual.start();
		
		Trigger trigger = new Trigger() {
			
			@Override
			public boolean get() {
				return DriveTrain.getInstance().isOverThreshold();
			}
		};
		
//		trigger.whenActive(new DriveTrainShiftHigh());
//		trigger.whenInactive(new DriveTrainShiftLow());
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
