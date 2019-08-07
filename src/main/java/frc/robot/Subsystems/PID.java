package frc.robot.Subsystems;

/**
 * PID
 */
public class PID {

    // P, I, D values
    double P, I, D, integral, previous_error, derivative, error;

    // Max and min motor outputs
    public double maxOutput, minOutput;
    
    // Lift PID encoder setpoints
    public final int RHL, RHM, RHT, RBL, RBM, RBT, CSB;

    // Claw PID encoder setpoints
    public final int TOP, MIDDLE, BOTTOM;

    /**
     * Porportional Integral Derivative; calculates the error for the lift and claw
     * <p>Ensure that the lift starts in the lowest point possible and the claw is facing down</p>
     * @param P The Porportional input
     * @param I The Integral input
     * @param D The Derivative input
     * @param minOutput The minimum output of the motor
     * @param maxOutput The maximum output of the motor
     */

    public PID(double P, double I, double D, double minOutput, double maxOutput) {

        this.P = P;
        this.I = I;
        this.D = D;
        this.maxOutput = maxOutput;
        this.minOutput = minOutput;
        this.previous_error = 0.0;

        RHL = 100;
        RHM = -7359;
        RHT = -14420;
        RBL = -2939;
        RBM = -9416;
        RBT = -16521;
        CSB = -3156; // Arbitrary value, needs to be updated

        TOP = 0;
        MIDDLE = -298873;
        BOTTOM = -631423;

    }

    /**
     * Calculate the PID error for the motor to run
     * @param setpoint The point PID will calculate to
     * @param currentValue The current reading from the encoder
     * @return The motor speed between the minimum and maximum output values
     */
    public double calculatePID(double setpoint, double currentValue) {
        error = setpoint - currentValue;
        integral += (error / 60.0);
        derivative = (error - previous_error) * 60.0;
        previous_error = error;
        error = (P * error + I * integral + D * derivative) / 10;
        if (Math.abs(error) < 0.1) {
            return 0.0;
        }
        System.out.println("Error: " + error);
        if (error < 0) {
            if (error < -maxOutput) {
                error = -maxOutput;
            }
            // error = error < -maxOutput ? error : -maxOutput;
            if (error > -minOutput) {
                error = -minOutput;
            }
        } else {
            // error = error < maxOutput ? error : maxOutput;
            // error = error > minOutput ? error : minOutput;
            if (error < minOutput) {
                error = minOutput;
            }
            // error = error < -maxOutput ? error : -maxOutput;
            if (error > maxOutput) {
                error = maxOutput;
            }
        }
        return error;
    }


}