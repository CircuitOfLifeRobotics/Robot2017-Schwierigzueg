
package com.team3925.commands;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.team3925.util.recording.Recorder;

import edu.wpi.first.wpilibj.command.Command;

public class SaveRecord extends Command {

	protected Recorder<?> recorder;
	protected String filePath;

	public SaveRecord(Recorder<?> recordable, String filePath) {
		this.recorder = recordable;
		this.filePath = filePath;
	}

	@Override
	protected void initialize() {
		try {
			Recorder.save(recorder, new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("the file was not found");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("the io was excepted");
			System.exit(0);
		}
	}

	protected boolean isFinished() {
		return true;
	}
}
