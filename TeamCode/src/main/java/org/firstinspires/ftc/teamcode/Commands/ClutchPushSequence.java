package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Push;

public class ClutchPushSequence extends SequentialCommandGroup {
    public ClutchPushSequence(Clutch clutch, Push push) {
        addCommands(
                new InstantCommand(() -> clutch.setPosition(0.9)),
                new WaitCommand(300),   // wait 300 ms for servo to finish

                new InstantCommand(push::open),
                new WaitCommand(300),

                new InstantCommand(clutch::close),
                new WaitCommand(300),

                new InstantCommand(() -> clutch.setPosition(0.9)),
                new WaitCommand(300),

                new InstantCommand(push::close),
                new WaitCommand(300),

                new InstantCommand(clutch::close),
                new WaitCommand(300)
        );

    }
}
