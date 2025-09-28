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
                new InstantCommand(() -> clutch.setPosition(0.1)),
                new WaitCommand(500), // wait 0.5 seconds for servo to reach

                new InstantCommand(push::open),
                new WaitCommand(500),

                new InstantCommand(clutch::close),
                new WaitCommand(500),

                new InstantCommand(() -> clutch.setPosition(0.1)),
                new WaitCommand(500),

                new InstantCommand(push::close),
                new WaitCommand(500),

                new InstantCommand(clutch::close),
                new WaitCommand(500)
        );

    }
}
