package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hang extends SubsystemBase {
    private DcMotor lift_1;
    private DcMotor lift_2;

    public Hang(HardwareMap hardwareMap) {
        lift_1 = hardwareMap.dcMotor.get("lift_1");
        lift_2 = hardwareMap.dcMotor.get("lift_2");

        lift_1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift_2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power){
        lift_1.setPower(power);
        lift_2.setPower(power);
    }

    public void stop(){
        setPower(0);
    }

}