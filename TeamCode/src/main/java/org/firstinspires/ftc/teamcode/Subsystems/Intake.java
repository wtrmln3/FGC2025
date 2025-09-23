package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake extends SubsystemBase {
    private DcMotor drum_intake;

    public Intake(HardwareMap hardwareMap){
        drum_intake = hardwareMap.dcMotor.get("drum_intake");

        drum_intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drum_intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power){
        drum_intake.setPower(power);
    }

    public void stop(){
        setPower(0);
    }

}