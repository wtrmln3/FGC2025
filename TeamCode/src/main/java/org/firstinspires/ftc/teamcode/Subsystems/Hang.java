package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hang extends SubsystemBase {
    private DcMotor hang_1;
    private DcMotor hang_2;

    public Hang(HardwareMap hardwareMap) {
        hang_1 = hardwareMap.dcMotor.get("lift_1");
        hang_2 = hardwareMap.dcMotor.get("lift_2");

        hang_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hang_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hang_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power){
        hang_1.setPower(power);
        hang_2.setPower(power);
    }

    public void stop(){
        setPower(0);
    }

}