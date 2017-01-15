package org.usfirst.frc100.BALLista.commands;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoLowBar extends CommandGroup{
	
	public double speed;
	public double distance;
	
	
	public AutoLowBar(double distance, double speed){
		this.distance = distance;
		this.speed = speed;
		
		requires(Robot.pickUp);
		requires(Robot.driveTrain);
		Robot.pickUp.enable();
		Robot.pickUp.setSetpoint(.526);
		//addSequential(new MovePickUpWithPID(.560));
		addParallel(new AutonomousDriveForward((int)distance, speed));
	}
	
	
	//@Override
	//public synchronized void start() {
		// TODO Auto-generated method stub
		//super.start();
	//}
}
