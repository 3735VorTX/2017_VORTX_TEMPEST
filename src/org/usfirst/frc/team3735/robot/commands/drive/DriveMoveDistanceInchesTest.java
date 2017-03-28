package org.usfirst.frc.team3735.robot.commands.drive;

import org.usfirst.frc.team3735.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class DriveMoveDistanceInchesTest extends Command {

	/**************** MAKE ADJUSTMENTS TO THESE CONSTANTS AND ENABLE DISABLES */
	public static final boolean ISCONSOLEDEBUG_ENABLED 	= true;
	public static final boolean ISSMARTDASHDEBUG_ENABLED= true;
	public static final boolean ENABLE_EXPRAMP 			= true;
	

	final double STATEPERIODINTERVAL 	= 0.05;
	final double REACHEDTOLERANCE 		= 1.0; 	// Inches
	final short POSTREACHED_TRYCNT 		= 4; 	// 

	final short EXPRAMP_NFACTOR 		= 8; 	// Small is Fast Large is Slow 2 fast, 8, 32 slow are good numbers 

	
	final static double KP = 0.018;
	final static double KI  = 0.00010;
	final static double KD = 0.000;
	/****************************************/

	/****************************************/
	/* STATE MACHINE */
	/****************************************/

	public enum STATE {
		IDLE, 			/* This Idles Nothing Going On */
		INITTRAVEL, 	/* This is Start of SM */
		ZERODISTANCE, 	/* Reset Encoders to to Zero Position */
		SETSTARTPOS,  	/* Save the Start Position as we Need to Calculate End */
		STARTPID, 		/* Init PID For Travel */
		TRAVELUNTILREACHED,  /* This is a State That Runs the POSITION Mode Until Reached */
		REACHED, 		/* We Reached End so Just Do some Fine tuning of Position  */
		CLEANUPEND		/* House Keeping Clean up in prep to Idle State.  */
	};


	private double displacementInches;
	private double startPositionLeftInches;
	private double startPositionRightInches;
	private double endPositionLeftInches;
	private double endPositionRightInches;
	private double filtdisplacementInches;
	private STATE currentstate;
	private STATE nextstate;
	private double stateDoWorkTime = 0;
	private boolean typeistwist = false;
	private boolean done = false;
	private short postreachcntr = 0;


	public DriveMoveDistanceInchesTest(double distance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.currentstate = STATE.IDLE;
		this.nextstate = STATE.IDLE;
		this.displacementInches = distance;
		this.typeistwist = false;
		this.filtdisplacementInches = 0;
		this.setTimeout(10.0);

	}

	public DriveMoveDistanceInchesTest(double distance, boolean typeistwist) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.currentstate = STATE.IDLE;
		this.nextstate = STATE.IDLE;
		this.displacementInches = distance;
		this.typeistwist = typeistwist;
		this.filtdisplacementInches = 0;
		this.setTimeout(15.0);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		done = false;
		this.currentstate = STATE.INITTRAVEL;
		stateDoWorkTime = Timer.getFPGATimestamp() + STATEPERIODINTERVAL; // 50
																			// ms
																			// State
		// Events
	}

	protected void stateMachine() {
		switch (currentstate) {

		case IDLE:
			this.nextstate = STATE.IDLE; 				/* Just Index to Next State */
			break;

		case INITTRAVEL:
			this.nextstate = STATE.ZERODISTANCE; 		/* Just Index to Next State */
			postreachcntr = 0;
			break;

		case ZERODISTANCE:
			Robot.drive.resetEncodersPositions();
			this.nextstate = STATE.SETSTARTPOS;			/* Just Index to Next State */
			break;

		case SETSTARTPOS:
			startPositionLeftInches = Robot.drive.getInchesPositionLeftInches();
			startPositionRightInches = Robot.drive.getInchesPositionRightInches();
			this.nextstate = STATE.STARTPID;			/* Just Index to Next State */
			break;

		case STARTPID:
			//Robot.drive.setPIDSettings(KP, KI, KD);
			Robot.drive.setupDriveForPositionControl();
			this.nextstate = STATE.TRAVELUNTILREACHED;	/* Just Index to Next State */
			filtdisplacementInches = 0;
			endPositionLeftInches=startPositionLeftInches;
			endPositionRightInches=startPositionRightInches;
			
			break;

		case TRAVELUNTILREACHED:
			
			/**************************************
			 *  Update the Drive Outputs with New Positions
			 */
			
			Robot.drive.setLeftRightDistance(endPositionLeftInches, endPositionRightInches);

			/*******************************
			 *  If Ramping we do Ramping other wise just straight step to end positon
			 */
			
			if (DriveMoveDistanceInchesTest.ENABLE_EXPRAMP) {
				filtdisplacementInches = ((EXPRAMP_NFACTOR - 1) * filtdisplacementInches + displacementInches)
						/ EXPRAMP_NFACTOR;
				endPositionLeftInches = startPositionLeftInches + filtdisplacementInches;
				if (typeistwist == false)
					endPositionRightInches = startPositionRightInches + filtdisplacementInches;
				else
					endPositionRightInches = startPositionRightInches - filtdisplacementInches;
				
			} else {

				endPositionLeftInches = startPositionLeftInches + displacementInches;
				if (typeistwist == false)
					endPositionRightInches = startPositionRightInches + displacementInches;
				else
					endPositionRightInches = startPositionRightInches - displacementInches;
			}
	
		
			if (DriveMoveDistanceInchesTest.ISCONSOLEDEBUG_ENABLED) {
				System.out.format("[%08.3f]:", stateDoWorkTime);
				System.out.print("|");
			}

			/**************************************
			 * When we Reach End Position we Are Done we are reached!
			 */
			if (isTravelReachedEndPosition()) {
				this.nextstate = STATE.REACHED;
			}
			break;

		case REACHED:
			// Reached But Last Fine Attempt to Get Final Fine Adjust
			// We Try for POSTREACHED_TRYCNT
			endPositionLeftInches = startPositionLeftInches + displacementInches;
			if (typeistwist == false)
				endPositionRightInches = startPositionRightInches + displacementInches;
			else
				endPositionRightInches = startPositionRightInches - displacementInches;
			Robot.drive.setLeftRightDistance(endPositionLeftInches, endPositionRightInches);

			if (++postreachcntr > POSTREACHED_TRYCNT) {
				this.nextstate = STATE.CLEANUPEND; /* Just Index to Next State After we count out*/
			}
			break;

		case CLEANUPEND:
			Robot.drive.setUpDriveForSpeedControl();
			done = true; /** WE ARE DONE SO LET DONE TERMINATE TO CND **/
			this.nextstate = STATE.IDLE;  /* Just Index to Idle*/
			break;

		default:
			Robot.drive.setUpDriveForSpeedControl();
			done = true; /** WE ARE DONE SO LET DONE TERMINATE TO CND **/
			this.nextstate = STATE.IDLE; /* Just Index to Idle*/
			break;
		}

		this.currentstate = this.nextstate;

	}

	protected boolean isTravelReachedEndPosition() {
		boolean rval = false;
		
		System.out.format("[%08.3f]: ErrLeft", Robot.drive.getInchesPositionLeftInches()-endPositionLeftInches);
		System.out.format("[%08.3f]: ErrRight", Robot.drive.getInchesPositionRightInches()-endPositionRightInches);
		System.out.println("");
		
		if (Math.abs(Robot.drive.getInchesPositionLeftInches()-endPositionLeftInches) < REACHEDTOLERANCE
				&& Math.abs(Robot.drive.getInchesPositionRightInches()-endPositionRightInches) < REACHEDTOLERANCE) {
			rval = true;
		}
		return rval;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// *******************
		// Call the State Machine at a Periodic Rate
		// *******************

		if (Timer.getFPGATimestamp() > stateDoWorkTime) {

			if (DriveMoveDistanceInchesTest.ISCONSOLEDEBUG_ENABLED) {
				System.out.format("[%08.3f]:", stateDoWorkTime);
				System.out.println(currentstate);
			}
			stateDoWorkTime = Timer.getFPGATimestamp() + STATEPERIODINTERVAL;
			stateMachine();
		}
		else
		if  (this.currentstate == STATE.TRAVELUNTILREACHED 
			|| this.currentstate == STATE.REACHED )
		{
			Robot.drive.setLeftRightDistance(endPositionLeftInches, endPositionRightInches);
		}	

		// This is an time out abort
		if (this.isTimedOut())
			done = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		done = true;
		this.currentstate = STATE.IDLE;
		Robot.drive.setUpDriveForSpeedControl();

	}

	protected void showDashTestInfo() {
		// SmartDashboard.putNumber("Cmd get rotations left",
		// Robot.drive.getRotationsLeft());
		// SmartDashboard.putNumber("Cmd get rotations right",
		// Robot.drive.getRotationsRight());
		//
		// SmartDashboard.putNumber("CmdGetRInches",
		// Robot.drive.getInchesPositionRightInches());
		// SmartDashboard.putNumber("CmdGetLInches",
		// Robot.drive.getInchesPositionLeftInches());
		//
		// SmartDashboard.putNumber("CmdSPLEnd", endPositionLeftInches);
		// SmartDashboard.putNumber("CmdSPREnd", endPositionRightInches);
		// System.out.println("Working");
	}

}
