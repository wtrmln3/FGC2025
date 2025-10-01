package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.List;

public class Vision extends SubsystemBase {

    private final AprilTagProcessor tagProcessor;
    private final VisionPortal visionPortal;

    // Approx camera parameters (adjust to your webcam!)
    private static final double CAMERA_FOV_DEGREES = 60.0;   // horizontal FOV
    private static final double IMAGE_WIDTH_PIXELS = 640.0;  // typical width

    public Vision(HardwareMap hardwareMap) {
        tagProcessor = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // FTC default family
                .build();

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

    /** Estimate bearing in degrees from the center of the camera view */
    public Double getApproxBearing(AprilTagDetection detection) {
        if (detection == null) return null;

        double dx = detection.center.x - (IMAGE_WIDTH_PIXELS / 2.0);
        // scale to degrees based on camera FOV
        return (dx / (IMAGE_WIDTH_PIXELS / 2.0)) * (CAMERA_FOV_DEGREES / 2.0);
    }

    /** Closes the VisionPortal (frees the camera) */
    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

    /** Check if portal is streaming */
    public boolean isStreaming() {
        return visionPortal != null && visionPortal.getCameraState() == VisionPortal.CameraState.STREAMING;
    }
}
