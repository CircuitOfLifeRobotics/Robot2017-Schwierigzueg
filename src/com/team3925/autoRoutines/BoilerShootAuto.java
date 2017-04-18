package com.team3925.autoRoutines;

import java.util.LinkedList;

import com.team3925.commands.MPDrive;
import com.team3925.commands.Timeout;
import com.team3925.commands.compound.TurnShoot;
import com.team3925.commands.driveTrain.GyroTurn;
import com.team3925.util.ChangePoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BoilerShootAuto extends CommandGroup {

		double turn;

		public BoilerShootAuto(String side) {
			if (side.equalsIgnoreCase("BLUE")) {
				turn = 1;
			} else {
				turn = -1;
			}

			addSequential(new Timeout(.1));

			LinkedList<ChangePoint> cps = new LinkedList<ChangePoint>();

			cps.add(new ChangePoint(250, -0.65 * turn, (120.87 - 37) / 12));
			cps.add(new ChangePoint(150, 0, (144.87) / 12));

			addSequential(new MPDrive(cps));

			addSequential(new PlaceGear(2));
			addSequential(new MPDrive(new ChangePoint(-200, .1, 3)));
			addSequential(new MPDrive(new ChangePoint(100, 0, 1)));
		//addSequential(new GyroTurn(150));
			addSequential(new Timeout(1));
		addSequential(new TurnShoot(0));

	}

}
