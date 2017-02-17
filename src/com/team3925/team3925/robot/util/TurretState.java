
package com.team3925.team3925.robot.util;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TurretState extends SubsystemState {
	
	private double angle, angularVelocity;
	
	public TurretState() {
		this(0, 0);
	}
	
	public TurretState(double angle) {
		this(angle, 0);
	}
	
	public TurretState(double angle, double angularVelocity) {
		this.angle = angle;
		this.angularVelocity = angularVelocity;
	}

	@Override
	public Subsystem getSubsystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void timeProject(double t) {
		// TODO Auto-generated method stub
		
	}
	
}
