package com.team3925.autoRoutines;

import java.util.LinkedList;

import com.team3925.commands.MPDrive;
import com.team3925.commands.Timeout;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.robot.Constants;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAuto extends CommandGroup {
	

    public TestAuto() {

addSequential(new GyroTurn(60));

    	
    }
}
