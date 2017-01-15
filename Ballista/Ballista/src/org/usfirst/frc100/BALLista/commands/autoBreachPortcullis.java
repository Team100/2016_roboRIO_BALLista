package org.usfirst.frc100.BALLista.commands;

import org.usfirst.frc100.BALLista.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class autoBreachPortcullis extends CommandGroup {
    
    public  autoBreachPortcullis() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(Robot.driveTrain);
    	requires(Robot.pickUp);
    //	addParallel(new MovePickUpWithPID( .558));
    	addParallel(new AutonomousDriveForward(8000, .5));
      // 	addSequential(new MovePickUpWithPID(.658));
    	//addSequential(new AutonomousDriveForward( 400, .5));
    }
}
