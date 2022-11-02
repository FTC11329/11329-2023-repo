package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotConfig;

public class Claw implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id="clawServo")
    Servo closeClaw;
    @DiContainer.Inject(id="handServo1")
    public Servo handWave1;
    @DiContainer.Inject(id="handServo2")
    public Servo handWave2;

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
    public void setClawPower(double clawSpeed){
        handWave1.setPosition(handWave1.getPosition() + RobotConfig.Claw.handSpeed*clawSpeed);
        handWave2.setPosition(handWave2.getPosition() - RobotConfig.Claw.handSpeed*clawSpeed);
    }

    @Override
    public void onDispose() {

    }
}
