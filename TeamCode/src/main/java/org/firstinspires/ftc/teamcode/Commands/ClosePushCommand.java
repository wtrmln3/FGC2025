package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Push;
import org.firstinspires.ftc.teamcode.Subsystems.Clutch;

public class ClosePushCommand extends SequentialCommandGroup {

    public ClosePushCommand(Push push, Clutch clutch) {
        addRequirements(push, clutch);

        addCommands(
                // Step 1: Slightly open clutch to release push before closing
                new InstantCommand(() -> clutch.setPosition(0.9), clutch),
                new WaitCommand(500), // wait for servo movement

                // Step 2: Close push
                new InstantCommand(push::close, push),
                new WaitCommand(700),

                // Step 3: Fully close clutch again
                new InstantCommand(clutch::close, clutch),
                new WaitCommand(500)
        );
    }
}
