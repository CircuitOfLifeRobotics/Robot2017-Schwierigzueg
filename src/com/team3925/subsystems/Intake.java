package com.team3925.subsystems;

import static com.team3925.robot.RobotMap.SOLENOID_PORT_A_INTAKE;
import static com.team3925.robot.RobotMap.SOLENOID_PORT_B_INTAKE;
import static com.team3925.robot.RobotMap.TALON_ID_INTAKE;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.util.MiscUtil;
import com.team3925.util.recording.IntakeState;
import com.team3925.util.recording.Recordable;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem implements Recordable<IntakeState> {

	private DoubleSolenoid upDown;

	private CANTalon wheels;

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}

	private Intake() {
		upDown = new DoubleSolenoid(SOLENOID_PORT_A_INTAKE, SOLENOID_PORT_B_INTAKE);
		wheels = new CANTalon(TALON_ID_INTAKE);

		wheels.changeControlMode(TalonControlMode.PercentVbus);
		MiscUtil.configureTalon(wheels, false, false, false, false, true);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setWheels(double percent) {
		wheels.set(percent);
	}

	public void setDown() {
		upDown.set(Value.kForward);
	}

	public void setUp() {
		upDown.set(Value.kReverse);
	}

	public void setPiston(boolean isUp) {
		if (isUp)
			setUp();
		else
			setDown();
	}

	@Override
	public IntakeState record() {
		return new IntakeState(upDown.get().equals(Value.kForward), wheels.get());
	}

	@Override
	public void repeat(IntakeState action) {
		setPiston(action.upDown);
		setWheels(action.speed);
	}

}
