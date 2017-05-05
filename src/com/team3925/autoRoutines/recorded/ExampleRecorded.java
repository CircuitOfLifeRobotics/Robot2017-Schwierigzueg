package com.team3925.autoRoutines.recorded;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.robot.Robot;
import com.team3925.subsystems.DriveTrain;
import com.team3925.util.recording.Recorder;
import com.team3925.util.recording.RobotState;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ExampleRecorded extends CommandGroup {

	public ExampleRecorded() {
		Recorder<RobotState> recorder = null;
		try {
			recorder = (Recorder<RobotState>) Recorder.recall(new FileInputStream(Robot.recordFilePath+"exampleRecord"+Robot.recordExtension));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("class not found in file exampleRecord.record");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("file exampleRecord.record not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("io exception when deserializing exampleRecord.record");
		}
		recorder.setModePlayback();
		
		DriveTrain.getInstance().enableSpeed();
		addSequential(recorder);
		addSequential(new GyroTurn(90));
		addSequential(new GyroTurn(-90));
		
	}

}
