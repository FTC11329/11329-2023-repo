package org.firstinspires.ftc.teamcode.commands;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Brace;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

public class TeleopDrive implements DiInterfaces.ITickable, DiInterfaces.IInitializable {
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
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject()
    Drivetrain drivetrain;
    @DiContainer.Inject()
    Slides slides;
    private double maxSpeed;
    @DiContainer.Inject()
    Brace brace;

    public int slidePosition = 0;
    public int tempSlidePosition = 0;
    public boolean reverse = false;
    public boolean preset = false;
    public static boolean beacon = false;
    public static boolean manualAdjust = false;
    public static boolean auto = false;
    private boolean releaseContorller = false;

    @Override
    public void onTick() {
        double vertical = (-gamepad1.left_stick_y) + gamepad1.right_trigger - gamepad1.left_trigger;
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
            maxSpeed = RobotConfig.Drivetrain.fastSpeed;
        } else {
            maxSpeed = RobotConfig.Drivetrain.slowSpeed;
        }
        if (gamepad2.left_stick_y > 0.1 || gamepad2.left_stick_y < -0.1 || gamepad2.left_trigger > 0.1 || gamepad2.right_trigger > 0.1) {
            manualAdjust = true;
        }

        drivetrain.MecanumDrive(vertical, horizontal, rotational, maxSpeed);

        slides.setRightTriggerState(gamepad2.right_trigger == 0);
        slidePosition += (upPower - downPower) * RobotConfig.Slides.manualSlidePower;
        if(slidePosition ==  RobotConfig.Presets.SlidesHighRev && claw.slidesOffset != 0){
            tempSlidePosition = slidePosition + claw.slidesOffset ;
        }
        else{
            tempSlidePosition = slidePosition + claw.slidesOffset;
        }

        tempSlidePosition = Math.max(Math.min(tempSlidePosition, RobotConfig.Slides.minSlidePosition), RobotConfig.Slides.maxSlidePosition);

        slides.setTargetPosition(tempSlidePosition);
//        telemetry.addData("slidesTarget", slidePosition);

        arm.setPower(armPower);

        claw.setWristPower(wristPower);

        //if (slidePosition > RobotConfig.Claw.maxAutoGrabHeight && claw.isConePresent() && claw.getConeColor() == ((side == RobotSide.Red) ? Claw.ConeColor.RED : Claw.ConeColor.BLUE)) claw.ungrab();

        if (gamepad1.left_bumper) claw.ungrab();

        if (grab) claw.toggle();
        else claw.resetToggle();

        if (gamepad2.right_stick_button){
            claw.halfGrab();
        }
        if (gamepad2.back) {
            if (releaseContorller == true) {
                claw.autoRelease = !claw.autoRelease;
                releaseContorller = false;
            }
        } else {
           if(releaseContorller == false){
               releaseContorller = true;
           }
        }


        slides.displayToTelemetry();
        arm.displayToTelemetry();
        claw.displayToTelemetry();
        brace.displayToTelemetry();

        telemetry.update();

        // High
        if (gamepad2.dpad_up && !reverse) {
            slidePosition = RobotConfig.Presets.SlidesHigh;
            arm.toPosition(RobotConfig.Presets.Arm1High);
            claw.setPos(RobotConfig.Presets.WristPickup);
            preset = true;
            claw.setPresetBool(true);
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // High From Reverse Pickup
        if (gamepad2.dpad_up && reverse) {
            slidePosition = RobotConfig.Presets.SlidesHighFromRev;
            arm.toPosition(RobotConfig.Presets.Arm1HighFromRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
            preset = true;
            claw.setPresetBool(true);
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // High Reverse
        if (gamepad2.y) {
            slidePosition = RobotConfig.Presets.SlidesHighRev;
            arm.toPosition(RobotConfig.Presets.Arm1HighRev);
            claw.grab();
            claw.setPos(RobotConfig.Presets.WristPickup);
            preset = true;
            claw.setPresetBool(true);
            brace.brace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // Medium
        if (gamepad2.dpad_right && !reverse) {
            slidePosition = RobotConfig.Presets.SlidesMed;
            arm.toPosition(RobotConfig.Presets.Arm1Med);
            claw.setPos(RobotConfig.Presets.WristPickup);
            preset = true;
            claw.setPresetBool(true);
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // Medium From Reverse Pickup
        if (gamepad2.dpad_right && reverse) {
            slidePosition = RobotConfig.Presets.SlidesMedFromRev;
            arm.toPosition(RobotConfig.Presets.Arm1MedFromRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
            preset = true;
            claw.setPresetBool(true);
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        //Medium Reverse
        if (gamepad2.b) {
            slidePosition = RobotConfig.Presets.SlidesMedRev;
            arm.toPosition(RobotConfig.Presets.Arm1MedRev);
            claw.grab();
            claw.setPos(RobotConfig.Presets.WristPickup);
            preset = true;
            claw.setPresetBool(true);
            brace.brace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // Low
        if (gamepad2.dpad_down) {
            slidePosition = RobotConfig.Presets.SlidesLow;
            arm.toPosition(RobotConfig.Presets.Arm1Low);
            claw.setPos(RobotConfig.Presets.WristPickup);
            preset = true;
            claw.setPresetBool(true);
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        // Low from Rev
        if (gamepad2.dpad_down && reverse) {
            slidePosition = RobotConfig.Presets.SlidesLowFromRev;
            arm.toPosition(RobotConfig.Presets.Arm1LowFromRev);
            claw.setPos(RobotConfig.Presets.WristPickupRev);
            claw.grab();
            preset = true;
            claw.setPresetBool(true);
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        //Pickup
        if (gamepad2.a) {
            slidePosition = RobotConfig.Presets.SlidesPickup;
            arm.toPosition(RobotConfig.Presets.Arm1Pickup);
            claw.setPos(RobotConfig.Presets.WristPickup);
            claw.ungrab();
            reverse = false;
            preset = false;
            claw.setPresetBool(false);
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        //Pickup Reverse
//        if (gamepad2.x) {
//            slidePosition = RobotConfig.Presets.SlidesPickupRev;
//            arm.toPosition(RobotConfig.Presets.Arm1PickupRev);
//            claw.setPos(RobotConfig.Presets.WristPickupRev);
//            claw.ungrab();
//            reverse = true;
//            preset = false;
//            claw.setPresetBool(false);
//        }
        //Beacon pickup
        if (gamepad2.x){
            claw.grab();
            brace.setBraceEnabled(false);
            beacon = true;
            claw.slidesOffset = 0;
            brace.unbrace();
        }
        //Reverse low
        if (gamepad2.dpad_left && !reverse) {
            arm.toPosition(RobotConfig.Presets.Arm1LowRev);
            slidePosition = RobotConfig.Presets.SlidesLowRev;
            claw.setPos(RobotConfig.Presets.WristLowRev);
            claw.grab();
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        //Pickup Fallen Cone
        if (gamepad1.a && !reverse) {
            slidePosition = RobotConfig.Presets.SlidesPickupFall;
            arm.toPosition(RobotConfig.Presets.Arm1PickupFall);
            claw.setPos(RobotConfig.Presets.WristPickupFall);
            claw.ungrab();
            preset = false;
            claw.setPresetBool(false);
            brace.unbrace();
            beacon = false;
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        //Drive Preset
        if (gamepad1.right_stick_button || gamepad2.left_bumper) {
            arm.toPosition(RobotConfig.Presets.Arm1Drive);
            slidePosition = RobotConfig.Presets.SlidesDrive;
            claw.setPos(RobotConfig.Wrist.startingPosition);
            brace.unbrace();
            manualAdjust = false;
            claw.slidesOffset = 0;
        }
        if (gamepad1.y) {
            slidePosition = -700;
            claw.slidesOffset = 0;
        }
        if (gamepad1.dpad_up) {
            brace.brace();
        }
        if (gamepad1.dpad_down) {
            brace.unbrace();
        }
        if (gamepad1.dpad_left) {
            drivetrain.backLeftMotor.setPower(0.3);
        }
        if (gamepad1.dpad_right) {
            drivetrain.backRightMotor.setPower(0.3);
        }
        if (gamepad1.x) {
            claw.setPos(RobotConfig.Claw.coneFlip);
        }
        if (gamepad1.b) {
            claw.setPos(RobotConfig.Presets.WristPickupRev);
        }
    }

    public static void setBeacon (boolean tempBeacon) {
        beacon = tempBeacon;
    }
    public static void setManualAdjust (boolean manualAdjustTemp) {
        manualAdjust = manualAdjustTemp;
    }

    public static boolean callBeacon () {
        return beacon;
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