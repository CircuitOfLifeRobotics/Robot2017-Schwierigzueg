package org.usfirst.frc.team3925.robot;

import static com.team3925.team3925.robot.util.Constants.MIN_CONFIG_WAIT_TIME;

import com.team3925.team3925.robot.commands_subsystems.RunShooter;
import com.team3925.team3925.robot.commands_subsystems.ShiftLow;
import com.team3925.team3925.robot.commands_subsystems.ToggleClimber;
import com.team3925.team3925.robot.util.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * POPULATED: TRUE
 * WHEN: 3/7/17
 * TESTED: FALSE
 * WHEN: 
 */

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private static OI instance;
	
	private ControlMode mode;
	public Joystick wheel, stick, xbox;
	private Command findJoysticks;
	private double deadZone;
	private boolean hasJoysticks;
	private boolean debug;
	
	public static OI getInstance() {
		return (instance == null) ? instance = new OI() : instance;
	}
	
	private OI() {
		deadZone = 0.1;
		mode = ControlMode.NONE;
		debug = false;
		
		findJoysticks = new Command() {
			private int numWheels, numSticks, numXboxs;
			private int idx_wheel, idx_stick, idx_xbox;
			private double timeConfiguredCorrectly;
			Joystick[] sticks;
			private Button setLowGear, setHighGear, triggerClimb, shoot;
			
			@Override
			protected void initialize() {
				SmartDashboard.putString("Controls Configuration", "configuring...");
				sticks = new Joystick[5];
			}
			
			@Override
			protected void execute() {
				// count how many of each type of joystick there is
				numSticks = numWheels = numXboxs = 0;
				for (int i = 0; i < 5; ++i) {
					sticks[i] = new Joystick(i);
					if (ControlMode.isWheel(sticks[i])) {
						++numWheels;
						idx_wheel = i;
					} else if (ControlMode.isXbox(sticks[i])) {
						++numXboxs;
						idx_xbox = i;
					} else if (ControlMode.isStick(sticks[i])) {
						++numSticks;
						idx_stick = i;
					}
				}
				// check for invalid configurations
				if ((numWheels > 1 || numSticks > 1 || numXboxs > 1) && debug)
					System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
							+ numXboxs + " xboxs.");
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
						} else if (debug)
							System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks
									+ " sticks, " + numXboxs + " xboxs.");
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
					} else {
						mode = ControlMode.XBOX;
						if (!hasJoysticks) {
							timeConfiguredCorrectly = Timer.getFPGATimestamp();
							hasJoysticks = true;
						}
					}
				} else if (debug)
					System.out.println("Unknown controller setup; " + numWheels + " wheels, " + numSticks + " sticks, "
							+ numXboxs + " xboxs.");
			}
			
			@Override
			protected boolean isFinished() {
				return hasJoysticks && Timer.getFPGATimestamp() - timeConfiguredCorrectly > MIN_CONFIG_WAIT_TIME;
			}
			
			@Override
			protected void end() {
				switch (mode) {
				case WHEEL_STICK_CUSTOM:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
//					setGearCam = new JoystickButton(wheel, 0);
//					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel & Stick | Launchpad");
					break;
				case WHEEL_STICK_XBOX:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
//					setGearCam = new JoystickButton(wheel, 0);
//					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel & Stick | Xbox");
					break;
				case XBOX_CUSTOM:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
//					setGearCam = new JoystickButton(xbox, 0);
//					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | Launchpad");
					break;
				case XBOX_STICK:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
//					setGearCam = new JoystickButton(xbox, 0);
//					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | Stick");
					break;
				case XBOX:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(xbox, 5);
					setHighGear = new JoystickButton(xbox, 6);
					triggerClimb = new JoystickButton(xbox, 9);
//					setGearCam = new JoystickButton(xbox, 0);
//					setShooterCam = new JoystickButton(xbox, 1);
					SmartDashboard.putString("Controls Configuration", "Xbox | PROGRAMMER PRO MODE");
					break;
				case WHEEL:
					shoot = new JoystickButton(xbox, 1);
					setLowGear = new JoystickButton(wheel, 5);
					setHighGear = new JoystickButton(wheel, 6);
					triggerClimb = new JoystickButton(wheel, 11);
//					setGearCam = new JoystickButton(wheel, 0);
//					setShooterCam = new JoystickButton(wheel, 1);
					SmartDashboard.putString("Controls Configuration", "Wheel | NASCAR PRO MODE");
					break;
				default:
					start();
					break;
				}
				setLowGear.whileHeld(new ShiftLow(false));
				triggerClimb.whenPressed(new ToggleClimber());
				shoot.whileHeld(new RunShooter());
			}
			
			@Override
			protected void interrupted() {
			}
		};
	}
	
}
