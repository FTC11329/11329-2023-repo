package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.*;
import com.fizzyapple12.javadi.DiInterfaces.IInitializable;

import java.lang.reflect.InvocationTargetException;

public abstract class DiOpMode extends OpMode {
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
