package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveJogMoveWithPowerValueAndSeconds extends Command {
	private static double pwr=0;
	
    public DriveJogMoveWithPowerValueAndSeconds(double power, double seconds) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    	pwr=power;
    	super.setTimeout(seconds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.drive.arcadeDrive(pwr,0,false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
