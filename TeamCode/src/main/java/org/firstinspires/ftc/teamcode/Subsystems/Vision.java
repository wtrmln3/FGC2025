package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.List;

public class Vision extends SubsystemBase {
    private AprilTagProcessor tagProcessor;
    private VisionPortal visionPortal;

    public Vision(HardwareMap hardwareMap) {
        tagProcessor = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(tagProcessor)
                .build();
    }

    public List<AprilTagDetection> getDetections() {
        return tagProcessor.getDetections();
    }

    public AprilTagDetection getFirstDetection() {
        List<AprilTagDetection> detections = getDetections();
        if(detections != null && !detections.isEmpty()){
            return detections.get(0);
        }
        return null;
    }
}

