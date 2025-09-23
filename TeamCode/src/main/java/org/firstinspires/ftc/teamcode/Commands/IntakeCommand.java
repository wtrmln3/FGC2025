package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private Intake intake;
    private double power;

    public IntakeCommand(Intake intake, double power){
        this.intake = intake;
        this.power = power;

        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.setPower(power);
    }

    @Override
    public void end(boolean interrupted){
        intake.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}