package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ArmIntake;

public class ArmIntakeCommand extends CommandBase {

    private final ArmIntake armIntake;
    private final double power; // +1 intake, -1 reverse, 0 stop

    public ArmIntakeCommand(ArmIntake armIntake, double power) {
        this.armIntake = armIntake;
        this.power = power;
        addRequirements(armIntake);
    }

    @Override
    public void initialize() {
        if (power > 0) {
            armIntake.intakeOn();
        } else if (power < 0) {
            armIntake.intakeReverse();
        } else {
            armIntake.intakeOff();
        }
    }

    @Override
    public void end(boolean interrupted) {
        armIntake.intakeOff();
    }

    @Override
    public boolean isFinished() {
        return false; // Keep running while the button is held
    }
}
