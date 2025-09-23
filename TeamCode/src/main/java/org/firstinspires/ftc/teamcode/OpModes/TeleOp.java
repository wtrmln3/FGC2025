package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.TakeServos;


@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOp extends LinearOpMode{
    @Override
    public void runOpMode(){
        Drivetrain drive = new Drivetrain(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Clutch clutch = new Clutch(hardwareMap);
        TakeServos takeServos = new TakeServos(hardwareMap);
        Lift lift = new Lift(hardwareMap);

        GamepadEx gamepadEx1 = new GamepadEx(gamepad1);
        GamepadEx gamepadEx2 = new GamepadEx(gamepad2);

        waitForStart();

        while(opModeIsActive()){
            double leftPower = -gamepad1.left_stick_y;
            double rightPower = gamepad1.right_stick_y;
            drive.tankDrive(leftPower, rightPower);

            if(gamepadEx1.getButton(GamepadKeys.Button.A)){
                intake.setPower(1.0);
            }else if(gamepadEx1.getButton(GamepadKeys.Button.B)){
                intake.setPower(-1.0);
            }else{
                intake.stop();
            }

            if (gamepadEx2.getButton(GamepadKeys.Button.Y)) {
                lift.setPower(1.0);         // up
            } else if (gamepadEx2.getButton(GamepadKeys.Button.A)) {
                lift.setPower(-1.0);        // down
            } else {
                lift.stop();
            }

            if (gamepadEx2.getButton(GamepadKeys.Button.X)) {
                takeServos.setPower(1.0);   // forward
            } else if (gamepadEx2.getButton(GamepadKeys.Button.B)) {
                takeServos.setPower(-1.0);  // reverse
            } else {
                takeServos.stop();
            }

            if (gamepadEx1.getButton(GamepadKeys.Button.DPAD_DOWN)) {
                drive.switchSlowMode();     // toggle slow mode
            }

        }


    }
}
