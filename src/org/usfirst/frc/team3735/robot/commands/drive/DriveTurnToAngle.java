package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTurnToAngle extends Command{

	private double setpoint;
	private double timeOnTarget = 0;
	private double finishTime = Constants.Drive.turnFinishTime;

    public DriveTurnToAngle(double angle){
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	Robot.drive.setUpDriveForSpeedControl();
    	setpoint = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.drive.setSetpoint(setpoint);
//    	Robot.drive.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(Robot.drive.onTarget()){
//    		timeOnTarget += .02;
//    	}else{
//    		timeOnTarget = 0;
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return timeOnTarget >= finishTime;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.drive.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	

}
