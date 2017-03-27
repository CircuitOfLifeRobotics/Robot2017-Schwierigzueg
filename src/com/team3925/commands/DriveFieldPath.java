
package com.team3925.commands;

import com.team3925.subsystems.DriveTrain;
import com.team3925.subsystems.Navx;
import com.team3925.util.FieldPosition;
import com.team3925.util.Path;

import edu.wpi.first.wpilibj.command.Command;

public class DriveFieldPath extends Command {
	
	private Path<FieldPosition> path;
	
	private double lastLeftEncPos, lastRightEncPos, lastRotation;
	private double predictedX, predictedY, predictedRotation;
	private double deltaLeft, deltaRight, predictedDeltaX, predictedDeltaY, predictedDeltaRotation;
	private double lMINUSr, lPLUSr, lMINUSr_SQUARED, lPLUSrOVERlMINUSr;
	private static final double BOT_RADIUS = 0.5;
	private static final double BOT_RADIUS_CUBEDx4 = BOT_RADIUS*BOT_RADIUS*BOT_RADIUS*4;
	private static final double BOT_RADIUS_SQUAREDx2 = BOT_RADIUS*BOT_RADIUS*2;
	private static final double BOT_RADIUS_SQUAREDx4 = BOT_RADIUS*BOT_RADIUS*4;
	
	public DriveFieldPath(Path<FieldPosition> path) {
		this.path = path;
		requires(DriveTrain.getInstance());
	}
	
	@Override
	protected void initialize() {
		lastLeftEncPos = DriveTrain.getInstance().getLeftEncRaw();
		lastRightEncPos = DriveTrain.getInstance().getRightEncRaw();
		lastRotation = Navx.getInstance().getHeading();
		predictedX = predictedY = predictedRotation = 0;
	}
	
	@Override
	protected void execute() {
		deltaLeft = DriveTrain.getInstance().getLeftEncRaw() - lastLeftEncPos;
		deltaRight = DriveTrain.getInstance().getRightEncRaw() - lastRightEncPos;
		lastLeftEncPos = DriveTrain.getInstance().getLeftEncRaw();
		lastRightEncPos = DriveTrain.getInstance().getRightEncRaw();
		lastRotation = Navx.getInstance().getHeading();//predictedRotation;
		lMINUSr = deltaLeft - deltaRight;
		lPLUSr = deltaLeft + deltaRight;
		lMINUSr_SQUARED = lMINUSr*lMINUSr;
		if (lMINUSr==0) {
			predictedDeltaX = 0;
			predictedDeltaY = deltaLeft;
			predictedDeltaRotation = 0;
		}else {
			lPLUSrOVERlMINUSr = lPLUSr/lMINUSr;
			predictedDeltaX = (BOT_RADIUS_CUBEDx4*lPLUSrOVERlMINUSr + BOT_RADIUS*(deltaLeft*deltaLeft-deltaRight*deltaRight)-BOT_RADIUS_SQUAREDx2*Math.signum(lMINUSr)*lPLUSr*Math.sqrt(1+BOT_RADIUS_SQUAREDx4/(lMINUSr_SQUARED)))/(lMINUSr_SQUARED+BOT_RADIUS_SQUAREDx4);
			predictedDeltaY = Math.signum(lPLUSr)*Math.sqrt(BOT_RADIUS*BOT_RADIUS*lPLUSrOVERlMINUSr*lPLUSrOVERlMINUSr-(predictedDeltaX-BOT_RADIUS*lPLUSrOVERlMINUSr)*(predictedDeltaX-BOT_RADIUS*lPLUSrOVERlMINUSr));
			predictedDeltaRotation = Math.atan(predictedDeltaY/(predictedDeltaX-BOT_RADIUS*lPLUSrOVERlMINUSr));
		}
		predictedX += Math.cos(lastRotation)*predictedDeltaX-Math.sin(lastRotation)*predictedDeltaY;
		predictedY += Math.cos(lastRotation)*predictedDeltaY+Math.sin(lastRotation)*predictedDeltaX;
		predictedRotation += predictedDeltaRotation;
	}
	
	@Override
	protected boolean isFinished() {
		return path.isFinished();
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
	
}
