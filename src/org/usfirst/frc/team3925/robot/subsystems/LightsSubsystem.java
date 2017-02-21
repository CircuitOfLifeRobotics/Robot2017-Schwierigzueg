
package org.usfirst.frc.team3925.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.usfirst.frc.team3925.robot.RobotMap.*;

public class LightsSubsystem extends Subsystem {
	
	private static LightsSubsystem instance;
	
	private Solenoid redLightsA, redLightsB, blueLightsA, blueLightsB, camRing;
	
	private boolean camRingEnabled;
	
	public static LightsSubsystem getInstance() {
		if (instance==null)
			instance = new LightsSubsystem();
		return instance;
	}
	
	private LightsSubsystem() {
		camRingEnabled = true;
		
		redLightsA = new Solenoid(LED_PORT_RED_A);
		redLightsB = new Solenoid(LED_PORT_RED_B);
		blueLightsA = new Solenoid(LED_PORT_BLUE_A);
		blueLightsB = new Solenoid(LED_PORT_BLUE_B);
		if (LED_PORT_RING<0)
			camRingEnabled = false;
		else
			camRing = new Solenoid(LED_PORT_RING);
	}
	
	@Override
	protected void initDefaultCommand() {
	}
	
	public void setRedLights(boolean on) {
		redLightsA.set(on);
		redLightsB.set(on);
	}
	
	public void setBlueLights(boolean on) {
		blueLightsA.set(on);
		blueLightsB.set(on);
	}
	
	public void setLEDRing(boolean on) {
		if (camRingEnabled)
			camRing.set(on);
	}
	
}
