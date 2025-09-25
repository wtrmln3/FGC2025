package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake extends SubsystemBase {
    private DcMotor suck;

    public Intake(HardwareMap hardwareMap){
        suck = hardwareMap.dcMotor.get("drum_intake");

        suck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        suck.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power){
        suck.setPower(power);
    }

    public void stop(){
        setPower(0);
    }

}