package org.firstinspires.ftc.teamcode.commands;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class TeleopDrive implements DiInterfaces.ITickable, DiInterfaces.IInitializable {
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject()
    Drivetrain drivetrain;
    @DiContainer.Inject()
    Slides slides;
    @DiContainer.Inject(id = "gamepad1")
    public Gamepad gamepad1;
    @DiContainer.Inject(id = "gamepad2")
    public Gamepad gamepad2;
    @DiContainer.Inject()
    public Arm arm;
    @DiContainer.Inject()
    public Claw claw;

    public int slidePosition = 0;

    @Override
    public void onTick() {
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double rotational = gamepad1.right_stick_x;
        double upPower = gamepad2.left_trigger;
        double downPower = gamepad2.right_trigger;
        boolean grab = gamepad2.right_bumper;
        double armPower = -gamepad2.left_stick_y;
        double handPower = gamepad2.right_stick_x;
        //doubles reading for probably more accuracy or something because java idk
        //slides.moveSlides(upPower - downPower);

        slidePosition += (upPower - downPower) * 10;
        slides.toPosition(slidePosition);

        drivetrain.MecanumDrive(vertical, horizontal, rotational, 0.7);
        arm.setArmSpeed(armPower);
        claw.setClawPower(handPower);
        if (grab) claw.toggle();
        else claw.resetToggle();
        slides.displayToTelemetry();
        arm.displayToTelemetry();
        telemetry.update();
        // High
        if (gamepad2.dpad_left) {
            slidePosition = RobotConfig.Presets.SlidesHigh;
            arm.setPos(RobotConfig.Presets.Arm1High);
        }
        // High Reverse
        if (gamepad2.dpad_up) {
            slidePosition = RobotConfig.Presets.SlidesHighRev;
            arm.setPos(RobotConfig.Presets.Arm1HighRev);
        }
        // Medium
        if (gamepad1.dpad_right || gamepad1.x) {
            slidePosition = RobotConfig.Presets.SlidesMed;
            arm.setPos(RobotConfig.Presets.Arm1Med);
        }
        // Low
        if (gamepad1.dpad_down || gamepad1.y) {
            slidePosition = RobotConfig.Presets.SlidesPickup;
            arm.setPos(RobotConfig.Presets.Arm1Pickup);
        }
    }

    @Override
    public void onInitialize() {
//        arm.gotoZero();
        arm.setPos(1);
    }
}
//Teagan was defenetly here. ya totally shure allen
/*
russian jets at N5.32234791 W2.3138492 CHEAP
192.168.4.382.930.171
russian jets for $5? satisfactory!
somebody once told me that the world was gonna roll me and i ain't the charpest tool in the shed and she was looking kinda dumb with a finger and a thumb in the shape of an l on her forehead and the years start coming and they don't stop coming and fed to the rures and you hit the ground ruunning. didnt make sense not to live for fun, your brain gets smart Iff you see this
 */