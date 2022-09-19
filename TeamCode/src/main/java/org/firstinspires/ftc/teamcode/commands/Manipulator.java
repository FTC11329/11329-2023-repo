package org.firstinspires.ftc.teamcode.commands;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Slides;

public class Manipulator implements DiInterfaces.ITickable {
    @DiContainer.Inject(id ="gamepad2")
    public Gamepad gamepad2;
    @DiContainer.Inject()
    public Slides viperSlides;
    @Override
    public void onTick() {
        viperSlides.moveSlidesWithPower(gamepad2.left_trigger);
    }
}
