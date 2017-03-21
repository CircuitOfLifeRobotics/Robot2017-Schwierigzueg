package com.team3925.team3925.robot.triggers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class Trigger20SecondsLeftInMatch extends Trigger {

	@Override
	public boolean get() {
		return !DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().getMatchTime()<=20;
	}

}
