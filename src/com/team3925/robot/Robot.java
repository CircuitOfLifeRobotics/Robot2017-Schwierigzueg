package com.team3925.robot;

import com.team3925.robot.commands.BlueFeederSide;
import com.team3925.robot.commands.Center;
import com.team3925.robot.commands.DriveForward;
import com.team3925.robot.commands.DriveManual;
import com.team3925.robot.commands.RedFeederSide;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.GearIntake;
import com.team3925.robot.subsystems.Gyro;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static DriveManual DRIVE_MANUAL = new DriveManual(OI.getInstance());
	Command test;
	SendableChooser autoChuzer;
 
	public Robot() {
		Gyro.getInstance().setAngleToZero();
		autoChuzer = new SendableChooser<Command>();
	}

	@Override
	public void robotInit() {
		autoChuzer.addDefault("Center", new Center());
		autoChuzer.addObject("Blue Feeder", new BlueFeederSide());
		autoChuzer.addObject("Red Feeder", new RedFeederSide());
		autoChuzer.addObject("Drive Forward", new DriveForward());
		autoChuzer.addObject("Do Nothing", new CommandGroup(){
			
		});
		SmartDashboard.putData("Auto Chuzer", autoChuzer);
		//COMMENT IN THE AUTO YOU WANT BELOW
//		test = new RedFeederSide();
//		test = new Center();7
		
		killRobot();
		CameraServer.getInstance().startAutomaticCapture();
	}

	
	@Override
	public void disabledInit() {
		killRobot();
		System.out.println("Disabled");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
//		test = autoChuzer.getSelected();
//		RIOConfigs.getInstance().reloadConfigs();
//		Gyro.getInstance().setAngleToZero();
		killRobot();
		test = (Command) autoChuzer.getSelected();
		test.start();
//		DRIVE_MANUAL.start();
		
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
//		test.cancel();
	}

	@Override
	public void teleopInit() {
		Gyro.getInstance().setAngleToZero();
		DRIVE_MANUAL.start();
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
		
		System.out.println(Gyro.getInstance().getFusedHeading());
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void killRobot() {
		System.out.println("KILLLLLL");
		DriveTrain.getInstance().setRaw(0, 0);
		GearIntake.getInstance().setMotor(0);
		GearIntake.getInstance().setSolenoid(true);
	}
}
