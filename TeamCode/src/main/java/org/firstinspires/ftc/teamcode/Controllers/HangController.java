package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.Hang;
import org.firstinspires.ftc.teamcode.Commands.HangCommand;

public class HangController {

    public HangController(Hang hang, Gamepad gamepad) {
        GamepadEx gamepadEx = new GamepadEx(gamepad);

        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new HangCommand(hang, 1.0));
        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new HangCommand(hang, -1.0));
    }
}
