package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Arm implements DiInterfaces.IInitializable, DiInterfaces.IDisposable {

    @DiContainer.Inject(id = "armServo1")
    public Servo arm1;
    @DiContainer.Inject(id = "armServo2")
    public Servo arm2;
    @DiContainer.Inject()
    Telemetry telemetry;

    @Override
    public void onInitialize() {}

    public void setArm1Pos(double pos) {
        arm2.setPosition(pos);
    }

    public void setArm2Pos(double pos) {
        arm1.setPosition(pos);
    }

    public void setArmSpeed(double armSpeed) {
        setPos(arm1.getPosition() + RobotConfig.Arm.armSpeed * armSpeed);
    }

    public void setPos(double pos) {
        arm1.setPosition(pos);
        arm2.setPosition(1.0 - pos);
    }

    public void displayToTelemetry() {
        telemetry.addData("Arm1", arm1.getPosition());
        telemetry.addData("Arm2", arm2.getPosition());
    }

    public void gotoZero() {
        setPos(1.0);
    }

    @Override
    public void onDispose() {

    }
}
