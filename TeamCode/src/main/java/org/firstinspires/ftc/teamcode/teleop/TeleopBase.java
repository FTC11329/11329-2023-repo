package org.firstinspires.ftc.teamcode.teleop;


import org.firstinspires.ftc.teamcode.commands.TeleopDrive;
import org.firstinspires.ftc.teamcode.jfinite.StateMachine;
import org.firstinspires.ftc.teamcode.utilities.OpModeBase;

import java.lang.reflect.InvocationTargetException;

public abstract class TeleopBase extends OpModeBase {

    public static StateMachine<TeleopState> stateMachine = new StateMachine<>(TeleopState.ENTRY);

    @Override
    public void InstallLower() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Container.bindInstance(gamepad1).withId("gamepad1");
        Container.bindInstance(gamepad2).withId("gamepad2");

        Container.bindInstance(stateMachine);

        //Container.bindInstance(TeleopBlue.class).asSingle();
        Container.bind(TeleopDrive.class).asSingle();
        //Container.bind(TeleopArm.class).asSingle();

    }
}
