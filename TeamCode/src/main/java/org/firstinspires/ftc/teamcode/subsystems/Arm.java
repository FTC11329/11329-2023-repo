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

    @DiContainer.Inject(id = "arm")
    public DcMotorEx arm;
    @DiContainer.Inject()
    Telemetry telemetry;


    double targetPosition = 0;
    double power;

    double zeroAngle = 199;
    //455 straight UP
    double tickToDegrees = 0.3854389722;
    private CustomPID armPID;
    //private double powerMod = GlacierDrive.armPowerSlow;
    int slidesOffset = 0;

    @Override
    public void onInitialize() {
        arm.setTargetPosition(0);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //arm.setPower(GlacierDrive.armPower);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        //arm.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(10,0.049988,0,0));
        //arm.setPositionPIDFCoefficients(GlacierDrive.PP);
        //arm.setVelocityPIDFCoefficients(GlacierDrive.VP, GlacierDrive.I, GlacierDrive.D, GlacierDrive.F);
        armPID = new CustomPID(RobotConfig.Arm.kp, RobotConfig.Arm.ki, RobotConfig.Arm.kd, RobotConfig.Arm.kf);
    }

    public void setPIDpower(boolean goingUp) {
        //arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        //if (goingUp) powerMod = GlacierDrive.armPower;//arm.setPower(RobotConfig.Arm.);
        //else powerMod = GlacierDrive.armPowerSlow;//arm.setPower(RobotConfig.Arm.armPowerSlow);

    }

    @Override
    public void onTick() {
        targetPosition += power * RobotConfig.Arm.armSpeed;
        targetPosition = Math.min(Math.max(targetPosition, RobotConfig.Arm.minArmPosition), RobotConfig.Arm.maxArmPosition);
        armPID.setTargetPosition(targetPosition);

        //telemetry.addData("ArmAngleEsitmation:",(arm.getCurrentPosition()-zeroAngle)*tickToDegrees );
        double armPow = armPID.getPIDfOutput(arm.getCurrentPosition(), (arm.getCurrentPosition() - zeroAngle) * tickToDegrees);
        telemetry.addData("ArmPower:", armPow);
        arm.setPower(armPow);
        //arm.setTargetPosition((int) targetPosition);
        //double currentPosition = arm.getCurrentPosition();
       /* if ((targetPosition > currentPosition && currentPosition < 480) || (targetPosition < currentPosition && currentPosition > 480)) {
            setPIDpower(true);
        } else {
            //low power
            setPIDpower(false);
        }*/

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
        //telemetry.addData("Arm Power", arm.getPower());

        telemetry.addData("PIDF : ", arm.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
    }

    @Override
    public void onDispose() {
        arm.setPower(0);
    }
}
