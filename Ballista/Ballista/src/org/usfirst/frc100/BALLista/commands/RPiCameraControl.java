package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Preferences;

/**
 *
 */
public class RPiCameraControl extends Command {
	NetworkTable rPiCamTable;
	Preferences prefs;
	double m_brightness = 133;

    public RPiCameraControl(double brightness) {
        // Use requires() here to declare subsystem dependencies
     
    	//rPiCamTable = NetworkTable.getTable("RPiCamControl"); // We will send information to RPi in this table
    	rPiCamTable = NetworkTable.getTable("SmartDashboard"); // We will send information to RPi in this table
    	m_brightness = brightness;
    }

    // This set the brightness value of the camera to be read on RPi and then set Lifecam 
    // Brightness value
    protected void initialize() {
    	
    	rPiCamTable.putNumber("camBright", m_brightness);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
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
