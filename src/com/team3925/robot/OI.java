package com.team3925.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements DriveManualInput {

	private static OI instance;

	private Joystick wheel, stick, xbox;

	private HashMap<Integer, JoystickButton> wheelButtonTriggers;
	private HashMap<Integer, JoystickButton> stickButtonTriggers;
	private HashMap<Integer, JoystickButton> xboxButtonTriggers;

	public static OI getInstance() {
		if (instance == null)
			instance = new OI();
		return instance;
	}

	private OI() {
		wheel = new Joystick(0);
		stick = new Joystick(1);
		xbox = new Joystick(2);

		wheelButtonTriggers = new HashMap<>();
		stickButtonTriggers = new HashMap<>();
		xboxButtonTriggers = new HashMap<>();
	}

	public void whenWheelButtonPressed(int button, Command command) {
		if (!wheelButtonTriggers.containsKey(button))
			wheelButtonTriggers.put(button, new JoystickButton(wheel, button));
		wheelButtonTriggers.get(button).whenActive(command);
	}

	public void whenWheelButtonReleased(int button, Command command) {
		if (!wheelButtonTriggers.containsKey(button))
			wheelButtonTriggers.put(button, new JoystickButton(wheel, button));
		wheelButtonTriggers.get(button).whenInactive(command);
	}

	public void whenStickButtonPressed(int button, Command command) {
		if (!stickButtonTriggers.containsKey(button))
			stickButtonTriggers.put(button, new JoystickButton(stick, button));
		stickButtonTriggers.get(button).whenActive(command);
	}

	public void whenStickButtonReleased(int button, Command command) {
		if (!stickButtonTriggers.containsKey(button))
			stickButtonTriggers.put(button, new JoystickButton(stick, button));
		stickButtonTriggers.get(button).whenInactive(command);
	}

	public void whenXboxButtonPressed(int button, Command command) {
		if (!xboxButtonTriggers.containsKey(button))
			xboxButtonTriggers.put(button, new JoystickButton(xbox, button));
		xboxButtonTriggers.get(button).whenActive(command);
	}

	public void whenXboxButtonReleased(int button, Command command) {
		if (!xboxButtonTriggers.containsKey(button))
			xboxButtonTriggers.put(button, new JoystickButton(xbox, button));
		xboxButtonTriggers.get(button).whenInactive(command);
	}

	@Override
	public double getForward() {
		return stick.getRawAxis(1);
	}

	@Override
	public double getRight() {
		return wheel.getRawAxis(0);
	}

}
