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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Template: Linear OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class Driver_Mechanum_GuntherExperiment extends LinearOpMode {

    /**
     * Make some objects
     */
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();

    /**
     * Make some variables
     */
    private int orientation; // orientation in DEGREES relative to start
    private static double a = 0.2; // Standard drive speed
    


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Hello Motherfucker");
        telemetry.update();

        robot.init(hardwareMap);

        // reset orientation
        orientation = 0;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Orientation", orientation);
            telemetry.update();

            /**
             * Driving
             */

            if (orientation == 0) {
                // Driving forwards or backwards
                if (gamepad1.y) {
                    drive(a, a, a, a);
                } else if (gamepad1.a) {
                    drive(-a, -a, -a, -a);

                    // Strafing
                } else if (gamepad1.x) {
                    drive(-a, a, a, -a);
                } else if (gamepad1.b) {
                    drive(a, -a, -a, a);
                } /*
            } else if (orientation == 90) {
                if (gamepad1.dpad_up) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_down) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_left) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_right) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                }
            } else if (orientation == 180) {
                if (gamepad1.dpad_up) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_down) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_left) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_right) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                }
            } else if (orientation == 270) {
                if (gamepad1.dpad_up) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_down) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_left) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                } else if (gamepad1.dpad_right) {
                    robot.backLeft.setPower();
                    robot.backRight.setPower();
                    robot.frontRight.setPower();
                    robot.frontLeft.setPower();
                }
            } */ else {
                    robot.backLeft.setPower(0);
                    robot.backRight.setPower(0);
                    robot.frontRight.setPower(0);
                    robot.frontLeft.setPower(0);
                }


                /**
                 * Turning
                 */

                /*
                // rotate 90 degrees counterclockwise
                if (gamepad1.a) {
                    // encoderDrive to turn wheels here...
                    updateOrientation(false);
                }

                //rotate 90 degrees clockwise
                if (gamepad1.b) {
                    // encoderDrive to turn wheels here...
                    updateOrientation(true);
                }
                */


                idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
            }
        }
    }

    private void drive(double frontLeft, double frontRight, double backLeft, double backRight) {
        robot.frontLeft.setPower(frontLeft);
        robot.frontRight.setPower(frontRight);
        robot.backLeft.setPower(backLeft);
        robot.backRight.setPower(backRight);
    }

    private void updateOrientation(boolean isClockwise) {

        int currOrientation = orientation;

        // prevent moving into negative degrees
        if (currOrientation == 0 && !isClockwise) {
            orientation = 270;
        }

        // deal with zero degrees otherwise
        else if (currOrientation == 0 && isClockwise) {
            orientation = 90;
        }

        // if the current orientation is either 90, 180, or 270
        else if (currOrientation > 0 && currOrientation < 360) {
            if (isClockwise) {
                orientation += 90;
            } else if (!isClockwise) {
                orientation -= 90;
            }
        }

        // reset the orientation to zero if 90 was added to 270
        if (orientation == 360) {
            orientation = 0;

        }
    }



    
}
