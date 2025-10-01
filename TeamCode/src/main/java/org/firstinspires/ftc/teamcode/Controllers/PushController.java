package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Subsystems.Clutch;

public class PushController {

    public PushController(Push push, Clutch clutch, Gamepad gamepad, CommandScheduler scheduler) {
        GamepadEx gamepadEx = new GamepadEx(gamepad);

        new Trigger(() -> gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.5)
                .whileActiveContinuous(push::open);

        new Trigger(() -> gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.5)
                .whileActiveContinuous(push::close);


    }
}
