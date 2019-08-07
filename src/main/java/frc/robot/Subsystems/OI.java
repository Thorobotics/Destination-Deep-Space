package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Joystick;

/**
 * OI
 */
public class OI {

    // Joystick axes mapping

    public final int X_STICK, Y_STICK, Z_STICK;

    // Joystick button mapping

    public final int THUMB_BUTTON, TRIGGER, BUTTON_3, BUTTON_4, BUTTON_5, BUTTON_6, BUTTON_7, BUTTON_8, BUTTON_9, BUTTON_10, BUTTON_11, BUTTON_12;

    // Joystick object

    public Joystick leftStick, rightStick;

    /**
     * The joystick mapping and joystick object class.
     */
    public OI() {

        X_STICK = 0;
        Y_STICK = 1;
        Z_STICK = 2;

        TRIGGER = 1;
        THUMB_BUTTON = 2;        
        BUTTON_3 = 3;
        BUTTON_4 = 4;
        BUTTON_5 = 5;
        BUTTON_6 = 6;
        BUTTON_7 = 7;
        BUTTON_8 = 8;
        BUTTON_9 = 9;
        BUTTON_10 = 10;
        BUTTON_11 = 11;
        BUTTON_12 = 12;

        /* new Joystick(JOYSTICK_ID)
            JOYSTICK_ID correlates to first joystick plugged in, not a particular joystick; ID starts at 0 and increments
        */

        rightStick = new Joystick(0);
        leftStick = new Joystick(1);

    }
    
}