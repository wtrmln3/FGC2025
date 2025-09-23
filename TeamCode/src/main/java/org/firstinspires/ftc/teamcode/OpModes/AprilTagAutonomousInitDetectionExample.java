package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@TeleOp
public class AprilTagAutonomousInitDetectionExample extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    double tagsize = 0.166;

    int LEFT = 101;
    int RIGHT = 102;
    int MIDDLE = 103;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
        if (hardwareMap == null) {
            telemetry.addLine("Ошибка: hardwareMap равен null!");
            telemetry.update();
            return;
        }

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
        if (webcam == null) {
            telemetry.addLine("Ошибка: Веб-камера 'Webcam 1' не найдена в hardwareMap!");
            telemetry.update();
            return;
        }

        camera = OpenCvCameraFactory.getInstance().createWebcam(webcam, cameraMonitorViewId);
        if (camera == null) {
            telemetry.addLine("Ошибка: Не удалось создать экземпляр камеры!");
            telemetry.update();
            return;
        }

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        if (aprilTagDetectionPipeline == null) {
            telemetry.addLine("Ошибка: Не удалось создать AprilTagDetectionPipeline!");
            telemetry.update();
            return;
        }

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
                telemetry.addLine("Камера успешно запущена!");
                telemetry.update();
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.addLine("Ошибка открытия камеры: " + errorCode);
                telemetry.update();
            }
        });

        telemetry.setMsTransmissionInterval(50);

        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections != null && currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections)
                {
                    if (tag.id == LEFT || tag.id == RIGHT || tag.id == MIDDLE)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if (tagFound)
                {
                    telemetry.addLine("Тег найден!\n\nДанные о местоположении:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Тег не найден :(");
                    if (tagOfInterest == null)
                    {
                        telemetry.addLine("(Тег никогда не был обнаружен)");
                    }
                    else
                    {
                        telemetry.addLine("\nТег был обнаружен ранее; последнее местоположение:");
                        tagToTelemetry(tagOfInterest);
                    }
                }
            }
            else
            {
                telemetry.addLine("Тег не найден :(");
                if (tagOfInterest == null)
                {
                    telemetry.addLine("(Тег никогда не был обнаружен)");
                }
                else
                {
                    telemetry.addLine("\nТег был обнаружен ранее; последнее местоположение:");
                    tagToTelemetry(tagOfInterest);
                }
            }

            telemetry.update();
            sleep(20);
        }

        if (tagOfInterest != null)
        {
            telemetry.addLine("Снимок тега:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("Снимок тега недоступен, он не был обнаружен во время инициализации :(");
            telemetry.update();
        }

        while (opModeIsActive()) { sleep(20); }
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        if (detection == null) {
            telemetry.addLine("Ошибка: Обнаружение равно null!");
            return;
        }

        Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        telemetry.addLine(String.format("\nОбнаружен тег ID=%d", detection.id));
        telemetry.addLine(String.format("Смещение X: %.2f футов", detection.pose.x * FEET_PER_METER));
        telemetry.addLine(String.format("Смещение Y: %.2f футов", detection.pose.y * FEET_PER_METER));
        telemetry.addLine(String.format("Смещение Z: %.2f футов", detection.pose.z * FEET_PER_METER));
        telemetry.addLine(String.format("Поворот (Yaw): %.2f градусов", rot.firstAngle));
        telemetry.addLine(String.format("Поворот (Pitch): %.2f градусов", rot.secondAngle));
        telemetry.addLine(String.format("Поворот (Roll): %.2f градусов", rot.thirdAngle));
    }
}