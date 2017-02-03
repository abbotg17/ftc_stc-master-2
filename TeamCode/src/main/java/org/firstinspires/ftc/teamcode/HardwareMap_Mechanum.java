package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left motor"
 * Motor channel:  Right drive motor:        "right motor"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareMap_Mechanum
{
    /* Public OpMode members. */
    public DcMotor  frontLeft       = null;
    public DcMotor  frontRight      = null;
    public DcMotor  backLeft        = null;
    public DcMotor  backRight       = null;
    public ColorSensor color1       = null;
  //  public ColorSensor color2       = null;
    public DcMotor  launcher        =null;
    public Servo backButton         = null;
    public Servo frontButton        = null;
    public Servo loaderServo        = null;
    public Servo gripServo        = null;
    public OpticalDistanceSensor odsSensor = null;



    /* Local OpMode members. */
    com.qualcomm.robotcore.hardware.HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareMap_Mechanum() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(com.qualcomm.robotcore.hardware.HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontLeft = hwMap.dcMotor.get("frontLeft");
        frontRight = hwMap.dcMotor.get("frontRight");
        backLeft = hwMap.dcMotor.get("backLeft");
        backRight = hwMap.dcMotor.get("backRight");
        launcher=hwMap.dcMotor.get("launcher");
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);


        // Set all motors to zero power
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        launcher.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //initialize color sensors
        color1 = hwMap.colorSensor.get("color1");

        // servos boy
        backButton = hwMap.servo.get("back");
        frontButton = hwMap.servo.get("frontButton");
        loaderServo = hwMap.servo.get("loaderServo");
        gripServo = hwMap.servo.get("grip");
        backButton.setPosition(.5);
        frontButton.setPosition(1);
        loaderServo.setPosition(0);
        gripServo.setPosition(0.45);
        odsSensor = hwMap.opticalDistanceSensor.get("ods");

        /*
        com.qualcomm.robotcore.hardware.I2cAddr shrek = new com.qualcomm.robotcore.hardware.I2cAddr(2);
        color2.setI2cAddress(shrek);
        */

        //com.qualcomm.robotcore.hardware.I2cAddr color1Addr = new com.qualcomm.robotcore.hardware.I2cAddr(0x3c);
       // color1.setI2cAddress(color1Addr);

       // com.qualcomm.robotcore.hardware.I2cAddr color2Addr = new com.qualcomm.robotcore.hardware.I2cAddr(0x3a);
       // color2.setI2cAddress(color2Addr);

        //com.qualcomm.robotcore.hardware.I2cAddr color3Addr = new com.qualcomm.robotcore.hardware.I2cAddr(0x3e);
        //color3.setI2cAddress(color2Addr);


    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }

}
