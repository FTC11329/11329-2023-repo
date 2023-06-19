package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.utilities.CustomPID;

public class Arm implements DiInterfaces.IInitializable, DiInterfaces.ITickable, DiInterfaces.IDisposable {

    /**
     * !!! ---------- MESSAGE TO SEAN ---------- !!!
     * Please learn to properly modularize your code
     * and don't rely on subsystems being called in
     * loops to run logic, I created ITickable for a
     * reason.
     * - Hazel
     * !!! ------------------------------------- !!!
     */

    //How do you make your messages green? - Allen
    /** nvm found out how */

    @DiContainer.Inject(id = "arm")
    public DcMotorEx arm;
    @DiContainer.Inject()
    Telemetry telemetry;

    double targetPosition = 0;
    double power;

    double zeroAngle = 205;
    //zero angle = arm being strait out
    //455 straight UP
    double tickToDegrees = 0.3854389722;
    private CustomPID armPID;

    @Override
    public void onInitialize() {
        arm.setTargetPosition(0);

        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //arm.setPower(GlacierDrive.armPower);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        armPID = new CustomPID(DriveConstants.P, DriveConstants.I, DriveConstants.D, DriveConstants.F, DriveConstants.minPow);
        //armPID = new CustomPID(RobotConfig.Arm.kp, RobotConfig.Arm.ki, RobotConfig.Arm.kd, RobotConfig.Arm.kf);
    }

    @Override
    public void onTick() {
        targetPosition += power * RobotConfig.Arm.armSpeed;
        targetPosition = Math.min(Math.max(targetPosition, RobotConfig.Arm.minArmPosition), RobotConfig.Arm.maxArmPosition);
        armPID.setTargetPosition(targetPosition);
        double armPow = armPID.getPIDfOutput(arm.getCurrentPosition(), (arm.getCurrentPosition() - zeroAngle) * tickToDegrees);
        arm.setPower(armPow);
        //double currentPosition = arm.getCurrentPosition();
    }


    public void toPosition(int pos) {
        targetPosition = pos;
        power = 0;
    }

    public void setPower(double armSpeed) {
        power = armSpeed;
    }

    public void displayToTelemetry() {
        telemetry.addData("Arm Position", arm.getCurrentPosition());
        telemetry.addData("Arm Target Position", targetPosition);
        telemetry.addData("Arm Estimate Angle:", (arm.getCurrentPosition() - zeroAngle) * tickToDegrees);
    }

    @Override
    public void onDispose() {
        arm.setPower(0);
    }
}
