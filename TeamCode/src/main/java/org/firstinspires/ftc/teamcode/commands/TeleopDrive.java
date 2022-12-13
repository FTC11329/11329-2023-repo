package org.firstinspires.ftc.teamcode.commands;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

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

    @DiContainer.Inject()
    public RobotSide side;

    public int slidePosition = 0;
    private double maxSpeed;
    public boolean reverse = false;

    @Override
    public void onTick() {
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double rotational = gamepad1.right_stick_x;
        double upPower = gamepad2.left_trigger;
        double downPower = gamepad2.right_trigger;
        boolean grab = gamepad2.right_bumper;
        double armPower = -gamepad2.left_stick_y;
        double wristPower = -gamepad2.right_stick_y;
        //doubles reading for probably more accuracy or something because java idk
        //slides.moveSlides(upPower - downPower);

        if (gamepad1.right_bumper) {
            maxSpeed = RobotConfig.Drivetrain.slowSpeed;
        } else {
            maxSpeed = RobotConfig.Drivetrain.fastSpeed;
        }

        drivetrain.MecanumDrive(vertical, horizontal, rotational, maxSpeed);

        slidePosition += (upPower - downPower) * RobotConfig.Slides.slidePower;
        slidePosition = Math.max(Math.min(slidePosition, RobotConfig.Slides.minSlidePosition), RobotConfig.Slides.maxSlidePosition);
        slides.toPosition(slidePosition);

        telemetry.addData("slidesTarget", slidePosition);

        arm.setPower(armPower);

        claw.setWristPower(wristPower);

        //if (slidePosition > RobotConfig.Claw.maxAutoGrabHeight && claw.isConePresent() && claw.getConeColor() == ((side == RobotSide.Red) ? Claw.ConeColor.RED : Claw.ConeColor.BLUE)) claw.ungrab();

        if (gamepad1.left_bumper) claw.ungrab();

        if (grab) claw.toggle();
        else claw.resetToggle();

        slides.displayToTelemetry();
        arm.displayToTelemetry();
        claw.displayToTelemetry();

        telemetry.update();

        // High
        if (gamepad2.dpad_up && !reverse) {
            slidePosition = RobotConfig.Presets.SlidesHigh;
            arm.toPosition(RobotConfig.Presets.Arm1High);
            claw.setPos(RobotConfig.Presets.WristPickup);
        }
        // High From Reverse Pickup
        if (gamepad2.dpad_up && reverse) {
            slidePosition = RobotConfig.Presets.SlidesHighFromRev;
            arm.toPosition(RobotConfig.Presets.Arm1HighFromRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
        }
        // High Reverse
        if (gamepad2.y) {
            slidePosition = RobotConfig.Presets.SlidesHighRev;
            arm.toPosition(RobotConfig.Presets.Arm1HighRev);
            claw.grab();
            claw.setPos(RobotConfig.Presets.WristPickup);
        }
        // Medium
        if (gamepad2.dpad_right && !reverse) {
            slidePosition = RobotConfig.Presets.SlidesMed;
            arm.toPosition(RobotConfig.Presets.Arm1Med);
            claw.setPos(RobotConfig.Presets.WristPickup);
        }
        // Medium From Reverse Pickup
        if (gamepad2.dpad_right && reverse) {
            slidePosition = RobotConfig.Presets.SlidesMedFromRev;
            arm.toPosition(RobotConfig.Presets.Arm1MedFromRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
        }
        //Medium Reverse
        if (gamepad2.b) {
            slidePosition = RobotConfig.Presets.SlidesMedRev;
            arm.toPosition(RobotConfig.Presets.Arm1MedRev);
            claw.grab();
            claw.setPos(RobotConfig.Presets.WristPickup);
        }
        // Low
        if (gamepad2.dpad_down) {
            slidePosition = RobotConfig.Presets.SlidesLow;
            arm.toPosition(RobotConfig.Presets.Arm1Low);
            claw.setPos(RobotConfig.Presets.WristPickup);
        }
        //Pickup
        if (gamepad2.a) {
            slidePosition = RobotConfig.Presets.SlidesPickup;
            arm.toPosition(RobotConfig.Presets.Arm1Pickup);
            claw.setPos(RobotConfig.Presets.WristPickup);
            claw.ungrab();
            reverse = false;
        }
        //Pickup Reverse
        if (gamepad2.x) {
            slidePosition = RobotConfig.Presets.SlidesPickupRev;
            arm.toPosition(RobotConfig.Presets.Arm1PickupRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
            claw.ungrab();
            reverse = true;
        }
    }

    @Override
    public void onInitialize() {
        //arm.gotoZero();
        //arm.toPosition(1);
    }
}
//Teagan was defenetly here. ya totally shure allen
/*
russian jets at N5.32234791 W2.3138492 CHEAP
192.168.4.382.930.171
russian jets for $5? satisfactory!
somebody once told me that the world was gonna roll me and i ain't the charpest tool in the shed and she was looking kinda dumb with a finger and a thumb in the shape of an l on her forehead and the years start coming and they don't stop coming and fed to the rures and you hit the ground ruunning. didnt make sense not to live for fun, your brain gets smart Iff you see this
 */