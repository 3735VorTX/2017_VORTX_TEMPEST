package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveForwardToCurrentGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToOffsetGyroHeading;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonForwardDriveSquare extends CommandGroup {

    public AutonForwardDriveSquare(){
    	
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToOffsetGyroHeading(90)); /* Turn */
    	
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToOffsetGyroHeading(90)); /* Turn */
      	
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToOffsetGyroHeading(90)); /* Turn */
    	
    	addSequential(new DriveForwardToCurrentGyroHeading(20)); /* Straight To Pin*/
    	addSequential(new DriveTurnToOffsetGyroHeading(90)); /* Turn */
  
    	
    	
     }
}
