package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Subsystems.Hang;
import com.arcrobotics.ftclib.command.CommandBase;

public class HangCommand extends CommandBase {
    private Hang lift;
    private double power;

    public HangCommand(Hang lift, double power){
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