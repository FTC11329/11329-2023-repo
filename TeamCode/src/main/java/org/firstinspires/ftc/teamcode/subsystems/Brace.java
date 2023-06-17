package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.commands.TeleopDrive;

public class Brace implements DiInterfaces.IDisposable, DiInterfaces.IInitializable, DiInterfaces.ITickable {
    @DiContainer.Inject(id = "bracePlate")
    Servo bracePlateServo;
    @DiContainer.Inject(id = "LineBreak")
    RevTouchSensor LineBreak;
    @DiContainer.Inject()
    Telemetry telemetry;

    boolean rightTriggerState = false;

    public boolean atPole = false;
    public boolean activated = false;


    public void setTargetPosition(int pos) {}

    public void brace() {
        if (!TeleopDrive.callBeacon() && !TeleopDrive.auto) {
            bracePlateServo.setPosition(0.41);//0.44
            activated = true;
        }
    }

    public void unbrace() {
        bracePlateServo.setPosition(0.71);//0.74
        activated = false;
    }

    public void displayToTelemetry() {
        telemetry.addData("Brace Plate Pos", bracePlateServo.getPosition());
        telemetry.addData("LineBreak", LineBreak.isPressed());
    }

    @Override
    public void onInitialize() {
        bracePlateServo.setPosition(0);
        unbrace();
    }
    @Override
    public void onTick() {
        atPole = LineBreak.isPressed();
    }

    public void setRightTriggerState(boolean state) {
        rightTriggerState = state;
    }

    @Override
    public void onDispose() {

    }
}
