package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.Subsystems.ArmIntake;

public class MoveDoorCommand extends InstantCommand {
    public MoveDoorCommand(ArmIntake armIntake, int targetTicks){
        super(() -> armIntake.setDoorTarget(targetTicks), armIntake);
    }
}
