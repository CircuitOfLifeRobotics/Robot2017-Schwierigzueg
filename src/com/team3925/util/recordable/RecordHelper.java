package com.team3925.util.recordable;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RecordHelper extends Command {

	class DumbEntry<K, V> implements Entry<K, V> {

		private K key;
		private V value;

		public DumbEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}

	}

	private RecordCommand[] recordCommands;
	private RepeatCommand[] repeatCommands;
	private Recordable[] recordables;
	private String[] recordableNames;
	private String nameOfRecording;
	private static final String AUTO_NAME_MODE = "NAME_USING_MILLIS";
	private File folder;
	private SendableChooser<File[]> chooser;
	private Entry<String, Boolean> lastRecording;
	private Entry<String, Boolean> lastPlaying;
	private Entry<String, Boolean> lastSave;
	private Entry<String, String> lastName;

	public RecordHelper(File folder, Recordable... recordables) {
		chooser = new SendableChooser<>();
		if (folder.isDirectory())
			this.folder = folder;
		else
			this.folder = new File(System.getProperty("user.home"));
		this.recordables = recordables;
		recordCommands = new RecordCommand[recordables.length];
		repeatCommands = new RepeatCommand[recordables.length];
		recordableNames = new String[recordables.length];
		for (int i = 0; i < recordables.length; ++i) {
			recordableNames[i] = this.recordables[i].getClass().getSimpleName();
			recordCommands[i] = new RecordCommand(recordables[i]);
			repeatCommands[i] = new RepeatCommand(recordables[i], 0.1, 10, true);
		}
		nameOfRecording = AUTO_NAME_MODE;
		chooser.addDefault("None auto", new File[0]);
		File[] files = folder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return false;
				return true;
			}
		});
		HashMap<String, ArrayList<Record>> arrangedRecords = new HashMap<>();
		HashMap<String, ArrayList<File>> arrangedFiles = new HashMap<>();
		for (int i = 0; i < files.length; ++i) {
			Optional<Record> rec = Record.recall(files[i]);
			if (rec.isPresent()) {
				if (!arrangedRecords.containsKey(rec.get().getName()))
					arrangedRecords.put(rec.get().getName(), new ArrayList<>());
				arrangedRecords.get(rec.get().getName()).add(rec.get());
				if (!arrangedFiles.containsKey(rec.get().getName()))
					arrangedFiles.put(rec.get().getName(), new ArrayList<>());
				arrangedFiles.get(rec.get().getName()).add(files[i]);
			}
		}
		arrangedFiles.forEach((_name, _files) -> {
			if (_files.size() == recordables.length)
				chooser.addObject(_name, _files.toArray(new File[0]));
		});
		lastName = new DumbEntry<String, String>("Name", AUTO_NAME_MODE);
		lastPlaying = new DumbEntry<String, Boolean>("Playback", false);
		lastRecording = new DumbEntry<String, Boolean>("Recording", false);
		lastSave = new DumbEntry<String, Boolean>("Save", false);
		SmartDashboard.putString(lastName.getKey(), lastName.getValue());
		SmartDashboard.putBoolean(lastPlaying.getKey(), lastPlaying.getValue());
		SmartDashboard.putBoolean(lastRecording.getKey(), lastRecording.getValue());
		SmartDashboard.putBoolean(lastSave.getKey(), lastSave.getValue());

		setRunWhenDisabled(true);
	}

	public RecordHelper(Recordable... recordables) {
		this(new File(System.getProperty("user.home")), recordables);
	}

	/**
	 * Starts recording fresh. Use {@link #continueRecording()} to start from
	 * previously left off.
	 */
	public void startRecording() {
		for (int i = 0; i < recordCommands.length; ++i) {
			recordCommands[i].reset();
			recordCommands[i].start();
		}
	}

	/**
	 * Starts recording where previously left off. Use {@link #startRecording()}
	 * to start anew.
	 */
	public void continueRecording() {
		for (int i = 0; i < recordCommands.length; ++i) {
			recordCommands[i].start();
		}
	}

	public void stopRecording() {
		for (int i = 0; i < recordCommands.length; ++i) {
			recordCommands[i].cancel();
		}
	}

	public void saveRecordings() {
		ArrayList<File> files = new ArrayList<>();
		for (int i = 0; i < recordCommands.length; ++i) {
			File file = new File(folder, System.currentTimeMillis() + "_" + recordableNames[i] + "_" + ".txt");
			files.add(file);
			recordCommands[i].get().save(file);
		}
		chooser.addObject(Long.toString(System.currentTimeMillis()), files.toArray(new File[0]));
	}

	public void saveRecordings(String name) {
		ArrayList<File> files = new ArrayList<>();
		for (int i = 0; i < recordCommands.length; ++i) {
			File file = new File(folder, System.currentTimeMillis() + "_" + recordableNames[i] + "_" + name + ".txt");
			files.add(file);
			recordCommands[i].get().save(file);
		}
		chooser.addObject(name, files.toArray(new File[0]));
	}

	@Override
	protected void execute() {
		Boolean currRecording = SmartDashboard.getBoolean(lastRecording.getKey(), lastRecording.getValue());
		Boolean currPlaying = SmartDashboard.getBoolean(lastPlaying.getKey(), lastPlaying.getValue());
		Boolean currSave = SmartDashboard.getBoolean(lastSave.getKey(), lastSave.getValue());
		String currName = SmartDashboard.getString(lastName.getKey(), lastName.getValue());

		if (lastRecording.getValue() != currRecording) {
			if (currRecording == Boolean.FALSE)
				stopRecording();
			else if (currRecording == Boolean.TRUE)
				startRecording();
			lastRecording.setValue(currRecording);
		}
		if (lastName.getValue() != currName) {
			nameOfRecording = (String) currName;
			lastName.setValue(currName);
		}
		if (lastSave.getValue() != currSave) {
			if (currSave == Boolean.TRUE) {
				if (nameOfRecording.equals(AUTO_NAME_MODE))
					saveRecordings();
				else if (currSave == Boolean.FALSE)
					saveRecordings(nameOfRecording);
				lastSave.setValue(false);
				lastName.setValue(AUTO_NAME_MODE);
				nameOfRecording = AUTO_NAME_MODE;
				SmartDashboard.putBoolean(lastSave.getKey(), lastSave.getValue());
				SmartDashboard.putString(lastName.getKey(), lastName.getValue());
			}
		}
		if (lastPlaying.getValue() != currPlaying) {
			if (currPlaying == Boolean.TRUE) {
				File[] records = chooser.getSelected();
				if (records.length > 0)
					for (int i = 0; i < records.length; ++i) {
						repeatCommands[i].set(Record.recall(records[i]).get());
						repeatCommands[i].start();
					}
			} else {
				for (int i = 0; i < repeatCommands.length; ++i)
					repeatCommands[i].cancel();
			}
			lastPlaying.setValue(currPlaying);
		}
	}

	public SendableChooser<File[]> getChooser() {
		return chooser;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
