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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Template: Linear OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class Auto_Mechanum extends LinearOpMode {

    // Initialization
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap_Mechanum robot = new HardwareMap_Mechanum();
    static double COUNTS_PER_INCH = ((1120 * 1.015384615) / (4 * Math.PI)); //static double COUNTS_PER_INCH_L = ((420) / (4 * Math.PI));
    static double DRIVE_SPEED = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Let's Roll Motherfuckers"); // thanks darryl
        telemetry.update();

        robot.init(hardwareMap);
        robot.color1.enableLed(false);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // launch ball
        launch(-.25,5,50);
        launch(-.25,2.7,50);

        // load second ball
        robot.loaderServo.setPosition(1);
        sleep(1000);

        // launch second ball
        launch(-.25,3.2,50);

        // drive
        encoderDrive(.5, 35, 35, 35, 35);
        strafeLeft(1, 80);
        strafeLeft(1, 10);

        go(-.5);
        while (robot.odsSensor.getLightDetected()<.6) { idle(); }

        encoderDrive(DRIVE_SPEED, -7, -7, -7, -7);
        strafeLeft(1,6);
        go(1);

        // wait until white line
        while (robot.odsSensor.getLightDetected()<.6) { idle(); }
        go(0);
        strafeLeft(1,5);
        sleep(500);

        if (robot.color1.red()>=2)
        {
            robot.backButton.setPosition(1);
            sleep(500);
            robot.backButton.setPosition(.45);
            robot.backButton.setPosition(1);
            sleep(500);
            robot.backButton.setPosition(.45);
        }

        else
        {
            robot.frontButton.setPosition(0);
            sleep(500);
            robot.frontButton.setPosition(1);
            robot.frontButton.setPosition(0);
            sleep(500);
            robot.frontButton.setPosition(1);
        }
        sleep(750);
        encoderDrive (DRIVE_SPEED,2.0,2.0,2.0,2.0);// avoid detecting previous line
        strafeLeft(1, 20); // smash into wall
        sleep(100);
        go(1);


        // wait until white line
        while (robot.odsSensor.getLightDetected()<.6) {idle();}
        go(0);
       strafeLeft(1,15);


        if (robot.color1.red()>=2)
        {
            robot.backButton.setPosition(1);
            sleep(500);
            robot.backButton.setPosition(.45);
            robot.backButton.setPosition(1);
            sleep(500);
            robot.backButton.setPosition(.45);
        }
        else
        {
            robot.frontButton.setPosition(0);
            sleep(500);
            robot.frontButton.setPosition(1);
            robot.frontButton.setPosition(0);
            sleep(500);
            robot.frontButton.setPosition(1);
        }


       /* while (robot.color1.red()<2)
        {
            robot.frontButton.setPosition(1.1);
            sleep(2000);
            robot.frontButton.setPosition(.45);
        }
            */
        sleep(500);
        // move away from wall
        strafeRight(1, 65);
        encoderDrive(.75,-32,-32,-32,-32);
        // move back and park on ramp
       // encoderDrive(1,-73,-73,-73,-73);
       // encoderDrive(1,-8,8,-8,8);
       // encoderDrive(.75,-32,-32,-32,-32);
        //robot.gripServo.setPosition(2);
}









    /**
     * Driving helper methods
     */

    private void go(double speed) {
        robot.frontLeft.setPower(speed);
        robot.frontRight.setPower(speed);
        robot.backLeft.setPower(speed);
        robot.backRight.setPower(speed);
    }

    private void strafeLeft(double speed, double distance) throws InterruptedException {
        encoderDrive(speed, -distance, distance, distance, -distance);
    }

    private void strafeRight(double speed, double distance) throws InterruptedException {
        encoderDrive(speed, distance, -distance, -distance, distance);
    }
    public void launch(double speed, double rotations, double timeoutS)
            throws InterruptedException
    {
        int newTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {

            // Determine new target position, and pass to motor controller
            //newLeftTarget = robot.launchMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            //newRightTarget = robot.sweeperMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newTarget = robot.launcher.getCurrentPosition() + (int)(4*rotations * COUNTS_PER_INCH);
            robot.launcher.setTargetPosition(newTarget);

            // Turn On RUN_TO_POSITION
            robot.launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.launcher.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and the motor is running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    robot.launcher.isBusy())
            {

                // Display data on the driver station.
                telemetry.addData("newTarget", "Running to %7d",newTarget);
                telemetry.addData("getCurrentPosition", "Running at %7d", robot.launcher.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.launcher.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // optional pause after each move
            sleep(250);
        }
    }
    private void encoderDrive(double speed,
                             double leftInches, double rightInches, double bLeftInches, double bRightInches) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // flip motors


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.frontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newBackLeftTarget = robot.backLeft.getCurrentPosition() + (int)(bLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = robot.backRight.getCurrentPosition() + (int)(bRightInches * COUNTS_PER_INCH);


            robot.frontRight.setTargetPosition(newFrontRightTarget);
            robot.frontLeft.setTargetPosition(newFrontLeftTarget);
            robot.backRight.setTargetPosition(newBackRightTarget);
            robot.backLeft.setTargetPosition(newBackLeftTarget);

            // Turn On RUN_TO_POSITION
            robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeft.setPower(Math.abs(speed));
            robot.frontRight.setPower(Math.abs(speed));
            robot.backLeft.setPower(Math.abs(speed));
            robot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (robot.backRight.isBusy() && robot.backLeft.isBusy()
                            && robot.frontLeft.isBusy() && robot.frontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newFrontLeftTarget,  newFrontRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.frontLeft.getCurrentPosition(),
                        robot.frontRight.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontRight.setPower(0);
            robot.frontLeft.setPower(0);
            robot.backRight.setPower(0);
            robot.backLeft.setPower(0);

            // Turn off RUN_TO_POSITION
          /* robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
*/
            //Added by Gunther, should reset encoder values
           robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          /*  robot.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);*/
            sleep(250);   // optional pause after each move
        }



    }



    
}
