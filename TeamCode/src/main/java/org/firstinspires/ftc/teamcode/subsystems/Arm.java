package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Arm implements DiInterfaces.IInitializable, DiInterfaces.ITickable, DiInterfaces.IDisposable {

    /**
     * !!! ---------- MESSAGE TO SEAN ---------- !!!
     * Please learn to properly modularize your code
     * and don't rely on subsystems being called in
     * loops to run logic, I created ITickable for a
     * reason.
     *                                       - Hazel
     * !!! ------------------------------------- !!!
     */

    @DiContainer.Inject(id = "arm")
    public DcMotorEx arm;
    @DiContainer.Inject()
    Telemetry telemetry;

    double targetPosition = 0;
    double power;

    @Override
    public void onInitialize() {
        arm.setTargetPosition(0);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        arm.setPower(RobotConfig.Arm.armPower);
    }

    @Override
    public void onTick() {
        targetPosition += power * RobotConfig.Arm.armSpeed;
        arm.setTargetPosition((int) targetPosition);
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
        telemetry.addData("Arm Power", power);
    }

    @Override
    public void onDispose() {
        arm.setPower(0);
    }
}
