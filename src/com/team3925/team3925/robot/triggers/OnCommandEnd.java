
package com.team3925.team3925.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Adam Mendenhall
 * Date: Mar 5, 2017
 * 
 * This class triggers a command whenever another command finishes or is cancelled.
 * 
 */
public class OnCommandEnd extends Trigger {
	
	private Command command;
	
	public OnCommandEnd(Command command) {
		this.command = command;
	}

	//TODO: check if logic works
	@Override
	public boolean get() {
		return command.isCanceled() || (command.timeSinceInitialized()>0 && !command.isRunning());
	}
	
}
