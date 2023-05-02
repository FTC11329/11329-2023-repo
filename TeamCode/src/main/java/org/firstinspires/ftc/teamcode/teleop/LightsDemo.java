package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.LED;


@TeleOp(name = "Lights Demo")
public class LightsDemo extends LinearOpMode {
    private DcMotor lightMotor;
    private ElapsedTime elapsedTime;

    @Override
    public void runOpMode() throws InterruptedException {
        lightMotor = hardwareMap.get(DcMotorEx.class, RobotConfig.LED.LEDName);
        double manualPower = 0;

        elapsedTime = new ElapsedTime();
        elapsedTime.reset();

        lightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        lightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        waitForStart();
        while (opModeIsActive()) {
            double power = LED.getPower(elapsedTime.seconds(), RobotConfig.LED.defaultLEDEffect) * RobotConfig.LED.MaxLightPower;

            telemetry.addData("Current effect", RobotConfig.LED.defaultLEDEffect);
            telemetry.addData("Current LED power", power);
            telemetry.update();
            if (gamepad1.b){
                manualPower = ((gamepad1.right_trigger - gamepad1.left_trigger) * 0.05) + manualPower;
                lightMotor.setPower(manualPower);
            } else {
                lightMotor.setPower(power);
                manualPower = power;
            }
        }
    }
}
