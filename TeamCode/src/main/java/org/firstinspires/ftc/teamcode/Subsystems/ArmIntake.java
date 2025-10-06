/*package org.firstinspires.ftc.teamcode.Subsystems;

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
    private int targetPosition;

    public ArmIntake(HardwareMap hardwareMap) {
        take_1 = hardwareMap.get(CRServo.class, "take_1");
        take_2 = hardwareMap.get(CRServo.class, "take_2");
        door = hardwareMap.get(DcMotorEx.class, "door");

        take_1.setDirection(CRServo.Direction.REVERSE);
        take_2.setDirection(CRServo.Direction.FORWARD);

        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pid = new PIDController(kP, kI, kD);
        targetPosition = door.getCurrentPosition();

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
*/


package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmIntake extends SubsystemBase {

    // --- Hardware ---
    private final DcMotorEx armMotor;
    private final CRServo intakeLeft;
    private final CRServo intakeRight;

    // --- PID coefficients ---
    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1; // feedforward bias

    // --- PID state ---
    private double integralSum = 0.0;
    private double lastError = 0.0;
    private final ElapsedTime timer = new ElapsedTime();

    private static final double INTEGRAL_LIMIT = 50.0;
    private static final int STABLE_THRESHOLD = 8;

    // --- Target position ---
    private int targetPosition = 0;

    // --- Intake control constants ---
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

    // ---------------------------
    // Intake control
    // ---------------------------
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

    // ---------------------------
    // Arm control
    // ---------------------------
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

    // ---------------------------
    // PID loop
    // ---------------------------
    @Override
    public void periodic() {
        double deltaTime = Math.max(timer.seconds(), 0.001);
        timer.reset();

        double currentPosition = armMotor.getCurrentPosition();
        double error = targetPosition - currentPosition;

        // --- Integral anti-windup ---
        if (Math.abs(error) < STABLE_THRESHOLD) {
            integralSum = 0;
        } else {
            integralSum = clamp(integralSum + error * deltaTime, -INTEGRAL_LIMIT, INTEGRAL_LIMIT);
        }

        double derivative = (error - lastError) / deltaTime;

        // --- PID + feedforward ---
        double output = (kP * error) + (kI * integralSum) + (kD * derivative);

        // Feedforward helps overcome gravity or friction
        output += Math.signum(error) * kF;

        // Apply small hold power when near target
        if (Math.abs(error) < STABLE_THRESHOLD) {
            output = HOLD_POWER * Math.signum(error);
        }

        armMotor.setPower(clamp(output, -1.0, 1.0));
        lastError = error;
    }

    // ---------------------------
    // Utility methods
    // ---------------------------
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}

