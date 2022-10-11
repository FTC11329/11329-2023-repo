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

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotConfig;

import org.firstinspires.ftc.teamcode.subsystems.ComputerVision;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.DiOpMode;

import java.lang.reflect.InvocationTargetException;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.openftc.easyopencv.OpenCvCameraFactory;

public abstract class OpModeBase extends DiOpMode {
    public abstract void InstallLower() throws IllegalAccessException, InvocationTargetException, InstantiationException;

    public abstract RobotSide GetSide();

    @Override
    public void Install() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //this declares the motors and classes allowed to be injected with the @Inject() function in all future OpModes (both teleop and autonomouse)
        //If a certin motor or class is only used in teleop then you can "bindInstance()" in the TeleopBase script
        //the .withId() determines the name used to get the motor or clas in teh @Inject(id=x) function
        Container.bindInstance(telemetry);

        Container.bindInstance(GetSide());


        /*Container.bindInstance(
                        OpenCvCameraFactory
                                .getInstance()
                                .createWebcam(
                                        hardwareMap.get(WebcamName.class, "scannerCam"),
                                        hardwareMap.appContext.getResources()
                                                .getIdentifier(
                                                        "cameraMonitorViewId",
                                                        "id",
                                                        hardwareMap.appContext.getPackageName()
                                                )))
                .withId("webcam");*/

        Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Drivetrain.frontLeftMotorName)).withId("frontLeftMotor");
        Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Drivetrain.frontRightMotorName)).withId("frontRightMotor");
        Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Drivetrain.backLeftMotorName)).withId("backLeftMotor");
        Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Drivetrain.backRightMotorName)).withId("backRightMotor");

        //Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Slides.leftSlideMotor)).withId("leftSlideMotor");
        //Container.bindInstance(hardwareMap.get(DcMotorEx.class, RobotConfig.Slides.rightSlideMotor)).withId("rightSlideMotor");
        //Container.bind(Drivetrain.class).asSingle();
        Container.bind(Slides.class).asSingle();
        //Container.bind(ComputerVision.class).asSingle();
        Container.bind(Drivetrain.class).asSingle();
        InstallLower();
    }
}
