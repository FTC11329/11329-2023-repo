package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.utilities.CustomPID;

@TeleOp(name = "SlidesTest", group = "Area 51")
public class SlidesTest extends LinearOpMode {
    DcMotor leftSlideMotor;
    DcMotor rightSlideMotor;
    TouchSensor leftLimitSwitch;
    TouchSensor rightLimitSwitch;
    CustomPID slidesPID;

    @Override
    public void runOpMode() throws InterruptedException {
        slidesPID = new CustomPID(RobotConfig.Slides.kp, RobotConfig.Slides.ki, RobotConfig.Slides.kd, RobotConfig.Slides.kf);
        leftSlideMotor = hardwareMap.get(DcMotor.class, RobotConfig.Slides.leftSlideMotor);
        rightSlideMotor = hardwareMap.get(DcMotor.class, RobotConfig.Slides.rightSlideMotor);
        leftSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLimitSwitch = hardwareMap.get(TouchSensor.class, RobotConfig.Slides.leftLimitSwitch);
        rightLimitSwitch = hardwareMap.get(TouchSensor.class, RobotConfig.Slides.rightLimitSwitch);


        waitForStart();

        //setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlideMotor.setPower(0.7);
        rightSlideMotor.setPower(0.7);

        int targetPower;
        int targetPosition = 0;

        while (opModeIsActive()) {

            targetPosition = Range.clip(Math.round((-gamepad2.right_trigger + gamepad2.left_trigger) * 30) + targetPosition, -50, 2200);

            if ((leftLimitSwitch.isPressed() || rightLimitSwitch.isPressed()) && gamepad2.right_trigger == 0) {
                setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                targetPosition = 0;
                setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            double averagePosition = (rightSlideMotor.getCurrentPosition() + leftSlideMotor.getCurrentPosition()) / 2.;

            rightSlideMotor.setPower(slidesPID.getPIDfOutputNoAngle(averagePosition));
            leftSlideMotor.setPower(slidesPID.getPIDfOutputNoAngle(averagePosition));

            slidesPID.setTargetPosition(targetPosition);
            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("Left Position ", leftSlideMotor.getCurrentPosition());
            telemetry.addData("Right Position", rightSlideMotor.getCurrentPosition());
            telemetry.addData("Slide Power", slidesPID.getPIDfOutputNoAngle(averagePosition));

            telemetry.update();
        }

    }

    private void setMode(DcMotor.RunMode runMode) {
        leftSlideMotor.setMode(runMode);
        rightSlideMotor.setMode(runMode);

    }

    private void setTargetPosition(int pos) {
        leftSlideMotor.setTargetPosition(pos);
        rightSlideMotor.setTargetPosition(pos);
    }


}
