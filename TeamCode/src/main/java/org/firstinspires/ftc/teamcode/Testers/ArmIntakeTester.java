package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ArmIntakeTest;

@TeleOp(name="Arm Intake Tester")
public class ArmIntakeTester extends CommandOpMode {
    private ArmIntakeTest armIntake;
    private GamepadEx gamepadEx2;

    @Override
    public void initialize() {
        armIntake = new ArmIntakeTest(hardwareMap);
        gamepadEx2 = new GamepadEx(gamepad2);

        // Intake
        gamepadEx2.getGamepadButton(GamepadKeys.Button.X).whileHeld(armIntake::intakeForward);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whileHeld(armIntake::intakeReverse);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.A).whenPressed(armIntake::stopIntake);

        // Clutch
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(armIntake::extendClutch);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(armIntake::retractClutch);

        // Door PID
        gamepadEx2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(() -> armIntake.setDoorTarget(500));
        gamepadEx2.getGamepadButton(GamepadKeys.Button.B).whenPressed(() -> armIntake.setDoorTarget(0));
    }
}
