package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotConfig;

public class Claw implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id="clawServo")
    Servo closeClaw;


    @Override
    public void onInitialize() {
        closeClaw.scaleRange(RobotConfig.Claw.clawMinRange, RobotConfig.Claw.clawMaxRange);
    }

    public void closeClaw(){
        closeClaw.setPosition(0.0);
    }
    public void openClaw(){
        closeClaw.setPosition(1.0);
    }

    @Override
    public void onDispose() {

    }
}
