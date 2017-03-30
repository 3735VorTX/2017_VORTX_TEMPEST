package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class DriveTurnToVisionPinTarget extends Command  implements PIDOutput{

	/**************** MAKE ADJUSTMENTS TO THESE CONSTANTS AND ENABLE DISABLES */
	public static final boolean ISCONSOLEDEBUG_ENABLED = true;
	public static final boolean ISSMARTDASHDEBUG_ENABLED = true;
	
	final static double kP = 0.0150;
	final static double kI  = 0.0; //0.0005;
	final static double kD = 0.005;
	final static double kF  = 0.005;
	static final double kToleranceDegrees = 1.0;
	
	

	
	PIDController visionTurnController;
	private double setpointangle;
    double pidOutputAngle;           // Current rotation rate
    short inzonecounter=0;

	
	public DriveTurnToVisionPinTarget(double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		visionTurnController = new PIDController(kP, kI, kD, kF, Robot.vision, this);
		visionTurnController.setInputRange(-180.0f,  180.0f);
	    visionTurnController.setOutputRange(-0.25, 0.25);
	    visionTurnController.setAbsoluteTolerance(kToleranceDegrees);
	    visionTurnController.setContinuous(true); 
	    setpointangle  = angle;
		this.setTimeout(15.0);

	}

	

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.navigation.zeroYaw();
		visionTurnController.reset();
		visionTurnController.setSetpoint(setpointangle);
		visionTurnController.enable();
		inzonecounter=0;
		 
	}


	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.arcadeDrive(0, pidOutputAngle, false);
		showDashTestInfo();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean done=false;
		
		if (Math.abs(Robot.navigation.getRate())< 5 && Math.abs(pidOutputAngle)<0.2 ){
			inzonecounter++;
			if (inzonecounter>20)done = true;
		}
		else 
		{
			inzonecounter=0;
		}
		return done || this.isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		visionTurnController.disable();
		
		Robot.drive.arcadeDrive(0, 0, false);	
		Robot.drive.setEnableBrake(true);
		
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	protected void showDashTestInfo() {
		if (ISSMARTDASHDEBUG_ENABLED) {
			SmartDashboard.putNumber("PID vis getSetpoint", visionTurnController.getSetpoint());
		    SmartDashboard.putNumber("PID vis pidOutputAngle", pidOutputAngle);
     		SmartDashboard.putData  ("PID vis turnController", visionTurnController);
			
		}
			
	
	}

	@Override
	public void pidWrite(double output) {
		pidOutputAngle = output;
	}

}
