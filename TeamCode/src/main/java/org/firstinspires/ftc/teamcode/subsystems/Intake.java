package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake extends SubsystemBase {
    DcMotorEx intake;

    public Intake(HardwareMap hmap) {
        intake = hmap.get(DcMotorEx.class, "intake");

        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intake.setDirection(DcMotorEx.Direction.FORWARD); //change direction as needed
    }

    /**
     * Sets the power of the intake
     * @param power A value between [-1, 1] that can be converted from voltage: ±(desired volts)/currentVoltage
     */
    public void setPower(double power) {
        intake.setPower(power);
    }

    /**
     * A method/function that is called repeatedly when the robot is initialized.
     * Useful for tracking telemetry or logged data values.
     */
    @Override
    public void periodic() {

    }
}
