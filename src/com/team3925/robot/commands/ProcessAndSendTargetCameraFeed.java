package com.team3925.robot.commands;

import java.util.ArrayList;
import java.util.Optional;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.team3925.robot.subsystems.CameraSubsystem;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ProcessAndSendTargetCameraFeed extends Command {

	private final CvSink targetCameraSink;
	private CvSource processedFrameSource;
	private MjpegServer server;
	private Mat inputImage;
	private long inputImageFrameTime;
	private double startErrorTime;

	private final Scalar loThresh, hiThresh;
	private Mat hsvImage, binaryImage;
	private ArrayList<MatOfPoint> findContoursOutput, filterContoursOutput;
	private Mat hierarchy;
	private Scalar unfilteredColor;

	private int horizOffSetPixels, vertOffsetPixels;
	private double FOV_WIDTH_PIXELS = 640;
	private double FOV_HEIGHT_PIXELS = 480;
	private double FOV_WIDTH_DEGREES = 30;
	private double differenceThresh = 3;

	private Double horizOffsetAngle;

	// private final Point textLocation;
	// private final int textThickness;
	// private final int fontFace;
	// private final double fontScale;
	// private final Scalar fontColor;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public ProcessAndSendTargetCameraFeed() {
		targetCameraSink = new CvSink("Target Camera Sink");
		targetCameraSink.setSource(CameraSubsystem.getInstance().getTargetCameraSource());
		startErrorTime = -1;

		findContoursOutput = new ArrayList<>();
		filterContoursOutput = new ArrayList<>();
		hsvImage = new Mat();
		binaryImage = new Mat();
		loThresh = new Scalar(40, 120, 89);
		hiThresh = new Scalar(100, 255, 255);
		hierarchy = new Mat();
		unfilteredColor = new Scalar(0, 255, 128);

		inputImage = new Mat();
		// textLocation = new Point(10, 10);
		// textThickness = 5;
		// fontFace = Core.FONT_HERSHEY_PLAIN;
		// fontScale = 4;
		// fontColor = new Scalar(128, 255, 255);

		horizOffsetAngle = null;

		requires(CameraSubsystem.getInstance());
	}

	@Override
	protected void initialize() {
		targetCameraSink.setEnabled(true);
		inputImageFrameTime = targetCameraSink.grabFrame(inputImage);
		if (inputImageFrameTime > 0) {
			System.out.println("inputImageFrameTime>0");
			if (processedFrameSource == null) {
				processedFrameSource = new CvSource("Processed Target Source", PixelFormat.kBGR, inputImage.width(),
						inputImage.height(), 30);
				/* FOV_HEIGHT_PIXELS = */System.out.println(inputImage.width());
				/* FOV_WIDTH_PIXELS = */System.out.println(inputImage.height());
			}
			if (server == null) {
				server = new MjpegServer("Processed Target Stream", 1185);
				server.setSource(processedFrameSource);
			}
			targetCameraSink.setEnabled(true);
		} else {
			cancel();
		}
	}

	@Override
	protected void execute() {
		inputImageFrameTime = targetCameraSink.grabFrame(inputImage);
		if (inputImageFrameTime > 0) {
			// Imgproc.putText(inputImage, "This is a test", textLocation,
			// fontFace, fontScale, fontColor, textThickness);
			// /*FOV_HEIGHT_PIXELS =*/System.out.println( inputImage.width());
			// /*FOV_WIDTH_PIXELS = */System.out.println(inputImage.height());
			Imgproc.cvtColor(inputImage, hsvImage, Imgproc.COLOR_BGR2HSV);
			Core.inRange(hsvImage, loThresh, hiThresh, binaryImage);
			findContoursOutput.clear();
			Imgproc.findContours(binaryImage, findContoursOutput, hierarchy, Imgproc.RETR_LIST,
					Imgproc.CHAIN_APPROX_SIMPLE);

			Rect rect;
			findContoursOutput
					.sort((a, b) -> (int) Math.signum(Imgproc.boundingRect(b).width - Imgproc.boundingRect(a).width));
			if (findContoursOutput.size() > 1) {
				for (int i = 0; i < 2; ++i) {
					rect = Imgproc.boundingRect(findContoursOutput.get(i));
					horizOffSetPixels = (int) (rect.x + rect.width / 2 - FOV_WIDTH_PIXELS / 2);
					vertOffsetPixels = (int) (rect.y + rect.height / 2 - FOV_HEIGHT_PIXELS / 2);
					Imgproc.line(inputImage, new Point(rect.x + rect.width / 2, rect.y + rect.height / 2),
							new Point(FOV_WIDTH_PIXELS / 2, FOV_HEIGHT_PIXELS / 2), unfilteredColor);
				}
				Imgproc.drawContours(inputImage, findContoursOutput, 0, unfilteredColor);
				Imgproc.drawContours(inputImage, findContoursOutput, 1, unfilteredColor);
			}
			if (findContoursOutput.size() > 1) {
				double a = (0.5 - (double) (Imgproc.boundingRect(findContoursOutput.get(0)).x
						+ Imgproc.boundingRect(findContoursOutput.get(0)).width / 2) / FOV_WIDTH_PIXELS)
						* FOV_WIDTH_DEGREES;
				double b = (0.5 - (double) (Imgproc.boundingRect(findContoursOutput.get(1)).x
						+ Imgproc.boundingRect(findContoursOutput.get(1)).width / 2) / FOV_WIDTH_PIXELS)
						* FOV_WIDTH_DEGREES;
				if (Math.abs(a - b) > differenceThresh) {
					horizOffsetAngle = null;
				} else {
					horizOffsetAngle = (a + b) / 2;
				}
			}

			processedFrameSource.putFrame(inputImage);
			if (startErrorTime > 0)
				startErrorTime = -1;
		} else {
			if (startErrorTime < 0)
				startErrorTime = Timer.getFPGATimestamp();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		targetCameraSink.setEnabled(false);
	}

	public Optional<Double> getHorizontalOffsetAngle() {
		return Optional.ofNullable(horizOffsetAngle);
	}

	public double getElapsedErrorTime() {
		if (startErrorTime > 0)
			return Timer.getFPGATimestamp() - startErrorTime;
		else
			return 0;
	}

	public double getHorizOffsetPercent() {
		return horizOffSetPixels / FOV_WIDTH_PIXELS;
	}

	public double getVertOffsetPercent() {
		return (vertOffsetPixels - FOV_HEIGHT_PIXELS / 2) / FOV_HEIGHT_PIXELS;
	}

}
