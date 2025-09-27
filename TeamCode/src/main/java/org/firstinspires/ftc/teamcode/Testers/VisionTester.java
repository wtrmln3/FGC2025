package org.firstinspires.ftc.teamcode.Testers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Vision Tester")
public class VisionTester extends CommandOpMode {
    private Vision vision;

    @Override
    public void initialize() {
        vision = new Vision(hardwareMap);
    }

    @Override
    public void run() {
        AprilTagDetection detection = vision.getFirstDetection();
        if (detection != null) {
            telemetry.addData("AprilTag ID", detection.id);
        } else {
            telemetry.addData("Detection", "None");
        }
        telemetry.update();
    }
}
