//values for the practice robot

//package org.usfirst.frc.team3735.robot;
//
//public class RobotMap {
//
//	public static class Drive{
//		public static int leftMotor1 =		4;
//		public static int leftMotor2 = 		5;
//		public static int leftMotor3 = 		6;
//		
//		public static int rightMotor1 = 	1;
//		public static int rightMotor2 = 	2;
//		public static int rightMotor3 = 	3;
//	}
//	public static class GearIntake{
//		public static int topRoller = 		9;
//		public static int bottomRoller = 	7;
//		public static int topFeedSolenoid =			1;
//		public static int liftSolenoid = 			0;
//	}
//	public static class Shooter{
//		public static int drum = 			12;
//		public static int drum2 = 			13;
//		public static int agitator =   		8;
//		public static int encoder1 = 			0;
//		public static int encoder2 = 			1;
//	}
//	public static class BallIntake{
//		public static int roller = 			11;
//	}
//	public static class Scaler{
//		public static int motor = 			10;
//	}
//		
//}

//values for the final robot

package org.usfirst.frc.team3735.robot;

public class RobotMap {
	/**************** SELECT PRACTICEBOT_IO = TRUE For Practice or FALSE for Compete */
	public static final boolean PRACTICEBOT_IO = true;


	public static class Drive {
		public static int leftMotor1 	= (PRACTICEBOT_IO)? 4:/*<-PRACTICEBOT*/		4; /*<-COMPETEBOT*/
		public static int leftMotor2 	= (PRACTICEBOT_IO)? 5:/*<-PRACTICEBOT*/		5; /*<-COMPETEBOT*/
		public static int leftMotor3 	= (PRACTICEBOT_IO)? 6:/*<-PRACTICEBOT*/		6; /*<-COMPETEBOT*/

		public static int rightMotor1 	= (PRACTICEBOT_IO)? 1:/*<-PRACTICEBOT*/		10; /*<-COMPETEBOT*/
		public static int rightMotor2 	= (PRACTICEBOT_IO)? 2:/*<-PRACTICEBOT*/		11; /*<-COMPETEBOT*/
		public static int rightMotor3 	= (PRACTICEBOT_IO)? 3:/*<-PRACTICEBOT*/		12; /*<-COMPETEBOT*/
	}

	public static class GearIntake {
		public static final boolean topRollerInverted = true;
		public static int topRoller 	= (PRACTICEBOT_IO)? 8:/*<-PRACTICEBOT*/ 		8; /*<-COMPETEBOT*/
		public static int topFeedSolenoid = (PRACTICEBOT_IO)?0:/*<-PRACTICEBOT*/ 		0; /*<-COMPETEBOT*/
		public static int liftSolenoid 	= (PRACTICEBOT_IO)? 1:/*<-PRACTICEBOT*/ 		1;/*<-COMPETEBOT*/
	}

	public static class Shooter {
		public static final boolean drumInverted = false;
		public static final boolean agitatorInverted = false;
		public static int drum 			= (PRACTICEBOT_IO)?7:/*<-PRACTICEBOT*/ 			2;  /*<-COMPETEBOT*/
		public static int drum2 		= (PRACTICEBOT_IO)?9:/*<-PRACTICEBOT*/			7;  /*<-COMPETEBOT*/
		public static int agitator 		= (PRACTICEBOT_IO)?7:/*<-PRACTICEBOT*/			13; /*<-COMPETEBOT*/
	}

	public static class BallIntake {
		public static int roller 		= (PRACTICEBOT_IO)?11:/*<-PRACTICEBOT*/			11; /*<-COMPETEBOT*/
		public static final boolean rollerInverted = false;
	}

	public static class Scaler {
		public static final boolean scalerInverted = false;
		public static int motor  		= (PRACTICEBOT_IO)? 10:	/*<-PRACTICEBOT*/		3;/*<-COMPETEBOT*/
	}
	
	

}
