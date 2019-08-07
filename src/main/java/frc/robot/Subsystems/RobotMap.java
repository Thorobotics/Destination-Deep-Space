package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * RobotMap
 */
public class RobotMap {

    // Map of the motor controllers

    Spark frontLeft, backLeft, frontRight, backRight;
    public Spark middleLeft, middleRight;

    // Drive train mapping

    SpeedControllerGroup leftTrain, rightTrain;

    // Actual drive train object

    public DifferentialDrive robot;

    // Actuator mapping

    public WPI_TalonSRX elevator, claw;

    // Pneumatic objects mapping

    public Compressor compressor;
    public DoubleSolenoid solenoid;


    /**
     * The motor control mapping for the robot
     */
    public RobotMap() {

        // new Spark(ROBORIO_PWM_PORT)

        frontLeft = new Spark(3);
        backLeft = new Spark(2);
        frontRight = new Spark(5);
        backRight = new Spark(4);
        middleLeft = new Spark(1);
        middleRight = new Spark(0);

        leftTrain = new SpeedControllerGroup(frontLeft, backLeft);
        rightTrain = new SpeedControllerGroup(frontRight, backRight);

        robot = new DifferentialDrive(leftTrain, rightTrain);

        // new WPI_TalonSRX(DEVICE_ID_IN_PHEONIX_TUNER)

        elevator = new WPI_TalonSRX(0);
        claw = new WPI_TalonSRX(1);

        // new DoubleSolenoid(FORWARD_CHANNEL_ID_ON_PCM, BACKWARD_CHANNEL_ID_ON_PCM)
        
        compressor = new Compressor(); // No device ID for ID 0
        solenoid = new DoubleSolenoid(0, 1);

    }
}