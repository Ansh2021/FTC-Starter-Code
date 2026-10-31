package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Flywheel extends SubsystemBase {
    DcMotorEx flywheel;

    public Flywheel(HardwareMap hmap) {
        flywheel = hmap.get(DcMotorEx.class, "flywheel");

        flywheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        flywheel.setDirection(DcMotorEx.Direction.FORWARD);
    }

    /**
     * Sets the power of the flywheel
     * @param power A value between [-1, 1] that can be converted from voltage: ±(desired volts)/currentVoltage
     */
    public void setPower(double power) {
        flywheel.setPower(power);
    }

    /**
     * Sets the RPM (rotations per minute) of the flywheel
     * @param rpm The desired flywheel RPM
     */
    public void setRPM(double rpm) {
        //The ticks per revolution of your flywheel motor.
        //Can be found on the motor's website.
        //This example value is for the GoBilda 5203 Series Yellow Jacket Motor 6000 RPM
        //Its encoder resolution is 28 PPR (pulses per revolution)
        final double motorTicks = 28;
        double ticks = (rpm/60) * motorTicks;
        flywheel.setVelocity(ticks);
    }

    /**
     * A method/function that is called repeatedly when the robot is initialized.
     * Useful for tracking telemetry or logged data values.
     */
    @Override
    public void periodic() {

    }
}
