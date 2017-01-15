package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PickUpRoller extends Subsystem {

    private final SpeedController pickUpRoller = RobotMap.moveRollInPickUpRoller;
    private final DigitalInput insideDetector = RobotMap.pickUpHomeLimit;
    private final boolean insideRobot = false;

    private static  final double MOVEROLLIN_ROLLOUTSPEED = -0.1;

    public PickUpRoller(){
    	super();
    	Robot.prefs.putDouble("MoveRollIn_rolloutSpeed", MOVEROLLIN_ROLLOUTSPEED);
    }

    public void updateDashboard() {

    	SmartDashboard.putNumber("PickUpRollers/PickUpRoller", pickUpRoller.get());
    	SmartDashboard.putBoolean("PickUpRollers/InsideDetector", insideDetector.get());
    	SmartDashboard.putBoolean("PickUpRollers/InsideRobot", insideRobot);

    }

    public void initDefaultCommand() {

    }

    public boolean hitDetector(){
    	return insideDetector.get();
    }

    public void setRollerSpeed(double speed){
    	/*
    	if(Robot.moveRollIn.hitDetector()){
    		if(Robot.oi.spinOut.get()){
    			pickUpRoller.set(-speed/2);
    		}else{
    			Robot.moveRollIn.stop();
    		}
    	}else{
    		pickUpRoller.set(speed);
    	}
    	*/
    	/*
    	if(RobotMap.pickUpInsideDetector.get()){
    		insideRobot = true;
    	}
    	else{
    		insideRobot = false;
    	}
    	if(insideRobot)
    	{
    		pickUpRoller.set(.5);
    	}
    	else
    	{
    		pickUpRoller.set(speed);
    	}
    	*/

    	//if(!RobotMap.pickUpHomeLimit.get())

    	pickUpRoller.set(speed);
    	//else
    	//pickUpRoller.set(0);
    }

    public void moveRollerBack(){
    	pickUpRoller.set(-.4);
    }

    public void stop(){
    	pickUpRoller.set(0);
    }
}

