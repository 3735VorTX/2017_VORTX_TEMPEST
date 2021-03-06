package org.usfirst.frc.team3735.robot.commands.autonomous;

//import org.usfirst.frc.team3735.robot.Constants;
import org.usfirst.frc.team3735.robot.util.Point;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceInches;
import org.usfirst.frc.team3735.robot.commands.drive.DriveMoveDistanceTimed;
import org.usfirst.frc.team3735.robot.commands.drive.DriveTurnToAngle;
import org.usfirst.frc.team3735.robot.commands.gearintake.GearIntakeDropOff;
import org.usfirst.frc.team3735.robot.Constants.PointsLeft;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
// Start for the middle then drive to gear lift and place gear.
// Then back up and and drive to gear loader
public class AutonomousMiddleGearTopLoader extends CommandGroup {
	
    public AutonomousMiddleGearTopLoader() {
    	Point point0 = PointsLeft.startMiddle;
    	Point point1 = PointsLeft.middleLift;
    	Point point2 = PointsLeft.midBackUp;
    	Point point3 = PointsLeft.retrivalAndBaseLine;
    	Point point4 = PointsLeft.opSideRetrivalAndBaseLine;
    	
    	addSequential(new DriveTurnToAngle(Point.findAngle(point0, point1)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point0, point1)));
    	addSequential(new GearIntakeDropOff());
    	addSequential(new DriveTurnToAngle(Point.findAngle(point1, point2)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point1, point2)));
    	addSequential(new DriveTurnToAngle(Point.findAngle(point2, point3)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point2, point3)));
    	addSequential(new DriveTurnToAngle(Point.findAngle(point3, point4)));
    	addSequential(new DriveMoveDistanceInches(Point.findDistance(point3, point4)));
    }
}
