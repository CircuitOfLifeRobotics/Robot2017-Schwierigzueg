package com.team3925.robot;

import java.util.ArrayList;

import com.team3925.robot.commands.AutoPickup;
import com.team3925.robot.commands.AutoRelease;
import com.team3925.robot.commands.Climb;
import com.team3925.robot.commands.DriveManual.DriveManualInput;
import com.team3925.robot.commands.RaiseGearIntake;
import com.team3925.robot.commands.Shoot;
import com.team3925.robot.commands.StopShooter;
import com.team3925.util.RIOConfigs;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI implements DriveManualInput {

	private final Joystick stick;
	private final Joystick wheel;
	private ArrayList<Joystick> sticks;

	private static OI instance;

	private OI() {
		sticks = new ArrayList<>();
		int totalJoysticks = 0;
		for (int i = 0; i < DriverStation.kJoystickPorts; ++i) {
			// DriverStation.getInstance().getJoystickIsXbox(i);
			String name = DriverStation.getInstance().getJoystickName(i);
			int type = DriverStation.getInstance().getJoystickType(i);
			System.out.println("Joystick " + i + "Type = " + type + "!");
			if (type != -1) {
				sticks.add(new Joystick(i));
				totalJoysticks++;
			}
		}
		if (totalJoysticks > 1) {
			stick = sticks.get(0);
			wheel = sticks.get(1);
			System.out.println("Two joysticks found. Stick is in 0, Wheel is in 1");
		} else if (totalJoysticks > 0) {
			stick = sticks.get(0);
			wheel = null;
			System.out.println("One joystick found. Stick is in 0");
		} else {
			stick = null;
			wheel = null;
			System.out.println("No joysticks found");
		}

		if (stick != null) {
			JoystickButton gearPickupButton = new JoystickButton(stick,
					RIOConfigs.getInstance().getConfigOrAdd("OI_GEAR_BUTTON", 4));
			gearPickupButton.whileHeld(new AutoPickup());
			gearPickupButton.whenReleased(new RaiseGearIntake());
			
			JoystickButton gearPutButton = new JoystickButton(stick,
					RIOConfigs.getInstance().getConfigOrAdd("OI_GEAR_PUT_BUTTON", 5));
			gearPutButton.whileHeld(new AutoRelease());
			gearPutButton.whenReleased(new RaiseGearIntake());

			JoystickButton shootButton = new JoystickButton(stick,
					RIOConfigs.getInstance().getConfigOrAdd("OI_SHOOT_BUTTON", 1));
			shootButton.whileHeld(new Shoot());
			shootButton.whenReleased(new StopShooter());

			JoystickButton climbButton = new JoystickButton(stick,
					RIOConfigs.getInstance().getConfigOrAdd("OI_CLIMB_BUTTON", 3));
			climbButton.whileHeld(new Climb());

			JoystickButton aimButton = new JoystickButton(stick,
					RIOConfigs.getInstance().getConfigOrAdd("OI_AIM_BUTTON", 2));
			// aimButton.whenActive(new AimHorizontal());
		}else {
			System.err.println("The joysticks are null!!");
		}
	}

	public static OI getInstance() {
		return instance == null ? instance = new OI() : instance;
	}

	@Override
	public double getFwd() {
		// Note: the stick has backward = positive, forward = negative!
		if (stick != null)
			return -stick.getRawAxis(1);
		return 0;
	}

	@Override
	public double getLeft() {
		if (wheel != null)
			return wheel.getRawAxis(0);
		if (stick != null)
			return stick.getRawAxis(1);
		return 0;
	}

}
