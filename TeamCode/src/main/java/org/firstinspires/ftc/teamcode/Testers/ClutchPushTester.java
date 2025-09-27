package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Commands.ClutchPushSequence;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Clutch + Push Tester")
public class ClutchPushTester extends CommandOpMode {
    private Clutch clutch;
    private Push push;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        clutch = new Clutch(hardwareMap);
        push = new Push(hardwareMap);

        gamepadEx1 = new GamepadEx(gamepad1);


        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ClutchPushSequence(clutch, push));


        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenPressed(clutch::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(clutch::close);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(push::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(push::close);

        telemetry.addLine("Clutch + Push Tester Ready (Press A for sequence)");
        telemetry.update();
    }
}
