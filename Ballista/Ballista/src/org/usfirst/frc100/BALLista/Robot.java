package org.usfirst.frc100.BALLista;

//import edu.wpi.first.wpilibj.Team100CameraServer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Team100CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.commands.*;
import org.usfirst.frc100.BALLista.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static OI oi;
	public static DriveTrain driveTrain;
	public static PickUp pickUp;
	public static PickUpRoller pickUpRoller;
	public static Shooter shooter;
	public static Preferences prefs;

	public static Relay spike = new Relay(0);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {

		prefs = Preferences.getInstance();
		RobotMap.init();
		RobotMap.driveTrainLeftEncoder.reset();
		RobotMap.driveTrainRightEncoder.reset();
		driveTrain = new DriveTrain();
		pickUp = new PickUp();
		shooter = new Shooter();
		pickUpRoller = new PickUpRoller();
		// int testValue = 5;

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.

		oi = new OI();

		autonomousCommand = new AutonomousCommand();

		spike.set(Relay.Value.kForward);
		Team100CameraServer.getInstance().startAutomaticCapture("cam0");
		RobotMap.driveTrainRightEncoder.reset();
		RobotMap.driveTrainRightEncoder.reset();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */

	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		RobotMap.driveTrainRightEncoder.reset();
		RobotMap.driveTrainLeftEncoder.reset();
		// schedule the autonomous command (example)
		// if (autonomousCommand != null)
		// autonomousCommand.start();

		// new AutonomousDriveForward(44, .5);
		// Robot.driveTrain.drives(.5);
		int modeSelect = oi.selector();
		switch (modeSelect) {
		case 0:
			new autoBreachPortcullis().start();
			break;
		case 1: // rock wall
			// new AutonomousDriveForward(10, .5).start();
			new AutonomousDriveForward(19000, 1).start();
			// new AutonomousDriveForward(800, .558).start();
			break;
		case 2: // moat
			new AutonomousDriveForward(16000, .6).start();
			new AutonomousDriveForward(3000, .99).start();
			break;
		case 3:	
			new DoNothing(3).start();
			break;
		case 4:		// low bar
			new AutoLowBar(19000, 0.8).start();
			break;
		default:
			new DoNothing(0).start();
			break;
		}
		new UpdateDashboard().start();
	}

	/**
	 * This function is called periodically during autonomous
	 */

	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("distance",
				RobotMap.driveTrainLeftEncoder.getDistance());
		// Robot.driveTrain.drives(.5);

	}

	public void teleopInit() {

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		// RobotMap.internalGyro.reset();
		Scheduler.getInstance().removeAll();
		RobotMap.driveTrainLeftEncoder.reset();
		RobotMap.driveTrainRightEncoder.reset();
		new UpdateDashboard().start();
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		oi.updateDPad();
		SmartDashboard.putNumber("gyro", Robot.driveTrain.getAngles());
		SmartDashboard.putNumber("rate of encoder right",
				RobotMap.driveTrainRightEncoder.getDistance());
		SmartDashboard.putNumber("rate of encoder left",
				RobotMap.driveTrainLeftEncoder.getDistance());
		SmartDashboard.putNumber("pot value", RobotMap.pickUpPickUpPot.get());
		SmartDashboard.putNumber("get setpoint drive train",
				Robot.driveTrain.pid.getSetpoint());
	}

	/**
	 * This function is called periodically during test mode
	 */

	public void testPeriodic() {
		LiveWindow.run();
	}

}