package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.List;

public class Vision extends SubsystemBase {

    private final AprilTagProcessor tagProcessor;
    private final VisionPortal visionPortal;

    // FTC field AprilTag black square size = 4 inches = 0.1016 meters
    private static final double TAG_SIZE_METERS = 0.1016;

    public Vision(HardwareMap hardwareMap) {
        tagProcessor = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // FTC default
                .build();

        // ✅ set tag size after build if builder doesn’t support it
        tagProcessor.(TAG_SIZE_METERS);

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(tagProcessor)
                .build();
    }

    /** Returns the list of all detected AprilTags this frame */
    public List<AprilTagDetection> getDetections() {
        return tagProcessor.getDetections();
    }

    /** Returns the first detected AprilTag, or null if none */
    public AprilTagDetection getFirstDetection() {
        List<AprilTagDetection> detections = getDetections();
        if (detections != null && !detections.isEmpty()) {
            return detections.get(0);
        }
        return null;
    }

    /** Closes the VisionPortal (frees the camera) */
    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

    /** Optional: check if portal is streaming */
    public boolean isStreaming() {
        return visionPortal != null && visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING;
    }
}
