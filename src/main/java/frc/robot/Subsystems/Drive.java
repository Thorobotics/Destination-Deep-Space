package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Drive
 */
public class Drive {

    // Separate classes
    // RobotMap map;

    // Robot drive object
    DifferentialDrive robot;

    // Middle wheel controllers
    Spark middleLeft, middleRight;

    /**
     * The drive subsystem for the robot. Handles the drive train.
     */
    public Drive() {

    }

    /**
     * Drives the robot on all 3 values
     * @param straffe The x-axis value from the joystick
     * @param velocity The y-axis value from the joystick
     * @param turn The z-axis value from the joystick
     */
    public void omniDrive(DifferentialDrive robot, Spark middleLeft, Spark middleRight, double straffe, double velocity, double turn) {
        robot.arcadeDrive(velocity, turn);
        middleLeft.set(straffe);
        middleRight.set(straffe);
    }
}