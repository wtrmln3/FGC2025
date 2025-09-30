package org.firstinspires.ftc.teamcode.Commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DetectTagCommand extends CommandBase {
    private final Vision vision;
    private final Telemetry telemetry;
    private final Gamepad gamepad;
    private boolean hasRumbled;

    public DetectTagCommand(Vision vision, Telemetry telemetry, Gamepad gamepad) {
        this.vision = vision;
        this.telemetry = telemetry;
        this.gamepad = gamepad;

        addRequirements(vision);
    }

    @Override
    public void initialize() {
        telemetry.addLine("DetectTagCommand started");
        telemetry.update();
        hasRumbled = false;
    }

    @Override
    public void execute() {
        AprilTagDetection detection = vision.getFirstDetection();
        if (detection != null) {
            telemetry.addLine("Tag detected with ID: " + detection.id);
            gamepad.rumble(30);

            if (detection.ftcPose != null) {
                telemetry.addData("Bearing", detection.ftcPose.bearing);

                // Haptic feedback when nearly aligned
                if (!hasRumbled || Math.abs(detection.ftcPose.bearing) < 180) {
                    gamepad1.rumble(1.0, 1.0, 500);  // correct rumble call
                    hasRumbled = true;
                }
            } else {
                telemetry.addLine("Pose data unavailable");
            }

            telemetry.update();
        } else {
            telemetry.addLine("No tag detected");
            telemetry.update();
        }
    }


    @Override
    public void end(boolean interrupted) {
        telemetry.addLine("DetectTagCommand ended");
        telemetry.update();
    }

    @Override
    public boolean isFinished() {
        return false; // runs until button is released
    }
}
