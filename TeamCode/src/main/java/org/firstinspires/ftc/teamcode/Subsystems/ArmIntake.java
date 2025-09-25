package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.arcrobotics.ftclib.controller.PIDController;

public class ArmIntake extends SubsystemBase{
    private CRServo take_1;
    private CRServo take_2;
    private DcMotorEx door;
    private PIDController pid;

    private  double kP = 0.02, kI = 0.006, kD = 0.001;
    private int targetPosition = 0;

    public ArmIntake(HardwareMap hardwareMap) {
        take_1 = hardwareMap.get(CRServo.class, "take_1");
        take_2 = hardwareMap.get(CRServo.class, "take_2");
        door = hardwareMap.get(DcMotorEx.class, "door");

        take_1.setDirection(CRServo.Direction.REVERSE);
        take_2.setDirection(CRServo.Direction.FORWARD);

        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pid = new PIDController(kP, kI, kD);

        stop();
    }

    public void setPower(double power){
        power = Math.max(-1.0, Math.min(1.0, power));
        take_1.setPower(power);
        take_2.setPower(power);

    }

    public void stop(){
        setPower(0);
    }

    public void setDoorTarget(int targetTicks){
        targetPosition = targetTicks;
    }

    @Override
    public void periodic(){
        pid.setPID(kP, kI, kD);
        int currentPos = door.getCurrentPosition();

        if(Math.abs(targetPosition - currentPos) <= 10){
            door.setPower(0);
        }else{
            double power = pid.calculate(currentPos, targetPosition);
            power = Math.max(-1.0, Math.min(1.0, power));
            door.setPower(power);
        }

    }
}