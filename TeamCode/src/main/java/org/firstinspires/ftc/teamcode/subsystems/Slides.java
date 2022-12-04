package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Slides  implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id="leftSlideMotor")
    DcMotorEx leftSlideMotor;
    @DiContainer.Inject(id="rightSlideMotor")
    DcMotorEx rightSlideMotor;

    public void moveSlides(double speed) {
        leftSlideMotor.setPower(speed);
        rightSlideMotor.setPower(speed);
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
