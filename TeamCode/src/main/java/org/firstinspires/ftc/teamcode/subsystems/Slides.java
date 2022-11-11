package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.javadi.DiContainer;
import org.firstinspires.ftc.teamcode.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.jfinite.StateMachine;
import org.firstinspires.ftc.teamcode.teleop.TeleopState;

public class Slides implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id = "leftSlideMotor")
    DcMotorEx leftSlideMotor;
    @DiContainer.Inject(id = "rightSlideMotor")
    DcMotorEx rightSlideMotor;
    //@DiContainer.Inject()
    //StateMachine stateMachine;

    public void moveSlides(double speed) {
        leftSlideMotor.setPower(speed);
        rightSlideMotor.setPower(speed);
    }

    public void goToPosition(int position) {
        leftSlideMotor.setTargetPosition(position);
        rightSlideMotor.setTargetPosition(position);
    }


    @Override
    public void onInitialize() {
        leftSlideMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightSlideMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void onDispose() {
        leftSlideMotor.setPower(0);
        rightSlideMotor.setPower(0);
    }
}
