package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.DetectTagCommand;
import org.firstinspires.ftc.teamcode.Subsystems.*;
import org.firstinspires.ftc.teamcode.Controllers.*;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOpTest extends CommandOpMode {

    private Drivetrain drive;
    private Intake intake;
    private Hang hang;
    private ArmIntake armIntake;
    private Clutch clutch;
    private Push push;
    private Vision vision;
    private DriveController driveController;
    private IntakeController intakeController;
    private HangController hangController;
    private ArmIntakeController armIntakeController;
    private ClutchController clutchController;
    private PushController pushController;
    private VisionController visionController;
    private ClutchPushController clutchPushController;

    @Override
    public void initialize() {
        // Initialize subsystems
        drive = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        hang = new Hang(hardwareMap);
        armIntake = new ArmIntake(hardwareMap);
        clutch = new Clutch(hardwareMap);
        push = new Push(hardwareMap);
        vision = new Vision(hardwareMap);

        // Initialize controllers
        driveController = new DriveController(drive, gamepad1);
        intakeController = new IntakeController(intake, gamepad1);
        hangController = new HangController(hang, gamepad2);
        armIntakeController = new ArmIntakeController(armIntake, gamepad2);
        clutchController = new ClutchController(clutch, gamepad2);
        pushController = new PushController(push, clutch, gamepad2, CommandScheduler.getInstance());
        visionController = new VisionController(vision, telemetry, gamepad1);
        clutchPushController = new ClutchPushController(clutch, push, gamepad2);


        telemetry.addLine("TeleOp initialized");
        telemetry.update();
    }


    @Override
    public void run() {
        super.run();
    }
}
