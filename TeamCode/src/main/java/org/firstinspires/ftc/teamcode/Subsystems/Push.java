package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Push {
    private Servo push_1;
    private Servo push_2;

    private final double CLOSED = 0.0;
    private final double OPEN = 1.0;

    public Push(HardwareMap hardwareMap) {
            push_1 = hardwareMap.get(Servo.class, "push_1");
            push_2 = hardwareMap.get(Servo.class, "push_2");

            push_1.setDirection(Servo.Direction.REVERSE);
            push_2.setDirection(Servo.Direction.FORWARD);
    }

    public void setPosition(double position){
        position = Math.max(0.0, Math.min(1.0, position));
        push_1.setPosition(position);
        push_2.setPosition(position);
    }

    public void open(){
        setPosition(OPEN);
    }

    public void close(){
        setPosition(CLOSED);
    }

    public double getPosition() {
        // average of both servos
        return (push_1.getPosition() + push_2.getPosition()) / 2.0;
    }


}


