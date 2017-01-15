package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
 * This is actually arcade drive, the name was never changed.
 */

public class TankDrive extends Command {

	private static double OutputOldY;

	public TankDrive() {

		requires(Robot.driveTrain);

	}
	public TankDrive(boolean direction) {
		Robot.driveTrain.setDriveDirection(direction);
		requires(Robot.driveTrain);
	}

	protected void initialize() {
		OutputOldY = 0;
	}

	public double GetPositionFiltered(double RawValueReadFromHw) {
		// double tempFilterNumber = 0.01;
		if (!Robot.prefs.containsKey("filterNumber")) {
			Robot.prefs.putDouble("filterNumber", 0.01);
		}
		double filteringNumber = Robot.prefs.getDouble("filterNumber", 0.01);
		double FilteredPosition = (filteringNumber * RawValueReadFromHw) + ((1.0 - filteringNumber) * OutputOldY);
		OutputOldY = FilteredPosition;
		return FilteredPosition;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		double localX = Robot.oi.getDriverController1().getX();
		double localY = Robot.oi.getDriverController2().getY();
		double filteredLocalY = GetPositionFiltered(localY);


		if (Robot.driveTrain.getDriveDirection()) {
			Robot.driveTrain.takeJoystickInputs(localX, -filteredLocalY);
		} else {
			Robot.driveTrain.takeJoystickInputsReverse(-localX, filteredLocalY);
		}


		/*
		if(Robot.driveTrain.getDriveDirection()){
			Robot.driveTrain.takeJoystickInputs(localX,-localY);
		}else{
			Robot.driveTrain.takeJoystickInputsReverse(localX,localY);
		}
		*/

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
