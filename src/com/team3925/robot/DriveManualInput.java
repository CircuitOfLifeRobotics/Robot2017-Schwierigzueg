package com.team3925.robot;

public interface DriveManualInput {
	
	/**
	 * @return percent of total forward speed robot should be traveling. Negative means backwards
	 */
	public abstract double getForward();
	
	/**
	 * @return percent of maximum turn speed robot should be traveling. Negative means left
	 */
	public abstract double getLeft();
	
}
