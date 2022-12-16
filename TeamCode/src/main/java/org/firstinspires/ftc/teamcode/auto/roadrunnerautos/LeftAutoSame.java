package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "LeftAutoSame", group = "Competition")
public class LeftAutoSame extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
    }

    @Override
    public void Build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.grab();
        trajectorySequenceBuilder.addDisplacementMarker(() -> {
                    //grabs preset
                    claw.grab();
                })
                //.strafeRight(1)
                //.forward(40)
                //go to 8, -4 without turning
                .lineTo(new Vector2d(8, 4))
                //.splineTo(new Vector2d(8, -4), Math.toRadians(0))
                //puts the arm in placing position
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh);
                    arm.toPosition(RobotConfig.Presets.Arm1High);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                //go to pole and let go
                .splineToLinearHeading(new Pose2d(62.2, 4, Math.toRadians(-90)), Math.toRadians(0))
                //.lineTo(new Vector2d(62, -3.2))
                //.lineToLinearHeading(new Pose2d(62, 4 ,Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                //------------------------------------------------------------pick up new------------
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTopRev);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupTopRev);
                })
                //go to pickup a cone
                .lineTo(new Vector2d(51.5, 4))
                .lineTo(new Vector2d(51.5, 18))
                .lineTo(new Vector2d(51.2, 26))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                //starts going to high
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(1)
                //moves to pole
                .lineTo(new Vector2d(51, 4))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 1250);
                    arm.toPosition(RobotConfig.Presets.Arm1High - 140);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })
                .waitSeconds(0.2)
                .lineTo(new Vector2d(62, 3))
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    claw.ungrab();
                }).waitSeconds(1.3)

                //---------------------------------another ONE --------------------------------
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTopRev + 100);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupTopRev);
                })
                .lineTo(new Vector2d(51.5, 4))
                .lineTo(new Vector2d(51.5, 18))
                .lineTo(new Vector2d(51.5, 26))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(1)
                .lineTo(new Vector2d(51, 4))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 1350);
                    arm.toPosition(RobotConfig.Presets.Arm1High - 140);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })
                .waitSeconds(0.7)
                .lineTo(new Vector2d(62.5, 3))
                .UNSTABLE_addTemporalMarkerOffset(3.7, () -> {
                    claw.ungrab();
                })
                .waitSeconds(1);

    }

    @Override
    public void BuildParkOne(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .UNSTABLE_addTemporalMarkerOffset(1.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .strafeRight(14)
                .back(23);;
        //.splineTo(new Vector2d(50, 20), Math.toRadians(0));
    }

    @Override
    public void BuildParkTwo(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .UNSTABLE_addTemporalMarkerOffset(1.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                //.lineToConstantHeading(new Vector2d(55, -3)).strafeLeft(3);
                .back(2)
                .strafeRight(13);
        //.splineTo(new Vector2d(50, -3), Math.toRadians(0));
    }

    @Override
    public void BuildParkThree(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .UNSTABLE_addTemporalMarkerOffset(2.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .strafeRight(12)
                //.lineToConstantHeading(new Vector2d(55, -3))
                .forward(34)
                .back(10);
        // .splineTo(new Vector2d(50, -29), Math.toRadians(0));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
