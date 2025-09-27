package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Hang;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Hang Tester")
public class HangTester extends CommandOpMode {
    private Hang hang;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        hang = new Hang(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(() -> hang.setPower(1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(() -> hang.setPower(-1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(hang::stop);
    }
}
