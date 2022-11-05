package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotConfig;

public class Arm implements DiInterfaces.IInitializable, DiInterfaces.IDisposable {

    @DiContainer.Inject(id="armServo1")
    public Servo arm1;
    @DiContainer.Inject(id="armServo2")
    public Servo arm2;


    @Override
    public void onInitialize() {

    }
    public void setArmSpeed(double armSpeed){
        arm1.setPosition(arm1.getPosition()+ RobotConfig.Arm.armSpeed*armSpeed);
        arm2.setPosition(arm2.getPosition()- RobotConfig.Arm.armSpeed*armSpeed);
    }
    public void setPos(double pos) {
    arm1.setPosition(pos);
    arm2.setPosition(pos);
    }
    @Override
    public void onDispose() {

    }
}
