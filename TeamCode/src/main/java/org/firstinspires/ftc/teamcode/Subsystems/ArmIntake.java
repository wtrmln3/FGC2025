
package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntake extends SubsystemBase {

    private final DcMotorEx armMotor;
    private final CRServo intakeLeft;
    private final CRServo intakeRight;

    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1; // feedforward bias

    private double integralSum = 0.0;
    private double lastError = 0.0;
    private final ElapsedTime timer = new ElapsedTime();

    private static final double INTEGRAL_LIMIT = 50.0;
    private static final int STABLE_THRESHOLD = 8;

    private int targetPosition = 0;

    private static final double INTAKE_POWER = 1.0;
    private static final double HOLD_POWER = 0.05;

    public ArmIntake(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotorEx.class, "door");
        intakeLeft = hardwareMap.get(CRServo.class, "take_1");
        intakeRight = hardwareMap.get(CRServo.class, "take_2");

        intakeLeft.setDirection(CRServo.Direction.REVERSE);
        intakeRight.setDirection(CRServo.Direction.FORWARD);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timer.reset();
    }

    
    public void intakeOn() {
        intakeLeft.setPower(INTAKE_POWER);
        intakeRight.setPower(INTAKE_POWER);
    }

    public void intakeReverse() {
        intakeLeft.setPower(-INTAKE_POWER);
        intakeRight.setPower(-INTAKE_POWER);
    }

    public void intakeOff() {
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }

    
    public void setTargetPosition(int targetTicks) {
        this.targetPosition = targetTicks;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public int getCurrentPosition() {
        return armMotor.getCurrentPosition();
    }

    public void stop() {
        intakeOff();
        armMotor.setPower(0);
    }

    
    @Override
    public void periodic() {
        double deltaTime = Math.max(timer.seconds(), 0.001);
        timer.reset();

        double currentPosition = armMotor.getCurrentPosition();
        double error = targetPosition - currentPosition;

        if (Math.abs(error) < STABLE_THRESHOLD) {
            integralSum = 0;
        } else {
            integralSum = clamp(integralSum + error * deltaTime, -INTEGRAL_LIMIT, INTEGRAL_LIMIT);
        }

        double derivative = (error - lastError) / deltaTime;

        double output = (kP * error) + (kI * integralSum) + (kD * derivative);

        output += Math.signum(error) * kF;

        if (Math.abs(error) < STABLE_THRESHOLD) {
            output = HOLD_POWER * Math.signum(error);
        }

        armMotor.setPower(clamp(output, -1.0, 1.0));
        lastError = error;
    }

    
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}

