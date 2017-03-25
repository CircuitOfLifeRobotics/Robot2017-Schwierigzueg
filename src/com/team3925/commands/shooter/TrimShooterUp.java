package com.team3925.commands.shooter;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.team3925.commands.Vision;
import com.team3925.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrimShooterUp extends Command {

	private double increment;

	public TrimShooterUp(double increment) {
		this.increment = increment;
	}

	public TrimShooterUp() {
		this(25);
	}

	@Override
	protected void initialize() {
		Shooter.getInstance().SHOOTER_TRIM -= increment;
		SmartDashboard.putNumber("Shooter Trim", Shooter.getInstance().SHOOTER_TRIM);
		if (Math.abs(Shooter.getInstance().getShooterVel())>100)
			Shooter.getInstance().setShooterVel(Vision.getInstance().getSpeed());
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
