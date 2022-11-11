package org.firstinspires.ftc.teamcode.utilities;

import org.firstinspires.ftc.teamcode.javadi.DiContainer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.lang.reflect.InvocationTargetException;

public abstract class DiOpMode extends OpMode {
    //This gives all future OpModes(teleop and autonomouse) a DiContainer which does some fancy stuff but basicly allows the @inject()
    //This also calls the tick, dispose, and initialize functions in classes that implement them once thoose classes have been bound via "bindInstance()"
    public DiContainer Container = new DiContainer();

    public abstract void Install() throws IllegalAccessException, InstantiationException, InvocationTargetException;

    @Override
    public void init() {
        try {
            Install();
        } catch (Exception e) {
            telemetry.log().add("Failed to Init, robot will die now");
            telemetry.log().add(e.toString());
        }

        Container.onInject();
    }

    @Override
    public void loop() {
        Container.onTick();
    }

    @Override
    public void stop() {
        Container.onDispose();
    }
}
