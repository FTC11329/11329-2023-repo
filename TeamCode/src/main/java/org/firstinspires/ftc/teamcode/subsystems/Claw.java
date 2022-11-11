package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.javadi.DiContainer;
import org.firstinspires.ftc.teamcode.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.jfinite.StateMachine;
import org.firstinspires.ftc.teamcode.teleop.TeleopState;

public class Claw implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id = "clawServo")
    Servo closeClaw;
    @DiContainer.Inject(id = "handServo1")
    public Servo handWave1;
    @DiContainer.Inject(id = "handServo2")
    public Servo handWave2;
    //@DiContainer.Inject()
    //public StateMachine stateMachine;

    @Override
    public void onInitialize() {
    }

    public void setClawPower(double armSpeed) {
        handWave1.setPosition(handWave1.getPosition() + RobotConfig.Claw.handSpeed * armSpeed);
        handWave2.setPosition(handWave2.getPosition() - RobotConfig.Claw.handSpeed * armSpeed);
    }

    public void setPos(double pos) {
        handWave2.setPosition(pos);
        handWave1.setPosition(pos);
    }

    public void grab() {
        closeClaw.setPosition(RobotConfig.Claw.closePos);
    }

    public void setHandPos(double pos) {
        handWave1.setPosition(pos);
        handWave2.setPosition(pos);
    }

    public void ungrab() {
        closeClaw.setPosition(RobotConfig.Claw.openPos);
    }

    @Override
    public void onDispose() {

    }
}
