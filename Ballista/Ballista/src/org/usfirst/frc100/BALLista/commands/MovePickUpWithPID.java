
//
//This file was generated by RobotBuilder. It contains sections of
//code that are automatically generated and assigned by robotbuilder.
//These sections will be updated in the future when you export to
//Java from RobotBuilder. Do not put any code or make any change in
//the blocks indicating autogenerated code or it will be lost on an
//update. Deleting the comments indicating the section will prevent
//it from being updated in the future.


package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

/**
*
*/
public class MovePickUpWithPID extends Command {
	double angles; 
	boolean disablePID = false;
 public MovePickUpWithPID() {

     requires(Robot.pickUp);

 }
 public MovePickUpWithPID(double angle){
	if(angle != .1)
		disablePID = false;
	else
		disablePID = true;
 	angles = angle;
 	requires(Robot.pickUp);
 }

 // Called just before this Command runs the first time
 protected void initialize() {
//	if(Math.abs(Robot.oi.operator.getRawAxis(1)) == 0){
	
	Robot.pickUp.enable();
	
 	Robot.pickUp.setAbsoluteTolerance(.001);
 	Robot.pickUp.setSetpoint(angles);
	//}
	//else{
	//Robot.pickUp.disable();
	//}
 	
//	}
	//else{
	//Robot.pickUp.disable();
	//}
 }

 // Called repeatedly when this Command is scheduled to run
 protected void execute() {

 }

 // Make this return true when this Command no longer needs to run execute()eee
 protected boolean isFinished() {
     if (disablePID == true || Robot.pickUp.onTarget() == true) return true;
     return false;
 }

 // Called once after isFinished returns true
 protected void end() {
	 
	 Robot.pickUp.disable();
 	Robot.pickUp.stop();
 }

 // Called when another command which requires one or more of the same
 // subsystems is scheduled to run
 protected void interrupted() {
 	end();
 }
}
