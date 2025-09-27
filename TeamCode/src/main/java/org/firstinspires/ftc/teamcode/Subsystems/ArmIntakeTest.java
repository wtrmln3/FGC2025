package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntakeTest extends SubsystemBase {
    private CRServo take1, take2;
    private DcMotor door;
    private Servo clutch1, clutch2;

    // PID stuff
    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime timer = new ElapsedTime();

    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1;
    private static final double INTEGRAL_LIMIT = 50.0;

    private int targetPosition = 0;

    private final double CLUTCH_INITIAL = 0.0;
    private final double CLUTCH_EXTENDED = 1.0;

    public ArmIntakeTest(HardwareMap hardwareMap) {
        take1 = hardwareMap.get(CRServo.class, "take_1");
        take2 = hardwareMap.get(CRServo.class, "take_2");
        door = hardwareMap.get(DcMotor.class, "door");
        clutch1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch2 = hardwareMap.get(Servo.class, "clutch_2");

        clutch1.setDirection(Servo.Direction.REVERSE);
        take1.setDirection(CRServo.Direction.REVERSE);

        door.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        door.setDirection(DcMotor.Direction.REVERSE);

        clutch1.setPosition(CLUTCH_INITIAL);
        clutch2.setPosition(CLUTCH_INITIAL);

        timer.reset();
    }

    // Intake controls
    public void intakeForward() {
        take1.setPower(1.0);
        take2.setPower(1.0);
    }

    public void intakeReverse() {
        take1.setPower(-1.0);
        take2.setPower(-1.0);
    }

    public void stopIntake() {
        take1.setPower(0.0);
        take2.setPower(0.0);
    }

    // Clutch controls
    public void extendClutch() {
        clutch1.setPosition(CLUTCH_EXTENDED);
        clutch2.setPosition(CLUTCH_EXTENDED);
    }

    public void retractClutch() {
        clutch1.setPosition(CLUTCH_INITIAL);
        clutch2.setPosition(CLUTCH_INITIAL);
    }

    // Door controls
    public void setDoorTarget(int target) {
        targetPosition = target;
    }

    @Override
    public void periodic() {
        // PID loop
        double deltaTime = timer.seconds();
        if (deltaTime < 0.001) deltaTime = 0.001;

        double currentPos = door.getCurrentPosition();
        double error = targetPosition - currentPos;

        integralSum += error * deltaTime;
        integralSum = Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integralSum));

        double derivative = (error - lastError) / deltaTime;
        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + kF;

        output = Math.max(-1.0, Math.min(1.0, output));
        door.setPower(output);

        lastError = error;
        timer.reset();
    }
}
