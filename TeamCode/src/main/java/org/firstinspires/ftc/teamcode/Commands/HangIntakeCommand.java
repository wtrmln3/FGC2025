package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.HangIntake;

public class HangIntakeCommand extends CommandBase {
    private final HangIntake subsystem;
    private final double power;

    public HangIntakeCommand(HangIntake subsystem, double power) {
        this.subsystem = subsystem;
        this.power = power;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.setPower(power);  // run all 3 motors
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();  // stop all 3 motors
    }

    @Override
    public boolean isFinished() {
        return false; // keep running while button is held
    }
}
