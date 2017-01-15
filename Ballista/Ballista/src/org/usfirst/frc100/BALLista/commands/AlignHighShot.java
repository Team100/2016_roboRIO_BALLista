package org.usfirst.frc100.BALLista.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignHighShot extends CommandGroup {
    
    public  AlignHighShot() {
    	addSequential(new RPiCameraControl(30));  // Turn Camera Brightness Down
    	addSequential(new AutoAlignHighGoal());   // Orient Robot to Aim at Target
    	addSequential(new RPiCameraControl(128)); // Turn Camera Brightness Up
        
    }
}
