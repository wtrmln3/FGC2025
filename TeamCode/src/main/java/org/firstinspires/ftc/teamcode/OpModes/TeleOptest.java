package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.firstinspires.ftc.teamcode.Commands.*;
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
        gamepadEx1 = new GamepadEx(gamepad1);

        // Drivetrain
        //drivetrain = new Drivetrain(hardwareMap);


        // Intake
        /*intake = new Intake(hardwareMap);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new IntakeCommand(intake, 1.0));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new IntakeCommand(intake, -1.0));
        */

        //Arm Intake
        /*
        armIntake = new ArmIntakeTest(hardwareMap);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whileHeld(armIntake::intakeForward);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whileHeld(armIntake::intakeReverse);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(armIntake::stopIntake);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(armIntake::extendClutch);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(armIntake::retractClutch);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() -> armIntake.setDoorTarget(500));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(() -> armIntake.setDoorTarget(0));
        */

        //Clutch + Push
        /*
        clutch = new Clutch(hardwareMap);
        push = new Push(hardwareMap);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new ClutchPushSequence(clutch, push));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(clutch::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(clutch::close);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.START).whenPressed(push::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(push::close);
        */

        // Hang
        /*
        hang = new Hang(hardwareMap);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whileHeld(() -> hang.setPower(1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whileHeld(() -> hang.setPower(-1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(hang::stop);
        */

        // Vision
        /*
        vision = new Vision(hardwareMap);
        */

        telemetry.addLine("Tester Ready (uncomment subsystems to test)");
        telemetry.update();
    }

    @Override
    public void run() {
        // Drivetrain Tank Drive
        if (drivetrain != null) {
            drivetrain.tankDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        }

        // Vision Telemetry
        if (vision != null) {
            AprilTagDetection detection = vision.getFirstDetection();
            if (detection != null) telemetry.addData("AprilTag ID", detection.id);
            else telemetry.addData("Detection", "None");
        }

        telemetry.update();
    }
}
