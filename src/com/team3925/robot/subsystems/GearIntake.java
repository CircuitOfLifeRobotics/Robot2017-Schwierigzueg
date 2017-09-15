package com.team3925.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.RobotMap;
import com.team3925.robot.subsystems.GearIntake.GearIntakeState;
import com.team3925.util.recordable.Recordable;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class GearIntake extends Subsystem implements Recordable<GearIntakeState> {

	public static class GearIntakeState {
		public boolean isUp;
		public double speed;

		public GearIntakeState(boolean isUp, double speed) {
			this.isUp = isUp;
			this.speed = speed;
		}

		@Override
		public String toString() {
			return isUp + "`" + speed;
		}

		public static GearIntakeState fromString(String s) {
			try {
				boolean up = Boolean.parseBoolean(s.substring(0, s.indexOf('`')));
				double speed = Double.parseDouble(s.substring(s.indexOf('`') + 1));
				return new GearIntakeState(up, speed);
			} catch (NumberFormatException e) {
				return new GearIntakeState(true, 0);
			}
		}
	}

	DoubleSolenoid gearSolenoid;
	CANTalon intakeMotor;
	private static GearIntake instance;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static GearIntake getInstance() {
		if (instance == null)
			instance = new GearIntake();
		return instance;
	}

	private GearIntake() {
		gearSolenoid = new DoubleSolenoid(RobotMap.PORT_INTAKE_SOLENOID_FORWARD, RobotMap.PORT_INTAKE_SOLENOID_REVERSE);
		intakeMotor = new CANTalon(RobotMap.PORT_INTAKE_MOTOR);

		intakeMotor.changeControlMode(TalonControlMode.PercentVbus);
		intakeMotor.enableLimitSwitch(false, false);
	}

	public void setSolenoid(boolean isUp) {
		gearSolenoid.set(isUp ? Value.kForward : Value.kReverse);
	}

	public void setMotor(double speed) {
		intakeMotor.set(speed);
	}

	public void setMotor() {
		intakeMotor.set(RobotMap.INTAKE_MOTOR_SPEED);
	}

	public boolean gearDetected() {
		return (intakeMotor.getOutputCurrent() > RobotMap.INTAKE_MOTOR_GEAR_CURRENT_THRESHOLD);
	}

	@Deprecated
	public double getMotorCurrent() {
		return intakeMotor.getOutputCurrent();
	}

	@Deprecated
	public double getMotorVoltage() {
		return intakeMotor.getOutputVoltage();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
	}

	@Override
	public GearIntakeState record() {
		return new GearIntakeState(!gearSolenoid.get().equals(Value.kReverse), intakeMotor.get());
	}

	@Override
	public void repeat(GearIntakeState snapshot) {
		setSolenoid(snapshot.isUp);
		setMotor(snapshot.speed);
	}
}
