/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Old.HardwareRegister;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwareRegister class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */


/**
 * Gunther's note:
 * This class is a driver class for HardwareRegister.
 * Copied from examples
 * There is a large possibility I have no idea what I'm talking about
 *
 * Kehoe's note:
 * die
 */







/**
 *
 *
 * THIS CLASS IS DEPRECATED, USE "DriverControlled.java" INSTEAD
 *
 *
 */








public class TeleOp1 extends OpMode
{

    /* Declare OpMode members. */
    HardwareRegister robot       = new HardwareRegister(); // use the class created to define a Pushbot's hardware

    boolean sweeperToggle = false;

    // could also use HardwarePushbotMatrix class.

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "This robot has been initiated lol");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start()
    {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double throttle;
        double direction;
        double right;
        double left;


        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        throttle = -gamepad1.left_stick_y;
        direction = gamepad1.left_stick_x;
        left = throttle + direction;
        right = throttle - direction;
        right = scaleInput(right);
        left = scaleInput(left);
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);

        // Toggle for sweeper motor
        if (gamepad2.a) {
            sweeperToggle = !sweeperToggle;
        }

        // Hold down to run the launcher motor
        if (gamepad2.b) {
            robot.launchMotor.setPower(1);
        }

        // Sets power for sweeperMotor based on the toggle boolean
        if (sweeperToggle) {
            robot.sweeperMotor.setPower(1);
        } else {
            robot.sweeperMotor.setPower(0);
        }




        // Send telemetry message to signify robot running;
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        updateTelemetry(telemetry);
    }


    /**
     * DT's scaling stuff
     */


    public double scaleInput(double dVal) {
            double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
        // get the corresponding index for the scaleInput array. int index = (int) (dVal * 16.0);
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16; }
        double dScale = 0.0; if (dVal < 0) {
            dScale = -scaleArray[index]; } else {
            dScale = scaleArray[index];
        }
        return dScale; }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
