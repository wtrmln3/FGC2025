package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Push;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Push Tester")
public class PushTester extends CommandOpMode {
    private Push push;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        push = new Push(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(push::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(push::close);
    }


}
