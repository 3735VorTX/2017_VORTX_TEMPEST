package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveForwardToCurrentGyroHeading;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePosition extends CommandGroup {

    public AutonForwardDrivePosition(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	//112 + 20
    	addSequential(new DriveForwardToCurrentGyroHeading(132)); /* Straight To Pin*/
    	//addSequential(new DriveMoveDistance(10));
     }
}
