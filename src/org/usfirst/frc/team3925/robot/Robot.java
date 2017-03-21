
package org.usfirst.frc.team3925.robot;

import com.team3925.team3925.robot.commands_subsystems.AlternatePurpleLEDS;
import com.team3925.team3925.robot.commands_subsystems.AutoDriveTimed;
import com.team3925.team3925.robot.commands_subsystems.CenterAutoLeft;
import com.team3925.team3925.robot.commands_subsystems.CenterAutoRight;
import com.team3925.team3925.robot.commands_subsystems.CenterGearAuto;
import com.team3925.team3925.robot.commands_subsystems.LeftBackAutoRoutine;
import com.team3925.team3925.robot.commands_subsystems.ManualDrive;
import com.team3925.team3925.robot.commands_subsystems.ResetSystems;
import com.team3925.team3925.robot.commands_subsystems.RightBackAutoRoutine;
import com.team3925.team3925.robot.commands_subsystems.RunShooter;
import com.team3925.team3925.robot.commands_subsystems.ShiftLow;
import com.team3925.team3925.robot.commands_subsystems.TestCommand;
import com.team3925.team3925.robot.commands_subsystems.ToggleClimber;
import com.team3925.team3925.robot.triggers.Trigger20SecondsLeftInMatch;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * POPULATED: FALSE
 * WHEN: 
 * TESTED: FALSE
 * WHEN: 
 */

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	private SendableChooser<CommandGroup> chooser = new SendableChooser<>();
	
	private CommandGroup leftBack, centerAutoLeft, chosenAuto, centerAutoRight,
						rightBack, autoTimed, gearCenter;

	private Command testCommand, resetSystems, manualDrive, shiftLow, runShooter, toggleClimber;
	private Command endOfMatchLEDS;
	private Trigger closeToEndOfMatch;
	
	private UsbCamera cam;
	
	public static Joystick wheel, stick, xbox;
	
	public static Solenoid redLightsA, redLightsB, blueLightsA, blueLightsB;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//init
//		CameraServer.getInstance().startAutomaticCapture();
//		cam = new UsbCamera("cam0", 0);
//		MjpegServer server = new MjpegServer("Gear server", 1180);
//		server.setSource(cam);
//		cam.setBrightness(70);
//		cam.setExposureManual(40);
//		cam.setFPS(30);
//		cam.setWhiteBalanceAuto();
//		cam.setResolution(480, 480);
		//end init
		
		wheel= new Joystick(0);
		stick= new Joystick(1);
		xbox= new Joystick(2);
		testCommand = new TestCommand();
		
		
		leftBack = new LeftBackAutoRoutine();
		centerAutoLeft = new CenterAutoLeft();
		resetSystems = new ResetSystems();
		manualDrive = new ManualDrive();
		shiftLow = new ShiftLow(true);
		runShooter = new RunShooter();
		toggleClimber = new ToggleClimber();
		centerAutoRight = new CenterAutoRight();
		rightBack = new RightBackAutoRoutine();
		autoTimed = new CommandGroup();
		gearCenter = new CenterGearAuto();
		autoTimed.addSequential(AutoDriveTimed.getInstance());
		
		closeToEndOfMatch = new Trigger20SecondsLeftInMatch();
		endOfMatchLEDS = new AlternatePurpleLEDS();
//		closeToEndOfMatch.whenActive(endOfMatchLEDS);
		
		chooser.addDefault("Blue Center", centerAutoLeft);
		chooser.addObject("Blue Back", leftBack);
		chooser.addObject("Red Center", centerAutoRight);
		chooser.addObject("Red Back", rightBack);
		chooser.addObject("No Encoder", autoTimed);
		chooser.addObject("Do Nothing", new CommandGroup());
		chooser.addObject("CENTER AUTO", gearCenter);
		SmartDashboard.putData("Auto Chooser (3.15.17@6:37)", chooser);
		resetSystems.start();
		
	}
	
	@Override
	public void disabledInit() {
		shiftLow.start();
		try {chosenAuto.cancel();}catch(Exception e){}
		try {
		cam.free();
		}catch(NullPointerException e) {}
		try {
			redLightsA.free();
			redLightsB.free();
			blueLightsA.free();
			blueLightsB.free();
		}catch(NullPointerException e) {}
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		resetSystems.start();
//		chosenAuto = autoTimed;
//		chosenAuto.start();
		chosenAuto = chooser.getSelected();
		chosenAuto.start();
		System.out.println(chosenAuto.getName());
//		testCommand.start();
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		try {chosenAuto.cancel();}catch(Exception e){}
		
		redLightsA = new Solenoid(5);
		redLightsB = new Solenoid(7);
		blueLightsA = new Solenoid(4);
		blueLightsB = new Solenoid(6);
		
		redLightsA.clearAllPCMStickyFaults();
		redLightsB.clearAllPCMStickyFaults();
		blueLightsA.clearAllPCMStickyFaults();
		blueLightsB.clearAllPCMStickyFaults();
		if (DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Blue)) {
			redLightsA.set(false);
			redLightsB.set(false);
			blueLightsA.set(true);
			blueLightsB.set(true);
		}else if (DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Red)) {
			redLightsA.set(true);
			redLightsB.set(true);
			blueLightsA.set(false);
			blueLightsB.set(false);
		}else {
			redLightsA.set(true);
			redLightsB.set(true);
			blueLightsA.set(true);
			blueLightsB.set(true);
		}
		shiftLow.start();
		runShooter.start();
		toggleClimber.start();
		manualDrive.start();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testInit() {
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		if (!testCommand.isRunning()){
		}
	}
}
