package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive extends SubsystemBase {
    DcMotorEx frontLeft;
    DcMotorEx frontBack;
    DcMotorEx backLeft;
    DcMotorEx backRight;

    public Drive(HardwareMap hmap) {
        frontLeft = hmap.get(DcMotorEx.class, "frontLeft");
        frontBack = hmap.get(DcMotorEx.class, "frontBack");
        backLeft = hmap.get(DcMotorEx.class, "backLeft");
        backRight = hmap.get(DcMotorEx.class, "backRight");
    }
}
