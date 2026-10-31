package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drive extends SubsystemBase {
    DcMotorEx frontLeft;
    DcMotorEx frontRight;
    DcMotorEx backLeft;
    DcMotorEx backRight;

    //Constants useful for tuning individual drivetrain motor powers
    public double frontLeftTune = 1;
    public double backLeftTune = 1;
    public double frontRightTune = 1;
    public double backRightTune = 1;

    //To account for inconsistencies in battery voltages potentially
    //giving drivetrain motors too much or too little power
    private final double voltageCalculation;

    /**
     * The Inertial Measurement Unit (IMU) built into the REV Control Hub.
     * Mainly measures roll, pitch, and yaw values (although it isn't accurate
     * enough in the grand scheme of things).
     */
    IMU imu;

    public Drive(HardwareMap hmap) {
        frontLeft = hmap.get(DcMotorEx.class, "frontLeft");
        frontRight = hmap.get(DcMotorEx.class, "frontBack");
        backLeft = hmap.get(DcMotorEx.class, "backLeft");
        backRight = hmap.get(DcMotorEx.class, "backRight");
        imu = hmap.get(IMU.class, "imu");

        voltageCalculation = hmap.voltageSensor.get("Control Hub").getVoltage();

        //Ensures that motors brake and don't coast when there is no
        //power being applied to them
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //Experiment with your drivetrain to figure out which motor(s) need to be reversed
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);


        //Change these directions as needed
        //Logo facing direction is the direction the logo of your control hub is facing
        //USB facing direction is the direction the USB port of your control hub is facing
        IMU.Parameters imuParameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.initialize(imuParameters);
    }

    /**
     * @param xInput x input (positive right negative left)
     * @param rotInput rotational input (positive clockwise negative counterclockwise)
     * @param yInput y input (positive forwards negative backwards)
     * @param slow true means slower false means not slower
     * Drives the robot relative to itself (forward on the joystick is always forward on the robot)
     */
    public void driveRobot(double xInput, double yInput, double rotInput, boolean slow) {
        double denominator = Math.max(Math.abs(rotInput) + Math.abs(xInput) + Math.abs(yInput), 1);
        double flPower = (slow ? 3 : 12) * ((-rotInput + xInput + yInput) / denominator);
        double frPower = (slow ? 3 : 12) * ((-rotInput - xInput - yInput) / denominator);
        double blPower = (slow ? 3 : 12) * ((-rotInput - xInput + yInput) / denominator);
        double brPower = (slow ? 3 : 12) * ((-rotInput + xInput - yInput) / denominator);

        frontLeft.setPower((frontLeftTune * flPower) / voltageCalculation);
        backLeft.setPower((backLeftTune * blPower) / voltageCalculation);
        frontRight.setPower((frontRightTune * frPower) / voltageCalculation);
        backRight.setPower((backRightTune * brPower) / voltageCalculation);
    }

    /**
     * @param xInput x input (positive right negative left)
     * @param rotInput rotational input (positive clockwise negative counterclockwise)
     * @param yInput y input (positive forwards negative backwards)
     * @param slow true means slower false means not slower
     * Drives the robot relative to the field (forward on the joystick is forward on the field)
     */
    public void driveField(double xInput, double yInput, double rotInput, boolean slow) {
        double botHeading = getAngle();

        double rotX = xInput * Math.cos(-botHeading) - yInput * Math.sin(-botHeading);
        double rotY = xInput * Math.sin(-botHeading) + yInput * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotInput), 1);
        double frontLeftPower = (slow ? 3 : 12) * (rotY + rotX + rotInput) / denominator;
        double backLeftPower = (slow ? 3 : 12) * (rotY - rotX + rotInput) / denominator;
        double frontRightPower = (slow ? 3 : 12) * (rotY - rotX - rotInput) / denominator;
        double backRightPower = (slow ? 3 : 12) * (rotY + rotX - rotInput) / denominator;

        frontLeft.setPower((frontLeftTune * frontLeftPower) / voltageCalculation);
        backLeft.setPower((backLeftTune * backLeftPower) / voltageCalculation);
        frontRight.setPower((frontRightTune * frontRightPower) / voltageCalculation);
        backRight.setPower((backRightTune * backRightPower) / voltageCalculation);
    }

    /**
     *
     * @return Drivetrain angle in degrees
     */
    public double getAngle() {
        return this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    /**
     * Resets the orientation (yaw) of the IMU and thus the robot.
     */
    public void resetIMU() {
        imu.resetYaw();
    }

    /**
     * A method/function that is called repeatedly when the robot is initialized.
     * Useful for tracking telemetry or logged data values.
     */
    @Override
    public void periodic() {

    }
}
