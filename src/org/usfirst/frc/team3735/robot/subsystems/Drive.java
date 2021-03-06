package org.usfirst.frc.team3735.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.RobotMap;
import org.usfirst.frc.team3735.robot.commands.drive.ExpDrive;


/**
 *
 */

public class Drive extends Subsystem {
	
	private CANTalon l1,l2,l3,r1,r2,r3;
	RobotDrive driveTrain;
	
	private boolean reversed = false;

	public Drive() {
		/******************
		 * Drivetrain Left
		 ******************/
		l1 = new CANTalon(RobotMap.Drive.leftMotor1);
		l2 = new CANTalon(RobotMap.Drive.leftMotor2);
		l3 = new CANTalon(RobotMap.Drive.leftMotor3);

		/******************
		 * Drivetrain Right
		 ******************/
		r1 = new CANTalon(RobotMap.Drive.rightMotor1);
		r2 = new CANTalon(RobotMap.Drive.rightMotor2);
		r3 = new CANTalon(RobotMap.Drive.rightMotor3);
		/* Setup Encoders and Controls Scaling */
		
		setupSlaves();
		
		initLeftRightSensorsAndControls();
		
		setDefaultPIDVelocityControlMode();
		setEnableBrake(false);
		
		driveTrain = new RobotDrive(l1, r1);
		reversed = false;

		setupDriveSensitivityAndScaling();
		

	}

	/*******************************
	 * Default Commend For Drive
	 *******************************/
	public void initDefaultCommand() {
		setDefaultCommand(new ExpDrive());
	}


	/*******************************
	 * The Main Robot Arcade Drive
	 *******************************/
	public void arcadeDrive(double move, double rotate, boolean squareValues) {
		driveTrain.arcadeDrive(move, (rotate ) * -1, squareValues);
	}

	public void normalDrive(double move, double curve) {
		driveTrain.drive(move, curve);
	}
	
	


	/*******************************
	 * Setup Config Items for Drive
	 *******************************/
	public void setupDriveSensitivityAndScaling() {
		driveTrain.setSensitivity(Constants.Drive.sensitivity);
		driveTrain.setMaxOutput(Constants.Drive.scaledMaxOutput);
	}

	/*******************************
	 * Default Commend For Drive
	 *******************************/
	public void initLeftRightSensorsAndControls() {
		
		int absolutePosition = l1.getPulseWidthPosition()
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about
							 * the wrap around
							 */

		l1.reverseOutput(false);

		l1.setEncPosition(absolutePosition);
		l1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		l1.reverseSensor(true);
		l1.configNominalOutputVoltage(+0.5f, -0.5f);
		l1.configPeakOutputVoltage(+12.0f, -12.0f);
		l1.setPosition(0);


		absolutePosition = r1.getPulseWidthPosition()
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about
							 * the wrap arounds
							 */

		r1.reverseOutput(true);
		r1.setEncPosition(absolutePosition);
		r1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		r1.reverseSensor(false);
		r1.configNominalOutputVoltage(+0.5f, -0.5f);
		r1.configPeakOutputVoltage(+12.0f, -12.0f);
		r1.setPosition(0);


	}
	
	public void setPIDSettings(double kp, double ki, double kd, double kf, int kz, double kramp ){
		l1.setPID(kp, ki, kd, kf, kz, kramp, 0);
		r1.setPID(kp, ki, kd, kf, kz, kramp, 0);
		
	}

	public void setDefaultPIDVelocityControlMode(){
		
		l1.setPID(1, 0, 0, 0, 0, 6, 0);
		r1.setPID(1, 0, 0, 0, 0, 6, 0);
	}
	public void setLeftPID(double kp, double ki, double kd){
		l1.setP(kp);
		l1.setI(ki);
		l1.setD(kd);
	}
	public void setRightPID(double kp, double ki, double kd){
		r1.setP(kp);
		r1.setI(ki);
		r1.setD(kd);
	}


	/*******************************
	 * Direction Change
	 *******************************/
	public void changeDirection() {
		if (reversed) {
			changeToForward();
		} else {
			changeToReverse();
		}
	}

	/*******************************
	 * Direction Change Forward
	 *******************************/
	public void changeToForward() {
		if (reversed) {
			driveTrain = new RobotDrive(l1, r1);
			reversed = false;
			setupDriveSensitivityAndScaling();
		}
	}

	/*******************************
	 * Direction Change Reverse
	 *******************************/
	public void changeToReverse() {
		if (!reversed) {
			driveTrain = new RobotDrive(r1, l1);
			reversed = true;
			setupDriveSensitivityAndScaling();
		}
	}
	
	public void resetEncodersPositions(){
		l1.setPosition(0);
		r1.setPosition(0);
	}
	

	public void setLeftRightRotations(double left, double right) {
		l1.set(left);
		r1.set(right);
	}

	public void setLeftRightDistance(double left, double right) {
		l1.set(left / (Constants.Drive.OneRotationInches )); 
		r1.set(right / (Constants.Drive.OneRotationInches ));
	}

	public void setLeftRightOutputs(double leftOutput, double rightOutput) {
		driveTrain.setLeftRightMotorOutputs(leftOutput, rightOutput);
	}

	public double getAverageDisplacementInches() {
		return .5 * (getInchesPositionLeftInches() + getInchesPositionRightInches());
	}

	public double getRotationsLeft() {
		return l1.getPosition();
	}

	public double getRotationsRight() {
		return r1.getPosition();
	}

	public double getInchesPositionLeftInches() {
		return getRotationsLeft() * (Constants.Drive.OneRotationInches);
	}

	public double getInchesPositionRightInches() {
		return getRotationsRight() * (Constants.Drive.OneRotationInches);
	}

	public void changeScaledMaxOutput(double output) {
		driveTrain.setMaxOutput(output);
	}
	
	public void sendLeftVoltage(double vol){
		l1.configPeakOutputVoltage(vol, -vol);
	}
	public void sendRightVoltage(double vol){
		r1.configPeakOutputVoltage(vol, -vol);
	}

	/*******************************
	 * Position Control Setup
	 *******************************/
	public void setupDriveForPositionControl() {
		l1.setAllowableClosedLoopErr(0);
		l1.setProfile(0);
		l1.changeControlMode(TalonControlMode.Position);
		//l1.setIZone(2);
		
		// l1.setMotionMagicCruiseVelocity(cruiseVelocity);
		// l1.setMotionMagicAcceleration(accel);

		r1.setAllowableClosedLoopErr(0);
		r1.setProfile(0);
		r1.changeControlMode(TalonControlMode.Position);
		//r1.setIZone(2);
		//setEnableBrake(true);
		// r1.setMotionMagicCruiseVelocity(cruiseVelocity);
		// r1.setMotionMagicAcceleration(accel);
	}

	/*******************************
	 * Speed Control Setup
	 *******************************/
	public void setUpDriveForSpeedControl() {
		setEnableBrake(false);
		setDefaultPIDVelocityControlMode();
		l1.changeControlMode(TalonControlMode.PercentVbus);
		r1.changeControlMode(TalonControlMode.PercentVbus);

	}
	
	/*******************************
	 * Speed Enable Brake
	 *******************************/
	public void setEnableBrake(boolean b) {
		l1.enableBrakeMode(b);
		l2.enableBrakeMode(b);
		l3.enableBrakeMode(b);
		
		r1.enableBrakeMode(b);
		r2.enableBrakeMode(b);
		r3.enableBrakeMode(b);

	}


	




	/*******************************
	 * Slaves Setup
	 *******************************/
	private void setupSlaves() {

		l2.changeControlMode(CANTalon.TalonControlMode.Follower);
		l2.set(l1.getDeviceID());

		l3.changeControlMode(CANTalon.TalonControlMode.Follower);
		l3.set(l1.getDeviceID());

		r2.changeControlMode(CANTalon.TalonControlMode.Follower);
		r2.set(r1.getDeviceID());

		r3.changeControlMode(CANTalon.TalonControlMode.Follower);
		r3.set(r1.getDeviceID());
	}

	/******************************************
	 * Dashboard Update Display Variables
	 ******************************************/
	public void dashBoardUpdateDisplays() {
		SmartDashboard.putNumber("DriveLPosition", l1.getPosition());
		SmartDashboard.putNumber("DriveRPosition", r1.getPosition());

		SmartDashboard.putNumber("DriveLSpeed", l1.getSpeed());
		SmartDashboard.putNumber("DriveRSpeed", r1.getSpeed());

		SmartDashboard.putNumber("DriveLActual", l1.get());
		SmartDashboard.putNumber("DriveRActual", r1.get());

	}

	/******************************************
	 * Dashboard Update Setting Variables
	 ******************************************/
	public void dashBoardUpdateControls() {
//		scaledMaxOutput = SmartDashboard.getNumber("DriveParamMaxOut", defaultMaxOutput);
	}

	/******************************************
	 * The Logs
	 ******************************************/
	public void log() {
		dashBoardUpdateDisplays();
		dashBoardUpdateControls();
    	//SmartDashboard.putNumber("Gyro Yaw", ahrs.getYaw());

		//changeScaledMaxOutput(scaledMaxOutput);
	}

}

