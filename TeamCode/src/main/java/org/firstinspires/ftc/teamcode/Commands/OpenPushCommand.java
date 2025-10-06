package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Subsystems.Clutch;

public class OpenPushCommand extends SequentialCommandGroup {
    public OpenPushCommand(Push push, Clutch clutch) {
        addRequirements(push, clutch);

        addCommands(
                // Step 1: Open clutch slightly (0.9)
                new InstantCommand(() -> clutch.setPosition(0.9), clutch),
                new WaitCommand(500), // wait 400ms for servo to finish moving

                // Step 2: Open push
                new InstantCommand(push::open, push),
                new WaitCommand(700),

                // Step 3: Close clutch again
                new InstantCommand(clutch::close, clutch),
                new WaitCommand(500)
        );
    }
}
