package com.team3925.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.RobotMap;
import com.team3925.util.recording.FeederState;
import com.team3925.util.recording.Recordable;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Feeder extends Subsystem implements Recordable<FeederState> {

	private static Feeder instance;

	private CANTalon feeder;

	public static Feeder getInstance() {
		return (instance == null) ? instance = new Feeder() : instance;
	}

	private Feeder() {
		feeder = new CANTalon(RobotMap.TALON_ID_FEEDER);
		feeder.configNominalOutputVoltage(+0.0f, -0.0f);
		feeder.configPeakOutputVoltage(+12.0f, -12.0f);
		feeder.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		feeder.changeControlMode(TalonControlMode.PercentVbus);
		feeder.enableBrakeMode(false);
		feeder.setPID(0.001, 0, .01, 0.1665, 0, 0, 0);
		feeder.enableBrakeMode(true);
	}

	public void set(double percent) {
		feeder.set(percent);
	}

	@Override
	protected void initDefaultCommand() {

	}

	@Override
	public FeederState record() {
		return new FeederState(feeder.get());
	}

	@Override
	public void repeat(FeederState action) {
		set(action.speed);
	}

}
