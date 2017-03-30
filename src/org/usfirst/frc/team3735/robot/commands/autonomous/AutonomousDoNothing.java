package org.usfirst.frc.team3735.robot.commands.autonomous;

import org.usfirst.frc.team3735.robot.commands.drive.DriveBrake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousDoNothing extends CommandGroup {

    public AutonomousDoNothing() {
    	addSequential(new DriveBrake());
     }
}
