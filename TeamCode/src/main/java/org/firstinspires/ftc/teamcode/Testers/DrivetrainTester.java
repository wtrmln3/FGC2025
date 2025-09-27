package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Drivetrain Tester")
public class DrivetrainTester extends CommandOpMode {
    private Drivetrain drivetrain;

    @Override
    public void initialize() {
        drivetrain = new Drivetrain(hardwareMap);
    }

    @Override
    public void run() {
        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;
        drivetrain.tankDrive(left, right);
    }
}
