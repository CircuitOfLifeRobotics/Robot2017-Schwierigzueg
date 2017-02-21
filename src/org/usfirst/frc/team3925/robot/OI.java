package org.usfirst.frc.team3925.robot;

import static org.usfirst.frc.team3925.robot.RobotMap.MIN_CONFIG_WAIT_TIME;

import org.usfirst.frc.team3925.robot.commands.SetShifterHigh;
import org.usfirst.frc.team3925.robot.commands.SetShifterLow;
import org.usfirst.frc.team3925.robot.commands.ToggleClimber;

import com.team3925.team3925.robot.util.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static OI instance;
	
	private ControlMode mode;
	private Joystick wheel, stick, other, xbox;
	private Command findJoysticks;
	private Button setLowGear, setHighGear, triggerClimb, setGearCam, setShooterCam;
	private double deadZone;
	private boolean hasJoysticks;
	private boolean debug = false;
	
	public static OI getInstance() {
		if (instance == null)
			instance = new OI();
		return instance;
	}
	
	public static boolean isWheel(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("thrustmaster");
	}
	
	public static boolean isStick(Joystick stick) {
		return !stick.getIsXbox() && stick.getName().toLowerCase().contains("attack 3");
	}
	
	public static boolean isXbox(Joystick stick) {
		return stick.getIsXbox() || stick.getName().toLowerCase().contains("xbox");
	}
	
	// TODO: make work
	public static boolean isOther(Joystick stick) {
		return false;
	}
	
	private OI() {
		deadZone = 0.05;
		
		mode = ControlMode.NONE;
		
		setGearCam = new Button() {
			@Override
			public boolean get() {
				return false;
			}
		};
		setShooterCam = new Button() {
			@Override
			public boolean get() {
				return false;
			}
		};
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
		
		findJoysticks = new Command() {
			private int numWheels, numSticks, numXboxs, numOther;
			private int idx_wheel, idx_stick, idx_xbox, idx_other;
			private double timeConfiguredCorrectly;
			Joystick[] sticks;
			
			@Override
			protected void initialize() {
				SmartDashboard.putString("Controls Configuration", "configuring...");
				sticks = new Joystick[5];
			}
			
			@Override
			protected void execute() {
				//count how many of each type of joystick there is
				numOther = numSticks = numWheels = numXboxs = 0;
				for (int i = 0; i < 5; ++i) {
					sticks[i] = new Joystick(i);
					if (isWheel(sticks[i])) {
						++numWheels;
						idx_wheel = i;
					} else if (isXbox(sticks[i])) {
						++numXboxs;
						idx_xbox = i;
					} else if (isStick(sticks[i])) {
						++numSticks;
						idx_stick = i;
					} else if (isOther(sticks[i])) {
						++numOther;
						idx_other = i;
					}
				}
				//check for invalid configurations
				if (numWheels > 1 || numSticks > 1 || numXboxs > 1 || numOther > 1 && debug)
					System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
							+ numXboxs + " xboxs, " + numOther + " other.");
				else if (numWheels == 1) {
					wheel = sticks[idx_wheel];
					if (numSticks == 1) {
						stick = sticks[idx_stick];
						if (numXboxs == 1) {
							mode = ControlMode.WHEEL_STICK_XBOX;
							xbox = sticks[idx_xbox];
							if (!hasJoysticks) {
								timeConfiguredCorrectly = Timer.getFPGATimestamp();
								hasJoysticks = true;
							}
						} else if (numOther == 1) {
							mode = ControlMode.WHEEL_STICK_CUSTOM;
							other = sticks[idx_other];
							if (!hasJoysticks) {
								timeConfiguredCorrectly = Timer.getFPGATimestamp();
								hasJoysticks = true;
							}
						} else if (debug)
							System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks
									+ " sticks, " + numXboxs + " xboxs, " + numOther + " other.");
					} else {
						mode = ControlMode.WHEEL;
					}
				} else if (numXboxs == 1) {
					xbox = sticks[idx_xbox];
					if (numSticks == 1) {
						mode = ControlMode.XBOX_STICK;
						stick = sticks[idx_stick];
						if (!hasJoysticks) {
							timeConfiguredCorrectly = Timer.getFPGATimestamp();
							hasJoysticks = true;
						}
					} else if (numOther == 1) {
						mode = ControlMode.XBOX_CUSTOM;
						other = sticks[idx_other];
						if (!hasJoysticks) {
							timeConfiguredCorrectly = Timer.getFPGATimestamp();
							hasJoysticks = true;
						}
					} else {
						mode = ControlMode.XBOX;
						if (!hasJoysticks) {
							timeConfiguredCorrectly = Timer.getFPGATimestamp();
							hasJoysticks = true;
						}
					}
				} else if (debug)
					System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
							+ numXboxs + " xboxs, " + numOther + " other.");
			}
			
			@Override
			protected boolean isFinished() {
				return hasJoysticks && Timer.getFPGATimestamp()-timeConfiguredCorrectly>MIN_CONFIG_WAIT_TIME;
			}
			
			@Override
			protected void end() {
				switch (mode) {
				case WHEEL_STICK_CUSTOM:
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
					setGearCam = new JoystickButton(wheel, 0);
					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel & Stick | Launchpad");
					break;
				case WHEEL_STICK_XBOX:
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
					setGearCam = new JoystickButton(wheel, 0);
					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel & Stick | Xbox");
					break;
				case XBOX_CUSTOM:
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
					setGearCam = new JoystickButton(xbox, 0);
					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | Launchpad");
					break;
				case XBOX_STICK:
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
					setGearCam = new JoystickButton(xbox, 0);
					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | Stick");
					break;
				case XBOX:
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
					setGearCam = new JoystickButton(xbox, 0);
					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | PROGRAMMER PRO MODE");
					break;
				case WHEEL:
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
					setGearCam = new JoystickButton(wheel, 0);
					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel | NASCAR PRO MODE");
					break;
				default:
					start();
					break;
				}
				setLowGear.whenPressed(SetShifterLow.getInstance());
				setHighGear.whenPressed(SetShifterHigh.getInstance());
				triggerClimb.whenPressed(ToggleClimber.getInstance());
			}
			
			@Override
			protected void interrupted() {
			}
		};
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
			return checkDeadZone(wheel.getRawAxis(3) - wheel.getRawAxis(4));
		default:
			findJoysticks.start();
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
			findJoysticks.start();
			return 0;
		}
	}
	
	public double getSensitivity() {
		double val = 1;
		switch (mode) {
		case WHEEL_STICK_CUSTOM:
			val = (-stick.getRawAxis(2) + 1) * 3 + 0.2;
			break;
		case WHEEL_STICK_XBOX:
			val = (-stick.getRawAxis(2) + 1) * 3 + 0.2;
			break;
		case XBOX_CUSTOM:
			val = (xbox.getRawAxis(5)) * 6 + 0.2;
			break;
		case XBOX_STICK:
			val = (xbox.getRawAxis(5)) * 6 + 0.2;
			break;
		case WHEEL:
			val = 1;
			break;
		case XBOX:
			val = (xbox.getRawAxis(5)) * 6 + 0.2;
			break;
		default:
			findJoysticks.start();
			break;
		}
		return val;
	}
	
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
	
}
