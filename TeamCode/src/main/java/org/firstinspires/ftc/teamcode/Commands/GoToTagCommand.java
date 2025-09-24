package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.Vision;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GoToTagCommand extends CommandBase{
    private Drivetrain drivetrain;
    private Vision vision;
    private Telemetry telemetry;
    private Gamepad gamepad;
    private boolean hasRumbled;

    public GoToTagCommand(Drivetrain drivetrain, Vision vision, Telemetry telemetry, Gamepad gamepad){
        this.drivetrain = drivetrain;
        this.vision = vision;
        this.telemetry = telemetry;
        this.gamepad = gamepad;

        addRequirements(drivetrain, vision);
    }

    @Override
    public void initialize(){
        telemetry.addLine("GoToTagCommand started");
        telemetry.update();
        vision.getDetections();
        hasRumbled = false;

    }

    @Override
    public void execute(){
        AprilTagDetection detection = vision.getFirstDetection();
        if(detection != null){
            telemetry.addLine("Tag detected with ID:" + detection.id);
            telemetry.update();

            if(!hasRumbled && Math.abs(detection.ftcPose.bearing) < 5){
                gamepad.rumble(500);
                hasRumbled = true;
            }
        }

    }

    @Override
    public void end(boolean interrupted){
        drivetrain.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
