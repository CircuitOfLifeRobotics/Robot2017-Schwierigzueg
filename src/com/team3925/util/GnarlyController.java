package com.team3925.util;

import com.ctre.CANTalon;
import com.team3925.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;

public class GnarlyController {
	
	boolean leftSide;
	double gyro_offset;
	
	int encoder_offset, encoder_tick_count;
    double wheel_circumference;

    double kp, ki, kd, kv, ka, kg;

    double last_error, heading;

    int segment;
    Trajectory trajectory;
	
	public GnarlyController(Trajectory traj) {
		this.trajectory = traj;
	}
	
	
	
	

    public void setTrajectory(Trajectory traj) {
        this.trajectory = traj;
        reset();
    }

    /**
     * Configure the PID/VA Variables for the Follower
     * @param kp The proportional term. This is usually quite high (0.8 - 1.0 are common values)
     * @param ki The integral term. Currently unused.
     * @param kd The derivative term. Adjust this if you are unhappy with the tracking of the follower. 0.0 is the default
     * @param kv The velocity ratio. This should be 1 over your maximum velocity @ 100% throttle.
     *           This converts m/s given by the algorithm to a scale of -1..1 to be used by your
     *           motor controllers
     * @param ka The acceleration term. Adjust this if you want to reach higher or lower speeds faster. 0.0 is the default
     * @param kg The gyro term. This makes your robot more aggressively track its heading.
     */
    public void configurePIDVA(double kp, double ki, double kd, double kv, double ka, double kg) {
        this.kp = kp; this.ki = ki; this.kd = kd;
        this.kv = kv; this.ka = ka; this.kg = kg;
    }

    /**
     * Configure the Encoders being used in the follower.
     * @param initial_position      The initial 'offset' of your encoder. This should be set to the encoder value just
     *                              before you start to track
     * @param ticks_per_revolution  How many ticks per revolution the encoder has
     * @param wheel_diameter        The diameter of your wheels (or pulleys for track systems) in meters
     * @param leftSide        		Used to calculate gyro heading in closed loop mode
     */
    public void configureEncoder(int initial_position, int ticks_per_revolution, double wheel_diameter,boolean leftSide) {
    	if (leftSide) {
    		this.leftSide = true;
    	}else {
    		this.leftSide = false;
    	}
        encoder_offset = initial_position;
        encoder_tick_count = ticks_per_revolution;
        wheel_circumference = Math.PI * wheel_diameter;
    }
    
    /**
     * Configure the Gyro being used in the follower.
     * @param initial_position      The initial 'offset' of your gyro. This should be set to the gyro value just
     *                              before you start to track
     */
	public void configureGyro(double initial_position) {
		this.gyro_offset = initial_position;
	}

    /**
     * Reset the follower to start again. Encoders must be reconfigured.
     */
    public void reset() {
        last_error = 0; segment = 0;
    }

    /**
     * Calculate the desired output for the motors, based on the amount of ticks the encoder has gone through.
     * This does not account for heading of the robot. To account for heading, add some extra terms in your control
     * loop for realignment based on gyroscope input and the desired heading given by this object.
     * @param encoder_tick The amount of ticks the encoder has currently measured.
     * @param gyro_heading The angle the gyro currently measures.
     * @return             The desired output for your motor controller
     */
    
    public double dt = 0;
    public double currentTime = 0;
    
    public double calculate(int encoder_tick, double gyro_heading) {
    	
    
    	
        // Number of Revolutions * Wheel Circumference
        double distance_covered = ((double)(encoder_tick - encoder_offset) / encoder_tick_count)
                * wheel_circumference;
        if (segment < trajectory.length()) {
        	
            Trajectory.Segment seg = trajectory.get(segment);
            
            double positionError = seg.position - distance_covered;
            double velocityError = seg.velocity - (leftSide ? DriveTrain.getInstance().getLeftSpeed() : DriveTrain.getInstance().getRightSpeed());
            double headingError = seg.heading - (gyro_heading - gyro_offset);
            
            
            // Loop is Velocity_Set_Point + Gyro_Error + Position_Error + Velocity_Error
            double calculated_value =
                   seg.velocity
                   + (leftSide ? 1.0 : -1.0)*kg*headingError
                   + kp*positionError
                   + kv*velocityError;
            
            calculated_value = calculated_value * 60 * 1.1126;
            System.out.println(" CALCA" + calculated_value);
            System.out.println("TIME: " + seg.dt);

        
            dt = Timer.getFPGATimestamp() - currentTime;
           if (dt >= seg.dt){
        	   System.out.println("DT:" + dt);
            segment++;
        	currentTime = Timer.getFPGATimestamp();
           }
          	System.out.println(calculated_value);
          return calculated_value;
        } else {
        	return 0;
        }
    }

    /**
     * @return the desired heading of the current point in the trajectory
     */
    public double getHeading() {
        return heading;
    }

    /**
     * @return the current segment being operated on
     */
    public Trajectory.Segment getSegment() {
        return trajectory.get(segment);
    }

    /**
     * @return whether we have finished tracking this trajectory or not.
     */
    public boolean isFinished() {
        return segment >= trajectory.length();
    }

	
	
	
}
