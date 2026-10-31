package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//This subsystem can be used if your magazine and intake are separated.
public class Magazine extends SubsystemBase {
    DcMotorEx magazine;

    public Magazine(HardwareMap hmap) {
        magazine = hmap.get(DcMotorEx.class, "magazine");

        magazine.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        magazine.setDirection(DcMotorEx.Direction.FORWARD);
    }

    /**
     * Sets the power of the magazine
     * @param power A value between [-1, 1] that can be converted from voltage: ±(desired volts)/currentVoltage
     */
    public void setPower(double power) {
        magazine.setPower(power);
    }

    /**
     * A method/function that is called repeatedly when the robot is initialized.
     * Useful for tracking telemetry or logged data values.
     */
    @Override
    public void periodic() {

    }
}
