package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Clutch {
    private Servo clutch_1;
    private Servo clutch_2;

    private final double CLOSED = 1.0;
    private final double OPEN = 0.0;

    // remember the last commanded target position
    private double targetPosition = CLOSED;

    public Clutch(HardwareMap hardwareMap){
        clutch_1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch_2 = hardwareMap.get(Servo.class, "clutch_2");

        clutch_1.setDirection(Servo.Direction.REVERSE);
        clutch_2.setDirection(Servo.Direction.FORWARD);

        close(); // start closed
    }

    public void setPosition(double position){
        position = Math.max(0.0, Math.min(1.0, position));
        targetPosition = position;
        clutch_1.setPosition(position);
        clutch_2.setPosition(position);
    }

    public void open(){
        setPosition(OPEN);
    }

    public void close(){
        setPosition(CLOSED);
    }

    public double getPosition() {
        // average of both servos
        return (clutch_1.getPosition() + clutch_2.getPosition()) / 2.0;
    }

    // ----------------------------
    // 🔽 New helper methods
    // ----------------------------

    /** Returns true if servos are at the last commanded target */
    public boolean atTarget() {
        return Math.abs(getPosition() - targetPosition) < 0.05;
    }

    public boolean isOpen() {
        return Math.abs(getPosition() - OPEN) < 0.05;
    }

    public boolean isClosed() {
        return Math.abs(getPosition() - CLOSED) < 0.05;
    }
}
