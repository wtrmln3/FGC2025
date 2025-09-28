package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Commands.ClutchPushSequence;
import com.arcrobotics.ftclib.command.CommandScheduler;

public class ClutchPushController {

    public ClutchPushController(Clutch clutch, Push push, Gamepad gamepad) {
        GamepadEx gamepadEx = new GamepadEx(gamepad);

        // DPAD_RIGHT triggers ClutchPushSequence
        new Trigger(() -> gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT))
                .whenActive(() -> CommandScheduler.getInstance().schedule(
                        new ClutchPushSequence(clutch, push)
                ));
    }
}
