package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Clutch Tester")
public class ClutchTester extends CommandOpMode {
    private Clutch clutch;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        clutch = new Clutch(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(clutch::open);
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(clutch::close);
    }
}
