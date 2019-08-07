package frc.robot.Subsystems;

import frc.robot.Autonomous.ClawAndLift;


/**
 * Run the currently selected autonomous
 * <p>When the button is released, stop() is called continuously to reset the auto files</p>
 */
public class ExecuteAuto {

    // Other classes
    PID m_pid;

    // Executable auto class
    ClawAndLift clawLift;

    public ExecuteAuto() {

        m_pid = new PID(0, 0, 0, 0, 0);

        clawLift = new ClawAndLift();

    }

    /*
        Auto name bank:
        RHL - Rocket Hatch Low / Player Station / Cargo Ship Hatch
        RHM - Rocket Hatch Mid
        RHT - Robot Hatch Top
        RBL - Rocket Ball Low
        RBM - Rocket Ball Mid
        RBT - Rocket ball Top
        CSB - Cargo Ship Ball
    */

    /**
     * Run the currently selected autonomous file
     */
    public void execute(String autoName) {
        System.out.println(autoName);
        switch (autoName) {
            case "RHM":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.RHM;
                break;

            case "RHT":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.RHT;
                break;

            case "RHL":
            clawLift.clawPIDSetpoint = m_pid.BOTTOM;
            clawLift.liftPIDSetpoint = m_pid.RBL;
                break;

            case "RBL":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.RBL;
                break;

            case "RBM":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.RBM;
                break;

            case "RBT":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.RBT;
                break;

            case "CSB":
            clawLift.clawPIDSetpoint = m_pid.MIDDLE;
            clawLift.liftPIDSetpoint = m_pid.CSB;
                break;

            default:
            clawLift.clawPIDSetpoint = m_pid.TOP;
            clawLift.liftPIDSetpoint = m_pid.RHL;
                break;
        }
        clawLift.execute();
    }

    public void reset() {
        clawLift.clawPIDSetpoint = m_pid.MIDDLE;
        clawLift.liftPIDSetpoint = m_pid.RHL;
        clawLift.execute();
    }
}