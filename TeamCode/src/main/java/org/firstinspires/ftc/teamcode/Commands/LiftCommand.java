package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import com.arcrobotics.ftclib.command.CommandBase;

public class LiftCommand extends CommandBase {
    private Lift lift;
    private double power;

    public LiftCommand(Lift lift, double power){
        this.lift = lift;
        this.power = power;

        addRequirements(lift);
    }

    @Override
    public void initialize(){
        lift.setPower(power);
    }

    @Override
    public void end(boolean interrupted){
        lift.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}