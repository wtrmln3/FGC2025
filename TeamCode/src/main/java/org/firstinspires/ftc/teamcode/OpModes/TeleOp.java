package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends CommandOpMode {

    private Drivetrain drive;
    private Intake intake;
    private Hang hang;
    private ArmIntake armIntake;
    private Clutch clutch;
    private Vision vision;
    private Push push;

    private GamepadEx gamepadEx1;
    private GamepadEx gamepadEx2;

    @Override
    public void initialize() {
        //Subsystems
        drive = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        hang = new Hang(hardwareMap);
        armIntake = new ArmIntake(hardwareMap);
        clutch = new Clutch(hardwareMap);
        vision = new Vision(hardwareMap);
        push = new Push(hardwareMap);

        //Gamepads
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        //Default drive command
        drive.setDefaultCommand(
                new DriveCommand(
                        drive,
                        () -> -gamepadEx1.getLeftY(),
                        () -> -gamepadEx1.getRightY()
                )
        );

        // Bindings
        // Intake
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new IntakeCommand(intake, 1.0));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new IntakeCommand(intake, -1.0));

        // Slow mode toggle
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(drive::switchSlowMode);

        // Vision tag alignment
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new DetectTagCommand(drive, vision, telemetry, gamepad1));

        // Hang
        gamepadEx2.getGamepadButton(GamepadKeys.Button.Y)
                .whileHeld(new HangCommand(hang, 1.0));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new HangCommand(hang, -1.0));

        // ArmIntake
        gamepadEx2.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new ArmIntakeCommand(armIntake, 1.0));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new ArmIntakeCommand(armIntake, -1.0));

        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(new MoveDoorCommand(armIntake, 0));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(new MoveDoorCommand(armIntake, 500));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                        .whenPressed(new MoveDoorCommand(armIntake, 1000));

        // Clutch
        gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(clutch::open);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(clutch::close);


        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(push::open);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(push::close);




        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }

    @Override
    public void run() {
        super.run();

        if (vision != null && vision.getFirstDetection() != null) {
            telemetry.addData("AprilTag ID", vision.getFirstDetection().id);
        } else {
            telemetry.addLine("No AprilTag detected");
        }
        telemetry.update();
    }
}
