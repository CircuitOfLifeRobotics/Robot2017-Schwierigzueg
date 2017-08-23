package com.team3925.robot;

import com.team3925.robot.commands.AgitatorShoot;

import java.util.function.Supplier;

import com.team3925.robot.commands.AgitatorOff;
import com.team3925.robot.commands.AgitatorReverse;
import com.team3925.robot.commands.DriveManual.DriveManualInput;
import com.team3925.robot.commands.IntakeShoot;
import com.team3925.robot.commands.IntakeOff;
import com.team3925.robot.commands.IntakeOut;
import com.team3925.robot.commands.ShooterOff;
import com.team3925.robot.commands.ShooterReverse;
import com.team3925.robot.commands.ShooterShoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI implements DriveManualInput, Supplier<Double> {

	private final Joystick stick;

	private static OI instance;

	private OI() {
		stick = new Joystick(0);
		JoystickButton agitatorIn = new JoystickButton(stick, 5);
		JoystickButton agitatorOut = new JoystickButton(stick, 6);
		agitatorIn.whenActive(AgitatorShoot.getInstance());
		agitatorIn.whenInactive(AgitatorOff.getInstance());
		agitatorOut.whenActive(AgitatorReverse.getInstance());
		agitatorOut.whenInactive(AgitatorOff.getInstance());
		JoystickButton shooterShoot = new JoystickButton(stick, 3);
		JoystickButton shooterReverse = new JoystickButton(stick, 4);
		shooterShoot.whenActive(ShooterShoot.getInstance());
		shooterShoot.whenInactive(ShooterOff.getInstance());
		shooterReverse.whenActive(ShooterReverse.getInstance());
		shooterReverse.whenInactive(ShooterOff.getInstance());
		JoystickButton intakeIn = new JoystickButton(stick, 1);
		JoystickButton intakeOut = new JoystickButton(stick, 2);
		intakeIn.whenActive(IntakeShoot.getInstance());
		intakeIn.whenInactive(IntakeOff.getInstance());
		intakeOut.whenActive(IntakeOut.getInstance());
		intakeOut.whenInactive(IntakeOff.getInstance());
	}

	public static OI getInstance() {
		return instance == null ? instance = new OI() : instance;
	}

	@Override
	public double getFwd() {
		return stick.getRawAxis(1);
	}

	@Override
	public double getLeft() {
		return stick.getRawAxis(0);
	}

	@Override
	public Double get() {
		return stick.getRawAxis(2);
	}

}
