package org.usfirst.frc100.BALLista.commands;

import org.usfirst.frc100.BALLista.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateDashboard extends Command {

	public UpdateDashboard() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putString("DriveTrain/~TYPE~", "SubSystem");
		SmartDashboard.putString("PickUpRollers/~TYPE~", "SubSystem");
		SmartDashboard.putString("PickUp/~TYPE~", "SubSystem");
		SmartDashboard.putString("Shooter/~TYPE~", "SubSystem");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.updateDashboard();
		Robot.pickUpRoller.updateDashboard();
		Robot.pickUp.updateDashboard();
		Robot.shooter.updateDashboard();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
