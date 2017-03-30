package org.usfirst.frc.team3735.robot.commands.gearintake;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Constants.GearIntake;
import org.usfirst.frc.team3735.robot.Robot;
import org.usfirst.frc.team3735.robot.commands.Wait;
import org.usfirst.frc.team3735.robot.commands.drive.DriveJogMoveWithPowerValueAndSeconds;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeDropOff extends CommandGroup {

	public GearIntakeDropOff() {
    	addSequential(new ExpDrive(.5,0),.1);
    	addParallel		(new GearIntakeRollersOut(),Constants.GearIntake.dropOffTotalTime);
    	addSequential	(new Wait(Constants.GearIntake.dropOffRollDelay));
    	addSequential	(new GearIntakeLiftDown());
    	addParallel		(new DriveJogMoveWithPowerValueAndSeconds(0.4, 0.75));
    	//addSequential(new ExpDrive(Constants.GearIntake.dropOffDrivePercent,0),Constants.GearIntake.dropOffDriveTime);
    	addSequential	(new Wait(Constants.GearIntake.dropOffJerkDelay));
    	addSequential	(new GearIntakeLiftUp());
    	addSequential	(new Wait(GearIntake.dropOffEndDelay));

    }

	@Override
	public void execute() {
		if (Robot.oi.getMainLeftTrigger() > .05) {
			cancel();
		}
	}
}
