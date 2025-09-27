package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Intake Tester")
public class IntakeTester extends CommandOpMode {
    private Intake intake;
    private GamepadEx gamepadEx1;

    @Override
    public void initialize() {
        intake = new Intake(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);

        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(() -> intake.setPower(1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(() -> intake.setPower(-1));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(intake::stop);
    }
}
