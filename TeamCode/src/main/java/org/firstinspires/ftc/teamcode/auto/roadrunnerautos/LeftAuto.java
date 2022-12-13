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

@Autonomous(name = "Left Auto", group = "Competition")
public class LeftAuto extends RoadRunnerAutoBase {
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
                    claw.grab();
                })
                //.strafeRight(1)
                .splineToLinearHeading(new Pose2d(-8, -4, Math.toRadians(0)), Math.toRadians(0))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh);
                    arm.toPosition(RobotConfig.Presets.Arm1High);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .splineToLinearHeading(new Pose2d(-63, -4, Math.toRadians(90)), Math.toRadians(0)).lineTo(new Vector2d(-63, -3))
                //.lineToLinearHeading(new Pose2d(62, 4 ,Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                }).waitSeconds(0.5).UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTopRev);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupTopRev);
                }).lineTo(new Vector2d(-51.5, -4)).lineTo(new Vector2d(-51.5, -18)).lineTo(new Vector2d(-51.5, -26))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                }).UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                }).waitSeconds(1).lineTo(new Vector2d(-51, -4)).UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 1050);
                    arm.toPosition(RobotConfig.Presets.Arm1High - 140);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                }).waitSeconds(0.2).lineTo(new Vector2d(-63, -2)).UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    claw.ungrab();
                }).waitSeconds(1.3)

                //---------------------------------another ONE --------------------------------
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTopRev + 100);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupTopRev);
                }).lineTo(new Vector2d(-51.5, -4)).lineTo(new Vector2d(-51.5, -18)).lineTo(new Vector2d(-51.5, -26))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                }).UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                }).waitSeconds(1).lineTo(new Vector2d(-51, -4)).UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 1050);
                    arm.toPosition(RobotConfig.Presets.Arm1High - 140);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                }).waitSeconds(1).lineTo(new Vector2d(-63, -2)).UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    claw.ungrab();
                });
    }

    @Override
    public void BuildParkOne(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
            slides.toPosition(RobotConfig.Presets.SlidesPickup);
            arm.toPosition(RobotConfig.Presets.Arm1Pickup);
            claw.setPos(RobotConfig.Wrist.startingPosition);
        }).strafeLeft(20).forward(20);
        //.splineTo(new Vector2d(50, 20), Math.toRadians(0));
    }

    @Override
    public void BuildParkTwo(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .lineToConstantHeading(new Vector2d(55, -3))
                .strafeLeft(3);
    }

    @Override
    public void BuildParkThree(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .lineToConstantHeading(new Vector2d(55, -3))
                .back(20);
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
