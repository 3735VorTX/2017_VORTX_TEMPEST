package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveForwardToCurrentGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToOffsetGyroHeading;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDrivePositionLeftWithGearDrop extends CommandGroup {

    public AutonForwardDrivePositionLeftWithGearDrop(){
    	/* Let Move FWD Only */
    	/* All the Timing Needs Adjustment     |    */ 
    	/*                                    This */
    	/*                                     |   */
    	addSequential(new DriveForwardToCurrentGyroHeading(72.8),3);
    	addSequential(new DriveTurnToOffsetGyroHeading(60));
    	addSequential(new DriveForwardToCurrentGyroHeading(60.7),3);
    	addSequential(new GearIntakeDropOff(),3);
     }
}
