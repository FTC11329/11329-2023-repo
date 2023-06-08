package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Slides implements DiInterfaces.IDisposable, DiInterfaces.IInitializable, DiInterfaces.ITickable {
    @DiContainer.Inject(id = "leftSlideMotor")
    DcMotorEx leftSlideMotor;
    @DiContainer.Inject(id = "rightSlideMotor")
    DcMotorEx rightSlideMotor;
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject(id = "leftSlideLimitSwitch")
    RevTouchSensor leftLimitSwitch;
    @DiContainer.Inject(id = "rightSlideLimitSwitch")
    RevTouchSensor rightLimitSwitch;


    DcMotor.RunMode currentRunmode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;

    boolean rightTriggerState = false;

    public boolean atPosition = false;

    public void setMode(DcMotorEx.RunMode runMode) {
        if (runMode != currentRunmode) {
            currentRunmode = runMode;
            leftSlideMotor.setMode(runMode);
            rightSlideMotor.setMode(runMode);
            if (runMode == DcMotor.RunMode.RUN_TO_POSITION) {
                leftSlideMotor.setPower(RobotConfig.Slides.automaticSlidePower);
                rightSlideMotor.setPower(1);
            }
        }
    }

    public void setTargetPosition(int pos) {
        pos =(int) (pos * RobotConfig.Arm.gearRatio);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlideMotor.setTargetPosition(pos);
        rightSlideMotor.setTargetPosition(pos);
    }


    public void displayToTelemetry() {
        telemetry.addData("Left Slide", leftSlideMotor.getCurrentPosition());
        telemetry.addData("Right Slide", rightSlideMotor.getCurrentPosition());
//        telemetry.addData("Slide PIDF Coefficients RUN_TO_POSITION", rightSlideMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
        telemetry.addData("Left Limit Switch", leftLimitSwitch.isPressed());
        telemetry.addData("Right Limit Switch", rightLimitSwitch.isPressed());
        telemetry.addData("At Positon", atPosition);
    }

    @Override
    public void onInitialize() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        zero();
        setTargetPositionTolerance(100);
        //setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void onTick() {
        if ((leftLimitSwitch.isPressed() || rightLimitSwitch.isPressed()) && rightTriggerState) {
            setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
            setTargetPosition(-1);
            setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(Math.abs(leftSlideMotor.getCurrentPosition()-leftSlideMotor.getTargetPosition())<50){
           atPosition=true;
        }

//        if (rightSlideMotor.getCurrentPosition() < rightSlideMotor.getTargetPosition() && rightLimitSwitch.isPressed()) {
//            setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
//            setTargetPosition(-1);
//        }
//        if (leftSlideMotor.getCurrentPosition() < leftSlideMotor.getTargetPosition() && leftLimitSwitch.isPressed()) {
//            setMode((DcMotor.RunMode.STOP_AND_RESET_ENCODER));
//            setTargetPosition(-1);
//        }
    }

    public void setRightTriggerState(boolean state) {
        rightTriggerState = state;
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
