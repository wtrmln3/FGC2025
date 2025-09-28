package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommand;

public class IntakeController {

    public IntakeController(Intake intake, Gamepad gamepad) {
        GamepadEx gamepadEx = new GamepadEx(gamepad);

        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new IntakeCommand(intake, 1.0));
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new IntakeCommand(intake, -1.0));
    }
}
