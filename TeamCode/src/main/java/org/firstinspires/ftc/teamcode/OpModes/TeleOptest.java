package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.ArmIntakeTest;
import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Hang;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.teamcode.Commands.ClutchPushSequence;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOptest")
public class TeleOptest extends CommandOpMode {

    // Subsystems
    private Drivetrain drivetrain;
    private ArmIntakeTest armIntake;
    private Clutch clutch;
    private Push push;
    private Hang hang;
    private Intake intake;
    private Vision vision;

    // Gamepad
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
        armIntake = new ArmIntakeTest(hardwareMap);
        clutch = new Clutch(hardwareMap);
        push = new Push(hardwareMap);
        hang = new Hang(hardwareMap);
        intake = new Intake(hardwareMap);
        vision = new Vision(hardwareMap);

        gamepadEx1 = new GamepadEx(gamepad1);

        //Drivetrain
        //in execute() default

        //Arm Intake
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whileHeld(armIntake::intakeForward);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whileHeld(armIntake::intakeReverse);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(armIntake::stopIntake);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(armIntake::extendClutch);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(armIntake::retractClutch);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() -> armIntake.setDoorTarget(500));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(() -> armIntake.setDoorTarget(0));

        //Clutch and Push
        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new ClutchPushSequence(clutch, push));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(clutch::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(clutch::close);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.START).whenPressed(push::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(push::close);

        //Hang
        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whileHeld(() -> hang.setPower(1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whileHeld(() -> hang.setPower(-1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(hang::stop);

        telemetry.addLine("Tester Ready");
        telemetry.update();
    }

    @Override
    public void run() {
        // Drivetrain Tank Drive
        drivetrain.tankDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

        // Intake triggers
        double intakePower = 0;
        if (gamepad1.right_trigger > 0.1) intakePower = 1;
        else if (gamepad1.left_trigger > 0.1) intakePower = -1;
        intake.setPower(intakePower);

        // Vision
        AprilTagDetection detection = vision.getFirstDetection();
        if (detection != null) telemetry.addData("AprilTag ID", detection.id);
        else telemetry.addData("Detection", "None");


        telemetry.update();
    }

}
