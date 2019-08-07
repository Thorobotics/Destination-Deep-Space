/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.ExecuteAuto;
import frc.robot.Subsystems.OI;
import frc.robot.Subsystems.RobotMap;
import frc.robot.Vision.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // Other classes
  Drive m_drive;
  ExecuteAuto m_execute;
  OI m_oi;
  RobotMap m_map;
  Vision m_vision;

  // Robot drive object
  DifferentialDrive robot;

  // Middle motor controllers
  Spark middleLeft, middleRight;

  // Actuator motors
  WPI_TalonSRX elevator, claw;

  // Pneumatics
  Compressor compressor;
  DoubleSolenoid s_claw; // solenoid_claw

  // Joystick
  Joystick leftStick, rightStick;

  // Camera connected to the RoboRio (camera 2)
  UsbCamera rioCamera;


  // Selected autonomous
  public String autoName = "RHL";
  // Close or open the claw
  boolean openClaw;
  // Debounce for claw
  boolean clawAction;
  // Have player control from auto
  public boolean playerControl;



  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    m_drive = new Drive();
    m_execute = new ExecuteAuto();
    m_map = new RobotMap();
    m_oi = new OI();
    m_vision = new Vision();

    robot = m_map.robot;

    middleLeft = m_map.middleLeft;
    middleRight = m_map.middleRight;

    claw = m_map.claw;
    claw.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    claw.setSelectedSensorPosition(0);
    elevator = m_map.elevator;
    elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    elevator.setSelectedSensorPosition(0);

    compressor = m_map.compressor;
    s_claw = m_map.solenoid;
    compressor.setClosedLoopControl(true); // Enable the compressor when the robot is enabled

    leftStick = m_oi.leftStick;
    rightStick = m_oi.rightStick;

    rioCamera = CameraServer.getInstance().startAutomaticCapture();
    rioCamera.setFPS(30);
    rioCamera.setResolution(160, 80);

    openClaw = false;
    clawAction = false;
    playerControl = true;
    
  }

  @Override
  public void robotPeriodic() {

    // Get and update values from vision tracking to ensure that it is running properly
    m_vision.get();

  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {

    teleopPeriodic();

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    System.out.println("LIFT PID: " + claw.getSelectedSensorPosition());

    // Output the name of the selected auto to the dashboard for ease of use
    SmartDashboard.putString("Auto Selected: ", autoName);
    SmartDashboard.putBoolean("Player Control", playerControl);
    // If button 5 is pressed (or held) start (or continue) the selected PID
    if (rightStick.getRawButton(m_oi.BUTTON_3)) { 
      playerControl = false;
      m_drive.omniDrive(robot, middleLeft, middleRight, leftStick.getRawAxis(m_oi.X_STICK), -rightStick.getRawAxis(m_oi.Y_STICK), rightStick.getRawAxis(m_oi.X_STICK));
      if (rightStick.getRawButton(m_oi.TRIGGER) && !clawAction) { // Toggle claw
        clawAction = true;
        s_claw.set(openClaw ? Value.kForward : Value.kReverse); // If openClaw = true, open the claw, otherwise close it
        openClaw = !openClaw; // If true, set to false, and vice versa
      } else if (!rightStick.getRawButton(m_oi.TRIGGER)) {
        clawAction = false;
      }
      m_execute.execute(autoName);
      if (!playerControl)
        return; // Only execute this if statement
    } else {
      playerControl = true;
    }

    if (rightStick.getRawButton(m_oi.BUTTON_5)) {
      m_vision.align();
    }

    // Claw control
    if (rightStick.getRawButton(m_oi.THUMB_BUTTON)) {
      System.out.println("Claw PID:" + rightStick.getRawAxis(m_oi.Y_STICK));
      claw.set(rightStick.getRawAxis(m_oi.Y_STICK));
      if (rightStick.getRawButton(m_oi.TRIGGER) && !clawAction) { // Toggle claw
        clawAction = true;
        s_claw.set(openClaw ? Value.kForward : Value.kReverse); // If openClaw = true, open the claw, otherwise close it
        openClaw = !openClaw; // If true, set to false, and vice versa
      } else if (!rightStick.getRawButton(m_oi.TRIGGER)) {
        clawAction = false;
      }
      return;
    } else {
      claw.set(0.0);
    }

    // Elevator control
    if (leftStick.getRawButton(m_oi.TRIGGER)) {
      elevator.set(-leftStick.getRawAxis(m_oi.Y_STICK)); // Push down to bring the lift down, pull back to bring the lift up
      System.out.println(-leftStick.getRawAxis(m_oi.Y_STICK));
      return; // Only run this if statement
    
    } else {
      elevator.set(0.0);
    }

    if (rightStick.getRawButton(m_oi.BUTTON_5)) {
      m_vision.align();
      return;
    }

    // Manual robot control
    m_drive.omniDrive(robot, middleLeft, middleRight, leftStick.getRawAxis(m_oi.X_STICK), -rightStick.getRawAxis(m_oi.Y_STICK), rightStick.getRawAxis(m_oi.X_STICK));

    // Choose the next auto align
    if (rightStick.getRawButton(m_oi.THUMB_BUTTON)) { return; } // If the claw is being controlled, don't continue
    if (rightStick.getRawButton(m_oi.BUTTON_9)) {
      autoName = "RHM";
    } else if (rightStick.getRawButton(m_oi.BUTTON_7)) {
      autoName = "RHT";
    } else if (rightStick.getRawButton(m_oi.BUTTON_11)) {
      autoName = "RHL";
    } else if (rightStick.getRawButton(m_oi.BUTTON_12)) {
      autoName = "RBL";
    } else if (rightStick.getRawButton(m_oi.BUTTON_10)) {
      autoName = "RBM";
    } else if (rightStick.getRawButton(m_oi.BUTTON_8)) {
      autoName = "RBT";
    }
    m_execute.reset();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
