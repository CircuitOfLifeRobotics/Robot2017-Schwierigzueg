package com.team3925.util.recording;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Recorder<T> extends Command implements Serializable {

	private static final long serialVersionUID = 131800189448476133L;

	private LinkedList<Double> recordTimes;
	private LinkedList<T> recordActions;
	private transient final Recordable<T> recordable;
	private transient T currentAction;
	private transient double currentTime;
	private transient boolean hasNextAction;

	/**
	 * If true, execute() records actions. If false, execute() repeats actions.
	 */
	private transient boolean recordMode;
	/**
	 * The index in recordTimes of the last execution. Makes looping to find
	 * appropriate action faster. (assumption: time flows forward)
	 */
	private transient int currentIdx;
	private transient double startTime;

	public Recorder(Recordable<T> recordable) {
		this.recordable = recordable;
		recordTimes = new LinkedList<>();
		recordActions = new LinkedList<>();
		recordMode = true;
	}

	/**
	 * This method sets the mode to recording<br/>
	 * This method only works when it is not running ({@link isRunning()})<br/>
	 * When start() is called next, this command will record the actions of the
	 * given {@link Recordable}
	 */
	public void setModeRecord() {
		if (!isRunning())
			recordMode = true;
	}

	/**
	 * This method sets the mode to playback<br/>
	 * This method only works when it is not running ({@link isRunning()})<br/>
	 * When start() is called next, this command will playback the actions of
	 * the given {@link Recordable}
	 */
	public void setModePlayback() {
		if (!isRunning())
			recordMode = false;
	}

	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
		if (recordMode) {
			recordTimes.clear();
			recordActions.clear();
		} else {
			currentIdx = 0;
			hasNextAction = true;
		}
	}

	@Override
	protected void execute() {
		if (recordMode) {
			currentTime = Timer.getFPGATimestamp();
			currentAction = recordable.record();
			if (recordActions.size() < 1) {
				recordActions.add(currentAction);
				recordTimes.add(currentTime - startTime);
			} else if (!recordActions.getLast().equals(currentAction)) {
				SmartDashboard.putString("Recorded", currentAction.toString());
				recordActions.add(currentAction);
				recordTimes.add(currentTime - startTime);
			}
		} else {
			currentTime = Timer.getFPGATimestamp();

			hasNextAction = (currentIdx < recordTimes.size());
			if (hasNextAction)
				if (recordTimes.get(currentIdx) <= currentTime - startTime) {
					SmartDashboard.putString("Time playback", Double.toString(currentTime-startTime));
					recordable.repeat(recordActions.get(currentIdx++));
				}
		}
	}

	@Override
	protected boolean isFinished() {
		return !recordMode && !hasNextAction;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
	
	public static void save(Recorder<?> recorder, FileOutputStream out) throws IOException {
		ObjectOutputStream objectOut = new ObjectOutputStream(out);
		objectOut.writeObject(recorder);
		objectOut.close();
		out.close();
	}
	
	/**
	 * Returns null if the given FileInputStream does not point to a Recorder<?> object
	 * @param in a FileInputStream that points to a Recorder<?> object
	 * @return the Recorder<?> pointed to by the FileInputStream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Recorder<?> recall(FileInputStream in) throws IOException, ClassNotFoundException {
		ObjectInputStream objectIn = new ObjectInputStream(in);
		Object obj = objectIn.readObject();
		objectIn.close();
		in.close();
		if (obj instanceof Recorder<?>)
			return (Recorder<?>) obj;
		return null;
	}
	
}
