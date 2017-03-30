package org.usfirst.frc.team3735.robot;

import org.usfirst.frc.team3735.robot.util.DriveOI;
import org.usfirst.frc.team3735.robot.util.JoystickPOVButton;
import org.usfirst.frc.team3735.robot.util.JoystickTriggerButton;
import org.usfirst.frc.team3735.robot.commands.autonomous.AutonForwardDrivePositionLeftWithGearDrop;
import org.usfirst.frc.team3735.robot.commands.autonomous.AutonForwardDriveSquare;
import org.usfirst.frc.team3735.robot.commands.drive.DriveJogTwistLeft;
import org.usfirst.frc.team3735.robot.commands.drive.DriveJogTwistRight;
import org.usfirst.frc.team3735.robot.commands.drive.DriveChangeToBallDirection;
import org.usfirst.frc.team3735.robot.commands.drive.DriveChangeToGearDirection;
import org.usfirst.frc.team3735.robot.commands.drive.DriveForwardToCurrentGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToOffsetGyroHeading;
import org.usfirst.frc.team3735.robot.commands.drive.DriveStopRobot;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeFeeding;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOff;
import org.usfirst.frc.team3735.robot.commands.shooter.ShooterOnAgitatorSmartDash;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GTAOI implements DriveOI {

	Joystick joy;
	Joystick cojoy;

	@SuppressWarnings("unused")
	public GTAOI() {

		// joystick port mapping
		joy = new Joystick(0);
		cojoy = new Joystick(1);

		// Button Mapping for driver joy-stick
		Button a = new JoystickButton(joy, 1);
		Button b = new JoystickButton(joy, 2);
		Button x = new JoystickButton(joy, 3);
		Button y = new JoystickButton(joy, 4);
		Button lb = new JoystickButton(joy, 5);
		Button rb = new JoystickButton(joy, 6);
		Button back = new JoystickButton(joy, 7);
		Button start = new JoystickButton(joy, 8);
		Button ls = new JoystickButton(joy, 9);
		Button rs = new JoystickButton(joy, 10);

		Button pov0 = new JoystickPOVButton(joy, 0);
		Button pov45 = new JoystickPOVButton(joy, 45);
		Button pov90 = new JoystickPOVButton(joy, 90);
		Button pov135 = new JoystickPOVButton(joy, 135);
		Button pov180 = new JoystickPOVButton(joy, 180);
		Button pov225 = new JoystickPOVButton(joy, 225);
		Button pov270 = new JoystickPOVButton(joy, 270);
		Button pov315 = new JoystickPOVButton(joy, 315);

		// Button Mapping for codriver joy-stick
		Button ca = new JoystickButton(cojoy, 1);
		Button cb = new JoystickButton(cojoy, 2);
		Button cx = new JoystickButton(cojoy, 3);
		Button cy = new JoystickButton(cojoy, 4);
		Button clb = new JoystickButton(cojoy, 5);
		Button crb = new JoystickButton(cojoy, 6);
		Button cback = new JoystickButton(cojoy, 7);
		Button cstart = new JoystickButton(cojoy, 8);
		Button cls = new JoystickButton(cojoy, 9);
		Button crs = new JoystickButton(cojoy, 10);
		Button clt = new JoystickTriggerButton(cojoy, false, .5);
		Button crt = new JoystickTriggerButton(cojoy, true, .5);

		Button cpov0 = new JoystickPOVButton(cojoy, 0);
		Button cpov45 = new JoystickPOVButton(cojoy, 45);
		Button cpov90 = new JoystickPOVButton(cojoy, 90);
		Button cpov135 = new JoystickPOVButton(cojoy, 135);
		Button cpov180 = new JoystickPOVButton(cojoy, 180);
		Button cpov225 = new JoystickPOVButton(cojoy, 225);
		Button cpov270 = new JoystickPOVButton(cojoy, 270);
		Button cpov315 = new JoystickPOVButton(cojoy, 315);

		x.whileHeld(new DriveStopRobot());
		b.whenPressed(new GearIntakeDropOff());

		a.whileHeld(new GearIntakeFeeding());

		start.whenPressed(new DriveChangeToGearDirection());
		back.whenPressed(new DriveChangeToBallDirection());

		lb.whileHeld(new DriveJogTwistLeft());
		rb.whileHeld(new DriveJogTwistRight());

		// cpov0.whenPressed(new ShooterOnAgitatorHigh());
		cpov90.whenPressed(new ShooterOnAgitatorSmartDash());
		// cpov180.whenPressed(new ShooterOnAgitatorLow());
		cpov270.whenPressed(new ShooterOff());

		if (false)
		{	
		SmartDashboard.putData("Test Navx Go 50", new DriveForwardToCurrentGyroHeading(50));
		SmartDashboard.putData("Test Navx Go 25", new DriveForwardToCurrentGyroHeading(25));

		SmartDashboard.putData("Test Navx 0 ", new DriveTurnToOffsetGyroHeading(0));
		SmartDashboard.putData("Test Navx 10 ", new DriveTurnToOffsetGyroHeading(10));
		SmartDashboard.putData("Test Navx 90 ", new DriveTurnToOffsetGyroHeading(90));
		SmartDashboard.putData("Test Navx 30 ", new DriveTurnToOffsetGyroHeading(30));
		SmartDashboard.putData("Test Navx 45 ", new DriveTurnToOffsetGyroHeading(45));

		SmartDashboard.putData("AutonForwardDrivePositionWithGearDropPinLeft ",
				new AutonForwardDrivePositionLeftWithGearDrop());

		SmartDashboard.putData("AutonForwardDriveSquare ", new AutonForwardDriveSquare());
		}
		
	}

	@Override
	public double getMainLeftX() {
		return joy.getX();
	}

	@Override
	public double getMainLeftY() {
		return joy.getY() * -1;
	}

	@Override
	public double getMainRightX() {
		return joy.getRawAxis(4);
	}

	@Override
	public double getMainRightY() {
		return joy.getRawAxis(5) * -1;
	}

	@Override
	public double getMainLeftTrigger() {
		return joy.getZ();
	}

	@Override
	public double getMainRightTrigger() {
		return joy.getThrottle();
	}

	public double getMainRightMagnitude() {
		return Math.hypot(getMainRightX(), getMainRightY());
	}

	// returns the angle of the right main joystick in degrees in the range
	// (-180, 180]
	public double getMainRightAngle() {
		return Math.toDegrees(Math.atan2(getMainRightX(), getMainRightY()));
	}

	@Override
	public double getDriveMove() {
		return (getMainRightTrigger() - getMainLeftTrigger());
	}

	@Override
	public double getDriveTurn() {
		return getMainLeftX();
	}

	public double getCoLeftX() {
		return cojoy.getX();
	}

	public double getCoLeftY() {
		return cojoy.getY() * -1;
	}

	public double getCoRightX() {
		return cojoy.getRawAxis(4);
	}

	public double getCoRightY() {
		return cojoy.getRawAxis(5) * -1;
	}

	public double getCoLeftTrigger() {
		return cojoy.getZ();
	}

	public double getCoRightTrigger() {
		return cojoy.getThrottle();
	}

	@Override
	public void log() {
		SmartDashboard.putNumber("right joystick angle", getMainRightAngle());
		SmartDashboard.putNumber("right joystick magnitude", getMainRightMagnitude());

	}

}
