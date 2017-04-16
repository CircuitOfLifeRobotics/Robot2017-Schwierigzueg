package com.team3925.commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TimerTask;
import java.util.TreeMap;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.command.Command;

public class Vision extends Command {

	private static Vision instance;

	private UsbCamera cam;
	private CvSink camSink;
	private CvSource source;
	private MjpegServer server;
	private Mat frame;
	private Mat processed;
	public Rect visionUpper;
	public Rect visionLower;

	private Mat hsvThresholdOutput = new Mat();
	private Mat hierarchy;
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();
	private TimerTask grabFrame;
	java.util.Timer timer;

	private final double[] hsvThresholdHue = { 74.5, 89.5 };
	private final double[] hsvThresholdSaturation = { 160, 255 };
	private final double[] hsvThresholdValue = { 160, 255 };
	private final double filterContoursMinArea = 10;
	private final double filterContoursMinPerimeter = 0.0;
	private final double filterContoursMinWidth = 20;
	private final double filterContoursMaxWidth = 1000.0;
	private final double filterContoursMinHeight = 0.0;
	private final double filterContoursMaxHeight = 1000.0;
	private final double[] filterContoursSolidity = { 0, 100 };
	private final double filterContoursMaxVertices = 1000000.0;
	private final double filterContoursMinVertices = 0.0;
	private final double filterContoursMinRatio = 0.0;
	private final double filterContoursMaxRatio = 1000.0;
	private final boolean findContoursExternalOnly = false;
	private final MatOfInt hull = new MatOfInt();
	private final int thickness = 3;
	private final Scalar unfilteredColor = new Scalar(0, 0, 255);
	private final Scalar color = new Scalar(0, 127, 255);

	private final double TURN_OFFSET = 0;
	private final double TARGET_WIDTH_FEET = 15d / 12d;
	private final double TARGET_HEIGHT_OFFSET = 10d / 12d;
	private final double FOV_WIDTH_PIXELS = 320;
	private final double FOV_HEIGHT_PIXELS = 240;
	private final double FOV_HEIGHT_DEGREES = 33.583;
	private final double FOV_WIDTH_DEGREES = 59.703;
	private final double TAN_OF_FOV = Math.tan(Math.toRadians(FOV_HEIGHT_DEGREES));
	private final double TARGET_HEIGHT_OFF_GROUND = 6.667;

	public final TreeMap<Double, Double> visionData;

	private boolean frameGotten;

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static Vision getInstance() {
		return instance == null ? instance = new Vision() : instance;
	}

	private Vision() {
		cam = new UsbCamera("Turret Cam", 0);
		camSink = new CvSink("Turret Cam Sink");
		source = new CvSource("Turret Cam Processed Source", new VideoMode(VideoMode.PixelFormat.kBGR, 320, 240, 5));
		server = new MjpegServer("Turret Cam Processed Server", 1185);
		camSink.setSource(cam);
		server.setSource(source);
		frame = new Mat();
		processed = new Mat();

		grabFrame = new TimerTask() {
			@Override
			public void run() {
				camSink.grabFrame(frame);
				frameGotten = true;
			}
		};

		frameGotten = false;

		visionData = new TreeMap<Double, Double>(new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return (o1 > o2) ? 1 : ((o1 == o2) ? 0 : -1);
			}
		});
		// Distance (FT) - Speed (RPM)
		visionData.put(6.9, -2900.0);
		visionData.put(7.6, -3100.0);
		visionData.put(8.7, -3250.0);

		timer = new java.util.Timer();
		timer.scheduleAtFixedRate(grabFrame, 0, 100);
	}

	@Override
	protected void initialize() {
		cam.setFPS(5);
		cam.setExposureManual(10);
		cam.setWhiteBalanceManual(0);
		cam.setBrightness(10);
		cam.setResolution(320, 240);
		findContoursOutput = new ArrayList<>();
		filterContoursOutput = new ArrayList<>();
		grabFrame.run();
	}

	@Override
	protected void execute() {
		if (frameGotten) {
			frameGotten = false;
			Imgproc.cvtColor(frame, processed, Imgproc.COLOR_BGR2HSV);

			Core.inRange(processed, new Scalar(hsvThresholdHue[0], hsvThresholdSaturation[0], hsvThresholdValue[0]),
					new Scalar(hsvThresholdHue[1], hsvThresholdSaturation[1], hsvThresholdValue[1]),
					hsvThresholdOutput);

			findContoursOutput.clear();
			int mode;
			if (findContoursExternalOnly) {
				mode = Imgproc.RETR_EXTERNAL;
			} else {
				mode = Imgproc.RETR_LIST;
			}
			int method = Imgproc.CHAIN_APPROX_SIMPLE;
			hierarchy = new Mat();
			Imgproc.findContours(hsvThresholdOutput, findContoursOutput, hierarchy, mode, method);

			filterContoursOutput.clear();
			for (int i = 0; i < findContoursOutput.size(); i++) {
				final MatOfPoint contour = findContoursOutput.get(i);
				final Rect bb = Imgproc.boundingRect(contour);
				// if (bb.width < filterContoursMinWidth || bb.width >
				// filterContoursMaxWidth)
				// continue;
				if (bb.height < filterContoursMinHeight || bb.height > filterContoursMaxHeight)
					continue;
				final double area = Imgproc.contourArea(contour);
				if (area < filterContoursMinArea)
					continue;
				if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < filterContoursMinPerimeter)
					continue;
				Imgproc.convexHull(contour, hull);
				MatOfPoint mopHull = new MatOfPoint();
				mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
				for (int j = 0; j < hull.size().height; j++) {
					int index = (int) hull.get(j, 0)[0];
					double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1] };
					mopHull.put(j, 0, point);
				}
				final double solid = 100 * area / Imgproc.contourArea(mopHull);
				if (solid < filterContoursSolidity[0] || solid > filterContoursSolidity[1])
					continue;
				if (contour.rows() < filterContoursMinVertices || contour.rows() > filterContoursMaxVertices)
					continue;
				final double ratio = bb.width / (double) bb.height;
				if (ratio < filterContoursMinRatio || ratio > filterContoursMaxRatio)
					continue;
				filterContoursOutput.add(contour);
			}

			if (filterContoursOutput.size() >= 2) {
				Rect rect0 = Imgproc.boundingRect(filterContoursOutput.get(0));
				Rect rect1 = Imgproc.boundingRect(filterContoursOutput.get(1));
				if (rect0.height > rect1.height) {
					visionUpper = rect0;
					visionLower = rect1;
				} else {
					visionLower = rect0;
					visionUpper = rect1;
				}
				Imgproc.rectangle(frame, rect0.tl(), rect0.br(), color);
				Imgproc.rectangle(frame, rect1.tl(), rect1.br(), color);
			} else if (findContoursOutput.size() >= 2) {
				Imgproc.drawContours(frame, findContoursOutput, 0, unfilteredColor, thickness);
				Imgproc.drawContours(frame, findContoursOutput, 1, unfilteredColor, thickness);
			}

		} else {

		}
		source.putFrame(frame);
	}

	public double getDistance() {
		if (visionLower != null && visionUpper != null) {
			double val = TARGET_WIDTH_FEET / 2 / Math.tan(Math
					.toRadians(0.5 * (visionLower.width + visionUpper.width) / FOV_WIDTH_PIXELS * FOV_HEIGHT_DEGREES));
			return Math.sqrt(val * val - TARGET_HEIGHT_OFF_GROUND * TARGET_HEIGHT_OFF_GROUND);
		} else {
			return 0;
		}
	}

	public double getTurnAngle() {
		if (visionLower != null && visionUpper != null) {
			double val = ((visionUpper.x + visionUpper.width / 2 - FOV_WIDTH_PIXELS / 2) / FOV_WIDTH_PIXELS
					* FOV_WIDTH_DEGREES) + TURN_OFFSET;
			return val;
		} else {
			return 0;
		}
	}

	public double getSpeed() {
		if (!visionData.equals(null)) {
			if (visionData.ceilingEntry(getDistance()) == null) {
				return visionData.floorEntry(getDistance()).getValue();
			} else if (visionData.floorEntry(getDistance()) == null) {
				return visionData.ceilingEntry(getDistance()).getValue();
			} else {
				double slope = (visionData.ceilingEntry(getDistance()).getValue()
						- visionData.floorEntry(getDistance()).getValue())
						/ (visionData.ceilingEntry(getDistance()).getKey()
								- visionData.floorEntry(getDistance()).getKey());
				return (slope * (getDistance() - visionData.ceilingEntry(getDistance()).getKey())
						+ visionData.ceilingEntry(getDistance()).getValue());
			}
		} else
			return 0;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
