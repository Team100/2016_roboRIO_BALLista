package org.usfirst.frc100.BALLista.commands;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class LowerArm extends Command{
		
	public LowerArm() {

		
		requires(Robot.pickUp);
	}
	
	@Override
	protected void initialize() {
		Robot.pickUp.enable();
		//Robot.pickUp.
	}

	public void execute() {

	}
	
	  protected boolean isFinished() {
	      return true;
	    }

	    // Called once after isFinished returns true
	    protected void end() {

	    }
	    // Called when another command which requires one or more of the same
	    // subsystems is scheduled to run
	    protected void interrupted() {
	    }
	
}
