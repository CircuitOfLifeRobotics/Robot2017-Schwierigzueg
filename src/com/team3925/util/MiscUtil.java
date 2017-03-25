package com.team3925.util;

import com.ctre.CANTalon;

public class MiscUtil {

	public static void configureTalon(CANTalon talon, boolean fwdLimitSwitchOpen, boolean revLimitSwitchOpen,
			boolean fwdLimitSwitchEnabled, boolean revLimitSwitchEnabled, boolean brakeModeEnabled) {
		talon.ConfigFwdLimitSwitchNormallyOpen(fwdLimitSwitchOpen);
		talon.ConfigRevLimitSwitchNormallyOpen(revLimitSwitchOpen);
		talon.enableLimitSwitch(fwdLimitSwitchEnabled, revLimitSwitchEnabled);
		talon.enableBrakeMode(brakeModeEnabled);
	}

}
