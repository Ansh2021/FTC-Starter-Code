package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Magazine;

@TeleOp(name = "Example TeleOp")
public class RunRobot extends LinearOpMode {
    Drive drive;
    Flywheel flywheel;
    Intake intake;
    Magazine magazine;

    public static boolean isFieldCentric = false;

    /**
     * The method running your robot during TeleOp. It is repeatedly called over and over again
     * for the duration of TeleOp.
     * @throws InterruptedException When the OpMode is stopped on the Driver Station
     */
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        flywheel = new Flywheel(hardwareMap);
        intake = new Intake(hardwareMap);
        magazine = new Magazine(hardwareMap);

        double voltageCalculation = hardwareMap.voltageSensor.get("Control Hub").getVoltage();


        drive.resetIMU(); //Resets the robot's yaw before initializing.

        //This method must be called in order for the robot to not immediately disable
        //after initialization. It just tells the OpMode to wait for the start button to be pressed.
        waitForStart();

        //Feel free to change any of the default controls given below
        //Remember that you have access to gamepad2 (the "copilot" or "codriver") and that
        //not all controls have to be given to gamepad1 (the pilot or driver)
        if(!isStopRequested()) {
            while (opModeIsActive()) {
                //You may need to flip some of the joystick signs around
                if(isFieldCentric) {
                    drive.driveField(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_bumper);
                } else {
                    drive.driveRobot(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.left_bumper);
                }

                //Resets the robot's yaw orientation
                if(gamepad1.start) {
                    drive.resetIMU();
                }

                //Sets the flywheel to a fixed power
                if(gamepad1.right_trigger_pressed) {
                    flywheel.setPower(7/voltageCalculation);
                }

                //Sets the intake to a fixed power
                if(gamepad1.left_trigger_pressed) {
                    intake.setPower(5/voltageCalculation);
                }
            }
        }
    }
}
