package org.usfirst.frc.team3735.robot;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3735.robot.commands.autonomous.*;
import org.usfirst.frc.team3735.robot.pipelines.GearPipeline;
import org.usfirst.frc.team3735.robot.pipelines.StickyNotePipeline;
import org.usfirst.frc.team3735.robot.subsystems.BallIntake;
import org.usfirst.frc.team3735.robot.subsystems.Drive;
import org.usfirst.frc.team3735.robot.subsystems.GearIntake;
import org.usfirst.frc.team3735.robot.subsystems.Navigation;
import org.usfirst.frc.team3735.robot.subsystems.Scaler;
import org.usfirst.frc.team3735.robot.subsystems.Shooter;
import org.usfirst.frc.team3735.robot.subsystems.Ultrasonic;
import org.usfirst.frc.team3735.robot.subsystems.Vision;
import org.usfirst.frc.team3735.robot.util.DriveOI;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static final boolean CAMERA_ENABLED 	= false;
	public static final boolean NAVX_ENABLED 	= true;
	
	
	String autoSelected;
	Command autonomousCommand;
	SendableChooser autonomousChooser;
	public static BallIntake ballIntake;
	public static Drive drive;
	public static GearIntake gearIntake;
	public static Scaler scaler;
	public static Shooter shooter;
	public static Navigation navigation;
	public static Ultrasonic ultra;
	public static Vision vision;
	
	public static DriveOI oi;
	public RobotMap robotmap;
	public CoordinateHandler cords;
	
	//SendableChooser lrChooser;
	boolean rightSide = false;
	
	//camera stuff
	CameraServer camerserver;
	
	public void presentAutonModes(){
		autonomousChooser = new SendableChooser();
		autonomousChooser.addDefault ("Do Nothing", new AutonomousDoNothing());
		autonomousChooser.addObject("CenterStart Drop GearOnPin", new  AutonForwardDrivePositionWithGearDrop());
		autonomousChooser.addObject("LeftStart   Drop GearOnPin", new  AutonForwardDrivePositionLeftWithGearDrop());
		autonomousChooser.addObject("RightStart  DroP GearOnPin", new  AutonForwardDrivePositionRightWithGearDrop());
		
		//autonomousChooser.addObject("Drive to side and drop gear", new  AutonForwardDrivePositionSideWithGearDrop());
		//autonomousChooser.addObject("TimedDriveBaseStraightOnlyToBase", new AutonTimedDriveTimedDriveStraightToBase());
		//autonomousChooser.addObject("Drive to Base Line", new AutonForwardDrivePosition());
		//autonomousChooser.addObject("Drive to right side and drop gear", new  AutonForwardDrivePositionRightWithGearDrop());
		//autonomousChooser.addObject("Drive to middle drop gear and drive to baseline", new  AutonMiddleGearThenBaseline());
		SmartDashboard.putData("RobotAutoNMode", autonomousChooser);
	}
	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		robotmap = new RobotMap();
		//cords = new CoordinateHandler();
		
		gearIntake = new GearIntake();
		shooter = new Shooter();
		scaler = new Scaler();
		drive = new Drive();
		ballIntake = new BallIntake();
		if (NAVX_ENABLED){
			navigation = new Navigation();
		}
		oi = new GTAOI();
		ultra = new Ultrasonic();
		vision = new Vision();
		

		/* Lets Start the WEB CAMERA */

		if (CAMERA_ENABLED) {
			try {
				camerserver = CameraServer.getInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				camerserver.startAutomaticCapture();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			camerserver.startAutomaticCapture();

		}
		
			
		log();
	}
	
	@Override
	public void robotPeriodic() {
		Robot.drive.sendLeftVoltage((SmartDashboard.getNumber("left Voltage", 5.4)));
		Robot.drive.sendRightVoltage((SmartDashboard.getNumber("right Voltage", 5)));

	}
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
       autonomousCommand = (Command) autonomousChooser.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	
    public void teleopInit() {
   	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
    
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
        log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void log(){
    	oi.log();
		scaler.log();
		drive.log();
		shooter.log();
		ballIntake.log();
		gearIntake.log();
		ultra.log();
		navigation.log();
	}
	
	
	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
}

