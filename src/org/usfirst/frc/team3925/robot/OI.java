package org.usfirst.frc.team3925.robot;

import org.usfirst.frc.team3925.robot.commands.GetJoysticks;

import com.team3925.team3925.robot.util.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static OI instance;
	
	private ControlMode mode;
	private Joystick wheel, stick, other, xbox;
	private double deadZone;
	private boolean hasJoysticks;
	
	private int numWheels = 0;
	private int numSticks = 0;
	private int numXboxs = 0;
	private int numOther = 0;
	
	Button setLowGear, setHighGear, triggerClimb;

	public static OI getInstance() {
		if (instance == null)
			instance = new OI();
		return instance;
	}
	
	private OI() {
		hasJoysticks = false;
		deadZone = 0;
		int idx_wheel = 0;
		int idx_stick = 0;
		int idx_xbox = 0;
		int idx_other = 0;
		Joystick[] sticks = new Joystick[5];
		wheel = null;
		stick = null;
		other = null;
		xbox = null;
		for (int i = 0; i < 5; ++i) {
			sticks[i] = new Joystick(i);
			if (GetJoysticks.isWheel(sticks[i])) {
				++numWheels;
				idx_wheel = i;
			} else if (GetJoysticks.isXbox(sticks[i])) {
				++numXboxs;
				idx_xbox = i;
			} else if (GetJoysticks.isStick(sticks[i])) {
				++numSticks;
				idx_stick = i;
			} else {
				++numOther;
				idx_other = i;
			}
		}
		if (numWheels > 1 || numSticks > 1 || numXboxs > 1 || numOther > 1)
			System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
					+ numXboxs + " xboxs, " + numOther + " other.");
		else if (numWheels == 1) {
			wheel = sticks[idx_wheel];
			if (numSticks == 1) {
				stick = sticks[idx_stick];
				if (numXboxs == 1) {
					mode = ControlMode.WHEEL_STICK_XBOX;
					xbox = sticks[idx_xbox];
					hasJoysticks = true;
				} else if (numOther == 1) {
					mode = ControlMode.WHEEL_STICK_CUSTOM;
					other = sticks[idx_other];
					hasJoysticks = true;
				} else
					System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
							+ numXboxs + " xboxs, " + numOther + " other.");
			} else {
				mode = ControlMode.WHEEL;
			}
		} else if (numXboxs == 1) {
			xbox = sticks[idx_xbox];
			if (numSticks == 1) {
				mode = ControlMode.XBOX_STICK;
				stick = sticks[idx_stick];
				hasJoysticks = true;
			} else if (numOther == 1) {
				mode = ControlMode.XBOX_CUSTOM;
				other = sticks[idx_other];
				hasJoysticks = true;
			} else {
				mode = ControlMode.XBOX;
				hasJoysticks = true;
			}
		} else
			System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
					+ numXboxs + " xboxs, " + numOther + " other.");
		switch (mode) {
		case WHEEL_STICK_CUSTOM:
			setLowGear = new JoystickButton(wheel, 5);
			setHighGear = new JoystickButton(wheel, 6);
			triggerClimb = new JoystickButton(wheel, 11);
			break;
		case WHEEL_STICK_XBOX:
			setLowGear = new JoystickButton(wheel, 5);
			setHighGear = new JoystickButton(wheel, 6);
			triggerClimb = new JoystickButton(wheel, 11);
			break;
		case XBOX_CUSTOM:
			setLowGear = new JoystickButton(xbox, 5);
			setHighGear = new JoystickButton(xbox, 6);
			triggerClimb = new JoystickButton(xbox, 9);
			break;
		case XBOX_STICK:
			setLowGear = new JoystickButton(xbox, 5);
			setHighGear = new JoystickButton(xbox, 6);
			triggerClimb = new JoystickButton(xbox, 9);
			break;
		case XBOX:
			setLowGear = new JoystickButton(xbox, 5);
			setHighGear = new JoystickButton(xbox, 6);
			triggerClimb = new JoystickButton(xbox, 9);
			break;
		case WHEEL:
			setLowGear = new JoystickButton(wheel, 5);
			setHighGear = new JoystickButton(wheel, 6);
			triggerClimb = new JoystickButton(wheel, 11);
			break;
		default:
			setLowGear = new Button() {
				@Override
				public boolean get() {
					return false;
				}
			};
			setHighGear = new Button() {
				@Override
				public boolean get() {
					return false;
				}
			};
			triggerClimb = new Button() {
				@Override
				public boolean get() {
					return false;
				}
			};
			break;
		}
	}
	
	public double getForward() {
		switch (mode) {
		case WHEEL_STICK_CUSTOM:
			return checkDeadZone(stick.getRawAxis(1));
		case WHEEL_STICK_XBOX:
			return checkDeadZone(stick.getRawAxis(1));
		case XBOX_CUSTOM:
			return checkDeadZone(xbox.getRawAxis(1));
		case XBOX_STICK:
			return checkDeadZone(xbox.getRawAxis(1));
		case XBOX:
			return checkDeadZone(xbox.getRawAxis(1));
		case WHEEL:
			return checkDeadZone(wheel.getRawAxis(3)-wheel.getRawAxis(4));
		default:
			System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
					+ numXboxs + " xboxs, " + numOther + " other.");
			return 0;
		}
	}
	
	public double getTurn() {
		switch (mode) {
		case WHEEL_STICK_CUSTOM:
			return checkDeadZone(wheel.getRawAxis(0));
		case WHEEL_STICK_XBOX:
			return checkDeadZone(wheel.getRawAxis(0));
		case XBOX_CUSTOM:
			return checkDeadZone(xbox.getRawAxis(4));
		case XBOX_STICK:
			return checkDeadZone(xbox.getRawAxis(4));
		case XBOX:
			return checkDeadZone(xbox.getRawAxis(0));
		case WHEEL:
			return checkDeadZone(wheel.getRawAxis(0));
		default:
			System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
					+ numXboxs + " xboxs, " + numOther + " other.");
			return 0;
		}
	}
	
	// public double getSensitivity() {
	// double val =
	// (double)((int)(getRawAxis(JOYSTICK_CHANNEL_DRIVE_SENSITIVITY)*40+1))/5;
	// SmartDashboard.putNumber("Sensitivity", val);
	// return val;
	// }
	
	public void setDeadZone(double deadZone) {
		this.deadZone = deadZone;
	}
	
	private double checkDeadZone(double val) {
		if (deadZone <= 0)
			return val;
		else {
			if (Math.abs(val) < deadZone)
				return 0;
			else
				return val;
		}
	}
	
	public Button getLowGearTrigger() {
		return setLowGear;
	}
	
	public Button getHighGearTrigger() {
		return setHighGear;
	}
	
	public Button getClimbingTrigger() {
		return triggerClimb;
	}
	
//	static {
//		
//		Controller stick = Controller.getInstance();
//		
//		stick.getShiftingTrigger().toggleWhenPressed(ToggleShifter.getInstance());
//		stick.getClimbingTrigger().toggleWhenPressed(ToggleClimber.getInstance());
//	}
	
}
