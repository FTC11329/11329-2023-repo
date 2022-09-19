package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotConfig;

import org.firstinspires.ftc.teamcode.utilities.DiOpMode;

import java.lang.reflect.InvocationTargetException;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
public abstract class OpModeBase extends DiOpMode {
    public abstract void InstallLower() throws IllegalAccessException, InvocationTargetException, InstantiationException;

    public abstract RobotSide GetSide();

    @Override
    public void Install() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Container.bindInstance(GetSide()); // works for bindinstance original "Container.BindInstance(GetSide());"

        Container.bind(Drivetrain.class).asSingle();
        InstallLower();
    }
}
