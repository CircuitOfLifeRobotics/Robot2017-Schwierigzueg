package com.team3925.subsystems;

//TODO: import AHRS

public class Navx {

	private static Navx instance;

	private AHRS ahrs;
	
	public static Navx getInstance() {
		if (instance == null)
			instance = new Navx();
		return instance;
	}

	private Navx() {
		//TODO: correct constructor
		ahrs = new AHRS();
	}
	
	public double getHeading() {
		return navx.getFusedHeading();
	}
	
}
