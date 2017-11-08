package com.team3925.robot.commands;

import com.ctre.CANTalon.TalonControlMode;
import com.team3925.robot.subsystems.DriveTrain;
import com.team3925.robot.subsystems.Gyro;
import com.team3925.util.GnarlyController;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class TestPath extends Command {

	TankModifier modifier;
	Trajectory trajectory;
	
	
	// TUNE THESE VALUES
	final double kP = 0;
	final double kV = 00;
	final double kG = 0;
	
	GnarlyController left;
	GnarlyController right;
	
	
    public TestPath() {
    	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 5, 2.5, 100);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-5, 0,0),
                new Waypoint(0, 0, 0)
        };
        trajectory = Pathfinder.generate(points, config);
        modifier = new TankModifier(trajectory).modify(2.2);
        requires(Gyro.getInstance());
        requires(DriveTrain.getInstance());
        
    }
   

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	left = new GnarlyController(modifier.getLeftTrajectory());
    	right = new GnarlyController(modifier.getRightTrajectory());
    	
    	left.configureEncoder((int) DriveTrain.getInstance().getLeftTicks(), 1024, 0.3541667, true);
    	right.configureEncoder((int) DriveTrain.getInstance().getRightTicks(), 1024, 0.3541667, false);
    	
    	left.configureGyro(Gyro.getInstance().getFusedHeading());
    	right.configureGyro(Gyro.getInstance().getFusedHeading());
    	
    	left.configurePIDVA(kP, 0, 0, kV, 0, kG);
    	right.configurePIDVA(kP, 0, 0, kV, 0, kG);
    	
    	DriveTrain.getInstance().changeControlModes(TalonControlMode.Speed);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
//    	System.out.println("Left: " + modifier.getLeftTrajectory() +
//    			"\tRight: " + modifier.getRightTrajectory());
    	DriveTrain.getInstance().setVelocity(left.calculate((int) DriveTrain.getInstance().getLeftTicks(), Gyro.getInstance().getFusedHeading()), right.calculate((int) DriveTrain.getInstance().getRightTicks(), Gyro.getInstance().getFusedHeading()));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(left.isFinished() && right.isFinished()) {
    		return true;
    	}else {
            return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriveTrain.getInstance().changeControlModes(TalonControlMode.PercentVbus);
    	DriveTrain.getInstance().setRaw(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
