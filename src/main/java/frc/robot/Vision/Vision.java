package frc.robot.Vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Drive;

/**
 * Vision
 */
public class Vision {

    // Other classes
    Drive m_drive;

    // Network table objects
    NetworkTableInstance instance;
    NetworkTable visionNetworkTable;
    NetworkTableEntry yawEntry, pitchEntry, slideEntry;

    // Values for straffing, pitch, and yaw
    double yawValue, pitchValue, slideValue, pitchMaxOutput, pitchMinOutput, yawMaxOutput, yawMinOutput, slideOutput;

    // Robot drive object
    DifferentialDrive robot;

    // Middle motor controllers
    Spark middleLeft, middleRight;

    /**
     * Control the robot's drive movement based off of the Yaw, Pitch, and Slide values passed from the Raspberry Pi
     */
    public Vision() {

        m_drive = new Drive();

        instance = NetworkTableInstance.getDefault();
        visionNetworkTable = instance.getTable("VisionTable");
        yawEntry = visionNetworkTable.getEntry("Yaw");
        pitchEntry = visionNetworkTable.getEntry("Pitch");
        slideEntry = visionNetworkTable.getEntry("Slide");

        yawValue = 0.0;
        pitchValue = 0.0;
        slideValue = 0.0;
        yawMaxOutput = 0.635;
        yawMinOutput = 0.4876;
        pitchMaxOutput = 0.575;
        pitchMinOutput = 0.51;
        slideOutput = 0.49;

    }

    /**
     * Update the values for the drive train
     */
    public void get() {

        yawValue = yawEntry.getDouble(0.0) * 1.7; // Multiply to get the returned values in range for the motors
        pitchValue = pitchEntry.getDouble(0.0) * 2.0; // Multiple to get the returned values in the range for the motors
        slideValue = slideEntry.getDouble(0.0); // -1.0 is slide left, 0.0 is on target, 1.0 is slide right

        // Display vision tracking data

        SmartDashboard.putNumber("Yaw", (int)yawValue);
        SmartDashboard.putNumber("Pitch", (int)pitchValue);
        SmartDashboard.putString("Slide", slideValue == -1 ? "Slide Left" : SmartDashboard.getString("Slide", "No data"));
        SmartDashboard.putString("Slide", slideValue == 0 ? "Aligned" : SmartDashboard.getString("Slide", "No data"));
        SmartDashboard.putString("Slide", slideValue == 1 ? "Slide Right" : SmartDashboard.getString("Slide", "No data"));

    }

    /**
     * Limit the robot drive train's spin movement based on the camera input
     * @param yawValue A custom yawValue. Set to -100 to grab the one from the camera
     * @return A (+/-)value between the maxYawValue and minYawValue
     */
    private double limitYaw(double yawValue) {

        if (yawValue == -100)
            yawValue = this.yawValue;
        // Provide a gray area so the robot isn't continuously trying to correct itself
        if (Math.abs(yawValue) > 0.85) {
            yawValue /= 10;
            if (yawValue > 0) {
                yawValue = yawValue > yawMaxOutput ? yawMaxOutput : yawValue;
                yawValue = yawValue < yawMinOutput ? yawMinOutput : yawValue;
            } else {
                yawValue = yawValue < -yawMaxOutput ? -yawMaxOutput : yawValue;
                yawValue = yawValue > -yawMinOutput ? -yawMinOutput : yawValue;
            }
            return yawValue;
        }
        return 0.0;

    }

    /**
     * Limit the robot drive train's pitch movement based on the camera input
     * @param pitchValue A custom pitchValue. Set to -100 to grab the one from the camera
     * @return A (+/-)value between the maxpitchValue and minpitchValue
     */
    private double limitpitch(double pitchValue) {

        if (pitchValue == -100)
            pitchValue = this.pitchValue;
        // Provide a gray area so the robot isn't continuously trying to correct itself
        if (Math.abs(pitchValue) > 0.85) {
            pitchValue /= 10;
            if (pitchValue > 0) {
                pitchValue = pitchValue > pitchMaxOutput ? pitchMaxOutput : pitchValue;
                pitchValue = pitchValue < pitchMinOutput ? pitchMinOutput : pitchValue;
            } else {
                pitchValue = pitchValue < -pitchMaxOutput ? -pitchMaxOutput : pitchValue;
                pitchValue = pitchValue > -pitchMinOutput ? -pitchMinOutput : pitchValue;
            }
            return pitchValue;
        }
        return 0.0;

    }

    public void align() {

        //m_drive.omniDrive(robot, middleLeft, middleRight, slideOutput * slideValue, limitYaw(yawValue), limitpitch(pitchValue));

    }

}