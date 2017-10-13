package com.team3925.robot;

import com.team3925.robot.commands.AutoPickup;
import com.team3925.robot.commands.AutoRelease;
import com.team3925.robot.commands.Climb;
import com.team3925.robot.commands.GearOutput;
import com.team3925.robot.commands.DriveManual.DriveManualInput;
import com.team3925.robot.commands.RaiseGearIntake;
import com.team3925.util.RIOConfigs;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI implements DriveManualInput {

	private final Joystick stick;
	private final Joystick wheel;
	private final Joystick xbox;

	private static OI instance;
	private final boolean WHEEL_CONTROL = true;

	private OI() {
		if (WHEEL_CONTROL) {
			stick = new Joystick(0);
			wheel = new Joystick(1);
		} else {
			stick = null;
			wheel = null;
		}
		xbox = new Joystick(2);
//		xbox = null;

		if (xbox != null) {
			JoystickButton gearPickupButton = new JoystickButton(xbox,
					RIOConfigs.getInstance().getConfigOrAdd("OI_GEAR_BUTTON", 2));
			gearPickupButton.whileHeld(new AutoPickup());
			gearPickupButton.whenReleased(new RaiseGearIntake());

			JoystickButton gearPutButton = new JoystickButton(xbox,
					RIOConfigs.getInstance().getConfigOrAdd("OI_GEAR_PUT_BUTTON", 1));
			gearPutButton.whileHeld(new AutoRelease());
			gearPutButton.whenReleased(new RaiseGearIntake());

			JoystickButton climbButton = new JoystickButton(xbox,
					RIOConfigs.getInstance().getConfigOrAdd("OI_CLIMB_BUTTON", 1));
			climbButton.whileHeld(new Climb());
			
			JoystickButton outputButton = new JoystickButton(xbox, 4);
			outputButton.whileHeld(new GearOutput());
		} else {
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
		else if (xbox != null)
			return -xbox.getRawAxis(1);
		else {
			return 0;
		}
	}

	@Override
	public double getLeft() {
		if (wheel != null)
			return wheel.getRawAxis(0);
		else if(xbox != null)
			return xbox.getRawAxis(0);
		else{
			return 0;
		}
	}

	public void setXboxVibrate(boolean isVibrate) {
		if (xbox != null)
			if (isVibrate) {
				xbox.setRumble(RumbleType.kLeftRumble, 1);
				xbox.setRumble(RumbleType.kRightRumble, 1);
			} else {
				xbox.setRumble(RumbleType.kLeftRumble, 0);
				xbox.setRumble(RumbleType.kRightRumble, 0);
			}
	}

}
