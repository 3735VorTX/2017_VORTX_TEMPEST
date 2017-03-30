package org.usfirst.frc.team3735.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/***********************************************
 *
 ***********************************************/

public class Vision extends SensorBase  implements PIDSource{
	NetworkTable table;



	public Vision() {
		table = NetworkTable.getTable("vision/filteredcontours");
		
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
	}


	public void updateVisionDataTables() {
		double[] defaultValue = new double[0];
		double[] cx = table.getNumberArray("cx", defaultValue);
		double[] cy = table.getNumberArray("cy", defaultValue);
		double[] h  = table.getNumberArray("height", defaultValue);
		double[] w  = table.getNumberArray("width", defaultValue);
		double   hb = table.getNumber("hb", 0);
		double   fps= table.getNumber("fps", 0);
	}

	public void log() {
	//	SmartDashboard.putNumber("Navigation Yaw", getYaw());
	//	SmartDashboard.putNumber("Navigation Rate", gyrospi.getRate());
		
	}
	

	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;// kDisplacement
		
		
	}

	@Override
	public double pidGet() {
		return 0;
	}

}
