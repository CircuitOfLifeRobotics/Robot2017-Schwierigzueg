
package com.team3925.commands;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.team3925.util.recording.Recorder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SaveRecordSmartDashboard extends SaveRecord {

	private String sbKey;

	public SaveRecordSmartDashboard(Recorder<?> recorder, String sbKey) {
		super(recorder, SmartDashboard.getString(sbKey, "none"));
		this.sbKey = sbKey;
	}

	@Override
	protected void initialize() {
		filePath = SmartDashboard.getString(sbKey, filePath);
		try {
			Recorder.save(recorder, new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("the file was not found");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("the io was excepted");
			System.exit(0);
		}
	}

	protected boolean isFinished() {
		return true;
	}
}
