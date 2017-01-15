package org.usfirst.frc100.BALLista.subsystems;

import org.usfirst.frc100.BALLista.Robot;
import org.usfirst.frc100.BALLista.RobotMap;
import org.usfirst.frc100.BALLista.commands.*;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends PIDSubsystem {

	private final SpeedController flyMotor = RobotMap.shooterFlyMotor;
	private final Counter flyCounter = RobotMap.shooterSpdCtr;
	private final PIDController shooterSpeedControllerPID = RobotMap.shooterShooterSpeedControllerPID;

	private static final double DEFAULT_SHOOTER_KP = .003;
	private static final double DEFAULT_SHOOTER_KI = 0.02;
	private static final double DEFAULT_SHOOTER_KD = 0.0;
	private static final double DEFAULT_SHOOTER_KF = 0.1;

	private double shooter_kP;
	private double shooter_kI;
	private double shooter_kD;
	private double shooter_kF;

	public static final double SHOOTER_TOP = 0.403;
	public static final double SHOOTER_MID = 0.558;
	public static final double SHOOTER_BOT = 0.658;

	// Initialize your subsystem here
	public Shooter() {

		super("Shooter", DEFAULT_SHOOTER_KP, DEFAULT_SHOOTER_KI,
				DEFAULT_SHOOTER_KD, DEFAULT_SHOOTER_KF);

		if (!Robot.prefs.containsKey("shooter_kP")) {
			Robot.prefs.putDouble("shooter_kP", DEFAULT_SHOOTER_KP);
		}
		if (!Robot.prefs.containsKey("shooter_kI")) {
			Robot.prefs.putDouble("shooter_kI", DEFAULT_SHOOTER_KI);
		}
		if (!Robot.prefs.containsKey("shooter_kD")) {
			Robot.prefs.putDouble("shooter_kD", DEFAULT_SHOOTER_KD);
		}
		if (!Robot.prefs.containsKey("shooter_kF")) {
			Robot.prefs.putDouble("shooter_kF", DEFAULT_SHOOTER_KF);
		}

		shooter_kP = Robot.prefs.getDouble("shooter_kP", DEFAULT_SHOOTER_KP);
		shooter_kI = Robot.prefs.getDouble("shooter_kI", DEFAULT_SHOOTER_KI);
		shooter_kD = Robot.prefs.getDouble("shooter_kD", DEFAULT_SHOOTER_KD);
		shooter_kF = Robot.prefs.getDouble("shooter_kF", DEFAULT_SHOOTER_KF);

		getPIDController().setPID(shooter_kP, shooter_kI, shooter_kD,
				shooter_kF);

		if (!Robot.prefs.containsKey("shooter_top")) {
			Robot.prefs.putDouble("shooter_top", SHOOTER_TOP);
		}
		if (!Robot.prefs.containsKey("shooter_mid")) {
			Robot.prefs.putDouble("shooter_mid", SHOOTER_MID);
		}
		if (!Robot.prefs.containsKey("shooter_bot")) {
			Robot.prefs.putDouble("shooter_bot", SHOOTER_BOT);
		}

		setAbsoluteTolerance(.002);
		// getPIDController().setContinuous(true);
		LiveWindow.addActuator("Shooter", "PIDSubsystem Controller",
				getPIDController());
		// getPIDController().setInputRange(0.0, 10.0);

		// Use these to get going:
		// setSetpoint() - Sets where the PID controller should move the system
		// to
		// enable() - Enables the PID controller.

	}

	public void initDefaultCommand() {

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		SmartDashboard.putNumber("rate", flyCounter.getRate());
		return flyCounter.getRate();
	}

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	SmartDashboard.putNumber("output value", output);
    	RobotMap.shooterFlyMotor.pidWrite(-output);
       // flyMotor.pidWrite(output);
	}

	public void updateDashboard() {

		SmartDashboard.putNumber("Shooter/FlyMotor Raw", flyMotor.get());
		SmartDashboard.putNumber("Shooter/FlyCounter Raw", flyCounter.getRate());
		// SmartDashboard.putNumber("Shooter/ShooterSpeedControllerPID",
		// shooterSpeedControllerPID.get());
		SmartDashboard.putNumber("Shooter/DistOfCounter", RobotMap.shooterSpdCtr.getDistance());
		SmartDashboard.putNumber("Shooter/RateOfCounter", RobotMap.shooterSpdCtr.getRate());
		SmartDashboard.putBoolean("Shooter/ShooterSensor", RobotMap.shooterSpdIn.get());
	}
}
