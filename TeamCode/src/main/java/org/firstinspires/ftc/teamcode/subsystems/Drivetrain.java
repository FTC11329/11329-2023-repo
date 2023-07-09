package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Drivetrain implements DiInterfaces.IInitializable, DiInterfaces.IDisposable {
    @DiContainer.Inject()
    Telemetry telemetry;

    @DiContainer.Inject(id = "frontLeftMotor")
    public DcMotorEx frontLeftMotor;

    @DiContainer.Inject(id = "frontRightMotor")
    public DcMotorEx frontRightMotor;

    @DiContainer.Inject(id = "backLeftMotor")
    public DcMotorEx backLeftMotor;

    @DiContainer.Inject(id = "backRightMotor")
    public DcMotorEx backRightMotor;

    @Override
    public void onInitialize() {
        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        backRightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void onDispose() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void MecanumDrive(double vertical, double horizontal, double clockwise, double maxSpeed) {
        frontRightMotor.setPower((-clockwise + horizontal + vertical) * maxSpeed * RobotConfig.Drivetrain.frontLeftMotorMultiplier);
        backRightMotor.setPower((-clockwise - horizontal + vertical) * maxSpeed * RobotConfig.Drivetrain.frontRightMotorMultiplier);
        frontLeftMotor.setPower(-(-clockwise + horizontal - vertical) * maxSpeed * RobotConfig.Drivetrain.backLeftMotorMultiplier);
        backLeftMotor.setPower(-(-clockwise - horizontal - vertical) * maxSpeed * RobotConfig.Drivetrain.backRightMotorMultiplier);
    }

    public double powerCurve(double stickInput) {
        if (stickInput > 1) {
            return 1;
        } else if (stickInput < -1) {
            return -1;
        }
        return 1 / (1 + Math.pow(-5 * stickInput - 3, 6));
    }

}
