package frc.robot.Autonomous;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.PID;

/**
 * ClawAndLift
 */
public class ClawAndLift {

    // Other classes
    PID clawPID, liftPID;

    // Actuator objects
    WPI_TalonSRX claw, lift;

    // PID values for both claw and lift
    public int clawPIDSetpoint, liftPIDSetpoint;

    public ClawAndLift() {

        claw = new WPI_TalonSRX(1); // Device ID in Pheonix Tuner
        lift = new WPI_TalonSRX(0);
        clawPID = new PID(0.0003, 0.000001, 0, 0.0, 0.4); // Arbitrary values, change on testing
        liftPID = new PID(0.02, 0.000175, 0, 0.0, 1.0); // May up the max output, depending on how well it moves during the practice match day

        clawPIDSetpoint = 0;
        liftPIDSetpoint = 0;

    }

    public void execute() {
        //System.out.println("LIFT PID: " + liftPID.calculatePID(liftPIDSetpoint, lift.getSelectedSensorPosition()));
        System.out.println("CLAW PID: " + clawPID.calculatePID(clawPIDSetpoint, claw.getSelectedSensorPosition()));
        claw.set(clawPID.calculatePID(clawPIDSetpoint, claw.getSelectedSensorPosition()));
        lift.set(liftPID.calculatePID(liftPIDSetpoint, lift.getSelectedSensorPosition()));

        SmartDashboard.putBoolean("In Position: ", (Math.abs(claw.get()) - clawPID.minOutput < 0.03) && (Math.abs(lift.get()) - liftPID.minOutput < 0.03));

    }


}