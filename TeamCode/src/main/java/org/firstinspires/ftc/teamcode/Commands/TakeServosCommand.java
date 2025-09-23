package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.TakeServos;

public class TakeServosCommand extends CommandBase{
    private TakeServos servo;
    private double power;

    public TakeServosCommand(TakeServos servo, double power){
        this.servo = servo;
        this.power = power;

        addRequirements(servo);
    }

    @Override
    public void initialize(){
        servo.setPower(power);
    }

    @Override
    public void end(boolean interrupted){
        servo.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}