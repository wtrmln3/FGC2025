package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Clutch extends SubsystemBase {
    private final Servo clutch_1;
    private final Servo clutch_2;

    private static final double CLOSED = 1.0;
    private static final double OPEN = 0.0;

    public Clutch(HardwareMap hardwareMap) {
        clutch_1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch_2 = hardwareMap.get(Servo.class, "clutch_2");

        clutch_1.setDirection(Servo.Direction.REVERSE);
        clutch_2.setDirection(Servo.Direction.FORWARD);

        close();
    }

    public void setPosition(double position) {
        position = Math.max(0.0, Math.min(1.0, position));
        clutch_1.setPosition(position);
        clutch_2.setPosition(position);
    }

    public void open() {
        setPosition(OPEN);
    }

    public void close() {
        setPosition(CLOSED);
    }

    // ---- Status checks ----
    public boolean isOpen() {
        return Math.abs(clutch_1.getPosition() - OPEN) < 0.05;
    }

    public boolean isClosed() {
        return Math.abs(clutch_1.getPosition() - CLOSED) < 0.05;
    }

    public double getServoPosition(){
        return clutch_1.getPosition();
    }
}
