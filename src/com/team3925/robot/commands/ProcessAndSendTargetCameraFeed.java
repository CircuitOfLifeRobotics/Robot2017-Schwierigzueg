package com.team3925.robot.commands;

import java.util.ArrayList;

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
	private double FOV_WIDTH_PIXELS;
	private double FOV_HEIGHT_PIXELS;

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
		loThresh = new Scalar(63, 79, 89);
		hiThresh = new Scalar(93, 255, 255);
		hierarchy = new Mat();
		unfilteredColor = new Scalar(0, 255, 128);

		inputImage = new Mat();
		// textLocation = new Point(10, 10);
		// textThickness = 5;
		// fontFace = Core.FONT_HERSHEY_PLAIN;
		// fontScale = 4;
		// fontColor = new Scalar(128, 255, 255);

		requires(CameraSubsystem.getInstance());
	}

	@Override
	protected void initialize() {
		System.out.println("started initializing camera stuff");
		inputImageFrameTime = targetCameraSink.grabFrame(inputImage);
		if (inputImageFrameTime > 0) {
			if (processedFrameSource == null) {
				processedFrameSource = new CvSource("Processed Target Source", PixelFormat.kBGR, inputImage.width(),
						inputImage.height(), 30);
				FOV_HEIGHT_PIXELS = inputImage.width();
				FOV_WIDTH_PIXELS = inputImage.height();
			}
			if (server == null) {
				server = new MjpegServer("Processed Target Stream", 1185);
				server.setSource(processedFrameSource);
			}
			targetCameraSink.setEnabled(true);
		} else
			cancel();
		System.out.println("finished initializing camera stuff");
	}

	@Override
	protected void execute() {
		inputImageFrameTime = targetCameraSink.grabFrame(inputImage);
		if (inputImageFrameTime > 0) {
			// Imgproc.putText(inputImage, "This is a test", textLocation,
			// fontFace, fontScale, fontColor, textThickness);
			Imgproc.cvtColor(inputImage, hsvImage, Imgproc.COLOR_BGR2HSV);
			Core.inRange(hsvImage, loThresh, hiThresh, binaryImage);
			findContoursOutput.clear();
			Imgproc.findContours(binaryImage, findContoursOutput, hierarchy, Imgproc.RETR_LIST,
					Imgproc.CHAIN_APPROX_SIMPLE);
			Imgproc.drawContours(inputImage, findContoursOutput, -1, unfilteredColor);

			Rect rect;
			if (findContoursOutput.size() > 0) {
				for (int i = 0; i < findContoursOutput.size(); ++i) {
					rect = Imgproc.boundingRect(findContoursOutput.get(i));
					horizOffSetPixels = (int) (rect.x + rect.width / 2 - FOV_WIDTH_PIXELS / 2);
					vertOffsetPixels = (int) (rect.y + rect.height / 2 - FOV_HEIGHT_PIXELS / 2);
					Imgproc.line(inputImage, new Point(rect.x + rect.width / 2, rect.y + rect.height / 2),
							new Point(FOV_WIDTH_PIXELS / 2, FOV_HEIGHT_PIXELS / 2), unfilteredColor);
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