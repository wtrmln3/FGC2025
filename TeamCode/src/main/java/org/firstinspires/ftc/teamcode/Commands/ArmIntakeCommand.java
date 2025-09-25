package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.ArmIntake;

public class ArmIntakeCommand extends CommandBase{
    private ArmIntake servo;
    private double power;

    public ArmIntakeCommand(ArmIntake servo, double power){
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