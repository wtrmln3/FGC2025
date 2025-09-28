package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Commands.DriveCommand;

public class DriveController {

    private final Drivetrain drive;
    private final GamepadEx gamepadEx;

    public DriveController(Drivetrain drive, Gamepad gamepad) {
        this.drive = drive;
        this.gamepadEx = new GamepadEx(gamepad);

        setupControls();
    }

    private void setupControls() {
        // Default drive command
        drive.setDefaultCommand(
                new DriveCommand(
                        drive,
                        () -> {
                            double y = -gamepadEx.getLeftY();
                            return Math.abs(y) > 0.05 ? y : 0.0;
                        },
                        () -> {
                            double y = -gamepadEx.getRightY();
                            return Math.abs(y) > 0.05 ? y : 0.0;
                        }
                )
        );

        // Slow mode toggle
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(drive::switchSlowMode);
    }
}
