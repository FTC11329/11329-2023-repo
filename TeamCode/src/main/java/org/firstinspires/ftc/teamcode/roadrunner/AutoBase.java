package org.firstinspires.ftc.teamcode.roadrunner;

import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.utilities.OpModeBase;

import java.lang.reflect.InvocationTargetException;

public abstract class AutoBase extends OpModeBase {
    //this is the base auto class for which to build autos ontop of
    // you can bind classes and hardware in the InstallLower() function if they are needed in future autos


    public abstract void Run();

    @Override
    public void InstallLower() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        GlacierDrive sampleMecanumDrive = new GlacierDrive(hardwareMap);

        Container.bindInstance(sampleMecanumDrive);
    }

    //the start function is called at the beginning of the program ie. when the play button is pressed and only runs ONCE
    @Override
    public void start() {
        Run();
    }

}
