
package org.usfirst.frc.team3925.robot;

import com.team3925.team3925.robot.commands_subsystems.CenterAuto;
import com.team3925.team3925.robot.commands_subsystems.DrivePath;
import com.team3925.team3925.robot.commands_subsystems.KeepTurretAimed;
import com.team3925.team3925.robot.commands_subsystems.KeepTurretHeading;
import com.team3925.team3925.robot.commands_subsystems.LeftBackAutoRoutine;
import com.team3925.team3925.robot.commands_subsystems.ManualDrive;
import com.team3925.team3925.robot.commands_subsystems.PanTurret;
import com.team3925.team3925.robot.commands_subsystems.RESET_AUTO;
import com.team3925.team3925.robot.commands_subsystems.ResetSystems;
import com.team3925.team3925.robot.commands_subsystems.TestCommand;
import com.team3925.team3925.robot.commands_subsystems.WaitForTarget;
import com.team3925.team3925.robot.commands_subsystems.ZeroTurret;
import com.team3925.team3925.robot.triggers.OnCommandEnd;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	
	private SendableChooser<Command> chooser = new SendableChooser<>();
	
	private CommandGroup autoDriveSequence, backgroundTurretSequence, 
						autoTurretSequence, leftBack, centerAuto, resetAuto;

	private Command testCommand, resetSystems, manualDrive;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		testCommand = new TestCommand();
//		chooser.addObject("Gear Left | Cross Line", );
		SmartDashboard.putData("Auto mode", chooser);
		
		//keeps turret aimed until command stops (loses aim), then keeps heading and pans while searching for target
		backgroundTurretSequence = new CommandGroup();
		backgroundTurretSequence.addSequential(KeepTurretAimed.getInstance());
		backgroundTurretSequence.addSequential(WaitForTarget.getInstance());
		backgroundTurretSequence.addParallel(KeepTurretHeading.getInstance(), 10);
		backgroundTurretSequence.addSequential(PanTurret.getInstance());
		
		//restarts background turret sequence once target is seen (WaitForTarget command ends)
		OnCommandEnd loseSightOfTarget = new OnCommandEnd(WaitForTarget.getInstance());
		loseSightOfTarget.cancelWhenActive(backgroundTurretSequence);
		loseSightOfTarget.whenActive(backgroundTurretSequence);
		
		//initializes the turret and starts the background sequence
		autoTurretSequence = new CommandGroup();
		autoTurretSequence.addSequential(ZeroTurret.getInstance());
//		autoTurretSequence.addSequential(PanTurret.getInstance());
//		autoTurretSequence.addParallel(WaitForTarget.getInstance());
		autoTurretSequence.addSequential(backgroundTurretSequence);
		leftBack = new LeftBackAutoRoutine();
		centerAuto = new CenterAuto();
		resetSystems = new ResetSystems();
		resetAuto = new RESET_AUTO();
		manualDrive = new ManualDrive();
	}
	
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		if (autoDriveSequence!=null)
			autoDriveSequence.cancel();
		if (autoTurretSequence!=null)
			autoTurretSequence.cancel();
//		if (backgroundTurretSequence!=null)
//			backgroundTurretSequence.cancel();
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		resetSystems.start();
		String auto = SmartDashboard.getString("Auto Selector", "Default");
		switch (auto) {
		case "Default":
			//does all autonomous driving
			autoDriveSequence = new CommandGroup();
			autoDriveSequence.addSequential(new DrivePath(null));
//			autoDriveSequence.addSequential(DriveGear.getInstance());
//			autoDriveSequence.addSequential(WaitForGear.getInstance());
			autoDriveSequence.addSequential(new DrivePath(null));
			break;
		default:
			break;
		}
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
//		 schedule the autonomous command (example)
//		testCommand.start();
		resetSystems.start();
		leftBack.start();
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		centerAuto.start();
		resetSystems.start();
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
		// TODO Auto-generated method stub
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
