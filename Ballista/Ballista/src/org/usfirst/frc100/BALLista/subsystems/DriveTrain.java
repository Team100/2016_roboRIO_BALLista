package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.Robot;

import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.TankDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private final SpeedController left = RobotMap.driveTrainLeft;
	private final SpeedController right = RobotMap.driveTrainRight;
	private final RobotDrive twoMotorDrive = RobotMap.driveTrainTwoMotorDrive;
	private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder;
	public PIDController pidRight;
	public PIDController pidLeft;

	public PIDController pid;
	private boolean driveDirection = true;
	private int distances;


	private static final double DEFAULT_DRIVE_TRAIN_KP = 180.04; //.004
	private static final double DEFAULT_DRIVE_TRAIN_KI = 0.00;
	private static final double DEFAULT_DRIVE_TRAIN_KD = 0.0;

	public double driveTrain_kP;
	public double driveTrain_kI;
	public double driveTrain_kD;

	public void updateDashboard() {
		SmartDashboard.putNumber("DriveTrain/Left Encoder Raw", leftEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Raw", rightEncoder.getRaw());
		SmartDashboard.putNumber("DriveTrain/Left Encoder Count", leftEncoder.get());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Count", rightEncoder.get());
		SmartDashboard.putNumber("DriveTrain/Left Encoder Distance", leftEncoder.getDistance());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Distance", rightEncoder.getDistance());
    	SmartDashboard.putNumber("DriveTrain/Left Encoder Rate", leftEncoder.getRate());
		SmartDashboard.putNumber("DriveTrain/Right Encoder Rate", rightEncoder.getRate());
		SmartDashboard.putNumber("DriveTrain/Gyro Angle", RobotMap.internalGyro.getAngle());
		SmartDashboard.putNumber("DriveTrain/Heading", RobotMap.internalGyro.getAngle() * 0.03);
		SmartDashboard.putNumber("DriveTrain/HoldItValue", Robot.driveTrain.pid.getSetpoint());
    	SmartDashboard.putBoolean("DriveTrain/Orientation", driveDirection);
    	SmartDashboard.putNumber("DriveTrain/DifferenceOfEncodersDistance:", Math.abs(rightEncoder.getDistance() - leftEncoder.getDistance()));
		SmartDashboard.putNumber("DriveTrain/DifferenceOfEncodersRate:", Math.abs(rightEncoder.getRate() - leftEncoder.getRate()));

		/*
		// Acceleration code
		SmartDashboard.putNumber("DriveTrain/Acceleration Limit", driveLimit);
		SmartDashboard.putNumber("DriveTrain/Interval", accelerationLoopInterval);

		// only applies to non-slide
		SmartDashboard.putNumber("DriveTrain/Velocity", velocity);
		SmartDashboard.putNumber("DriveTrain/Acceleration", trueAcceleration);
		*/
	}

	public DriveTrain() {

		if (!Robot.prefs.containsKey("driveTrain_kP")) {
			Robot.prefs.putDouble("driveTrain_kP", DEFAULT_DRIVE_TRAIN_KP);
		}
		if (!Robot.prefs.containsKey("driveTrain_kI")) {
			Robot.prefs.putDouble("driveTrain_kI", DEFAULT_DRIVE_TRAIN_KI);
		}
		if (!Robot.prefs.containsKey("driveTrain_kD")) {
			Robot.prefs.putDouble("driveTrain_kD", DEFAULT_DRIVE_TRAIN_KD);
		}

		driveTrain_kP = Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP);
		driveTrain_kI = Robot.prefs.getDouble("driveTrain_kI",
				DEFAULT_DRIVE_TRAIN_KI);
		driveTrain_kD = Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD);

		pid = new PIDController(driveTrain_kP, driveTrain_kI, driveTrain_kD, new PIDSource() { // .04 0 0 for 180
					PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

					public double pidGet() {
						return RobotMap.internalGyro.getAngle();
					}

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {
						m_sourceType = pidSource;
					}

					@Override
					public PIDSourceType getPIDSourceType() {
						return m_sourceType;
					}
				}, new PIDOutput() {
					public void pidWrite(double d) {
						right.pidWrite(d/2); // /2
						left.pidWrite(-d/2); // /2
					}
				});
		//pid.setPID(p, i, d);
		pidRight = new PIDController(Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP), Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD), 0, new PIDSource() { // .04 0 0 for 180
					PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

					public double pidGet() {
						return Math.abs(RobotMap.driveTrainLeftEncoder.getDistance());
					}

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {
						m_sourceType = pidSource;
					}

					@Override
					public PIDSourceType getPIDSourceType() {
						return m_sourceType;
					}
				}, new PIDOutput() {
					public void pidWrite(double d) {
						right.pidWrite(d); // /2

					}
				});
		pidLeft = new PIDController(Robot.prefs.getDouble("driveTrain_kP",
				DEFAULT_DRIVE_TRAIN_KP), Robot.prefs.getDouble("driveTrain_kD",
				DEFAULT_DRIVE_TRAIN_KD), 0, new PIDSource() { // .04 0 0 for 180
					PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

					public double pidGet() {
					 return Math.abs(RobotMap.driveTrainLeftEncoder.getDistance());
					}

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {
						m_sourceType = pidSource;
					}

					@Override
					public PIDSourceType getPIDSourceType() {
						return m_sourceType;
					}
				}, new PIDOutput() {
					public void pidWrite(double d) {

						left.pidWrite(d); // /2
					}
				});
	}

	public void initDefaultCommand() {

		setDefaultCommand(new TankDrive());
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void takeJoystickInputs(double x, double y) {
		// twoMotorDrive.tankDrive(left, right);
		twoMotorDrive.arcadeDrive(-x, y);

	}

	public void takeJoystickInputsReverse(double x, double y) {

		twoMotorDrive.arcadeDrive(-x, y);

	}

	public boolean getDriveDirection(){
		return driveDirection;
	}

	public void setDriveDirection(boolean input){
		driveDirection = input;
	}

	public int getDistances(){
		return distances;
	}

	public void setDistances(int input){
		distances = input;
	}

	public void stop() {
		twoMotorDrive.drive(0, 0);
	}

	public double getAngles() {

		return RobotMap.internalGyro.getAngle(); // add the gyro

	}

	public void drives(double speed) {
		//twoMotorDrive.drive(speed, -1*(RobotMap.internalGyro.getAngle() * .03));
		twoMotorDrive.tankDrive(-speed, speed);// .getAngleOfGyro());
		//SmartDashboard.putNumber("heading",
			//	(-RobotMap.internalGyro.getAngle()) * 0.03);

	}

}