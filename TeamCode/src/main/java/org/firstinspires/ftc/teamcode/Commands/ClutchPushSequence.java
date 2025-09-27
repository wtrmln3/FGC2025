package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Clutch;
import org.firstinspires.ftc.teamcode.Subsystems.Push;

public class ClutchPushSequence extends SequentialCommandGroup {
    public ClutchPushSequence(Clutch clutch, Push push) {
        addCommands(
                // 1. Clutches open slightly
                new InstantCommand(() -> clutch.setPosition(0.1)),
                new WaitUntilCommand(() -> Math.abs(clutch.getPosition() - 0.1) < 0.05),

                // 2. Push extends fully
                new InstantCommand(push::open),
                new WaitUntilCommand(() -> Math.abs(push.getPosition() - 1.0) < 0.05),

                // 3. Clutches open fully
                new InstantCommand(clutch::open),
                new WaitUntilCommand(() -> Math.abs(clutch.getPosition() - 1.0) < 0.05),

                // 4. Clutches close slightly
                new InstantCommand(() -> clutch.setPosition(0.1)),
                new WaitUntilCommand(() -> Math.abs(clutch.getPosition() - 0.1) < 0.05),

                // 5. Push retracts
                new InstantCommand(push::close),
                new WaitUntilCommand(() -> Math.abs(push.getPosition() - 0.0) < 0.05)
        );
    }
}
