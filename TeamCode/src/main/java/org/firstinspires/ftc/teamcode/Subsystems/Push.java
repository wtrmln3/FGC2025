package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Push extends SubsystemBase {
    private final Servo push_1;
    private final Servo push_2;

    private static final double CLOSED = 1.0;
    private static final double OPEN = 0.0;

    public Push(HardwareMap hardwareMap) {
        push_1 = hardwareMap.get(Servo.class, "push_1");
        push_2 = hardwareMap.get(Servo.class, "push_2");

        push_1.setDirection(Servo.Direction.REVERSE);
        push_2.setDirection(Servo.Direction.FORWARD);


        close();
    }

    public void setPosition(double position) {
        position = Math.max(0.0, Math.min(1.0, position));
        push_1.setPosition(position);
        push_2.setPosition(position);
    }

    public void open() {
        setPosition(OPEN);
    }

    public void close() {
        setPosition(CLOSED);
    }

    // ---- Status checks ----
    public boolean isOpen() {
        return Math.abs(push_1.getPosition() - OPEN) < 0.05;
    }

    public boolean isClosed() {
        return Math.abs(push_1.getPosition() - CLOSED) < 0.05;
    }
}
