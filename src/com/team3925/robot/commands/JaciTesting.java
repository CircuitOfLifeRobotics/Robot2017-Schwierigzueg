package com.team3925.robot.commands;

import javax.swing.text.AbstractDocument.LeafElement;

import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.RobotMap;
import com.team3925.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class JaciTesting extends Command{
	
	EncoderFollower left, right;
	
	@Override
	protected void initialize() {
		DriveTrain.getInstance().changeControlModes(TalonControlMode.Speed);
		
		Waypoint[] points = new Waypoint[] {
			    new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
			    new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
			    new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
		};		
			
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
		Trajectory trajectory = Pathfinder.generate(points, config);
		
		TankModifier modifier = new TankModifier(trajectory).modify(0.5);
		
		 left = new EncoderFollower(modifier.getLeftTrajectory());
		 right = new EncoderFollower(modifier.getRightTrajectory());
		
//		EncoderFollower 
		
		left.configureEncoder((int) DriveTrain.getInstance().getLeftTicks(), (int) RobotMap.DRIVETRAIN_TICKS_PER_REV, 0.34);
		right.configureEncoder((int) DriveTrain.getInstance().getRightTicks(), (int) RobotMap.DRIVETRAIN_TICKS_PER_REV, 0.34);
	}
	@Override
	protected void execute() {
		DriveTrain.getInstance().setVelocity(500, 500);
		System.out.println("Right Speed " + DriveTrain.getInstance().getRightSpeed());

	//	DriveTrain.getInstance().setRaw(left.calculate((int) DriveTrain.getInstance().getLeftEncoder()),
		//		right.calculate((i
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
