package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slides implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id = "leftSlideMotor")
    DcMotorEx leftSlideMotor;
    @DiContainer.Inject(id = "rightSlideMotor")
    DcMotorEx rightSlideMotor;
    @DiContainer.Inject()
    Telemetry telemetry;

    DcMotor.RunMode currentRunmode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;

    public void setMode(DcMotorEx.RunMode runMode) {
        if (runMode != currentRunmode) {
            currentRunmode = runMode;
            leftSlideMotor.setMode(runMode);
            rightSlideMotor.setMode(runMode);
            if (runMode == DcMotor.RunMode.RUN_TO_POSITION) {
                leftSlideMotor.setPower(0.8);
                rightSlideMotor.setPower(0.8);
            }
        }
    }

    public void toPosition(int pos) {
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlideMotor.setTargetPosition(pos);
        rightSlideMotor.setTargetPosition(pos);
    }

    public void displayToTelemetry() {
        telemetry.addData("Left Slide", leftSlideMotor.getCurrentPosition());
        telemetry.addData("Right Slide", rightSlideMotor.getCurrentPosition());
    }

    @Override
    public void onInitialize() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        zero();
        setTargetPositionTolerance(100);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTargetPositionTolerance(int tolerance) {
        leftSlideMotor.setTargetPositionTolerance(tolerance);
        rightSlideMotor.setTargetPositionTolerance(tolerance);

    }

    public void zero() {
        leftSlideMotor.setTargetPosition(0);
        rightSlideMotor.setTargetPosition(0);
    }

    @Override
    public void onDispose() {
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlideMotor.setPower(0);
        rightSlideMotor.setPower(0);
    }
}
