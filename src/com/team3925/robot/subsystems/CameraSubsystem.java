package com.team3925.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CameraSubsystem extends Subsystem {

	private final UsbCamera targetCamera, otherCamera;

	private static CameraSubsystem instance;

	public static CameraSubsystem getInstance() {
		return instance == null ? instance = new CameraSubsystem() : instance;
	}

	private CameraSubsystem() {
		targetCamera = new UsbCamera("Target Camera", 0);
		otherCamera = null;
		targetCamera.setBrightness(0);
		targetCamera.setFPS(30);
		targetCamera.setResolution(640, 480);
		targetCamera.setExposureManual(0);
		// otherCamera = new UsbCamera("Other Camera", dev);
		// CameraServer.getInstance().startAutomaticCapture(otherCamera);
	}

	@Override
	protected void initDefaultCommand() {

	}

	public VideoSource getTargetCameraSource() {
		return targetCamera;
	}

}
