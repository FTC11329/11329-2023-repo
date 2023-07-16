package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;


import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Brace;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;
@Disabled
@Autonomous(name = "Right Center PreLoad GoldCone", group = "Competition")
public class RightPreLoadGoldCone extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
        brace = (Brace) Container.resolve(Brace.class);
    }

    @Override
    public void build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.grab();
        trajectorySequenceBuilder
                //Setup--
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                    brace.unbrace();
                    claw.setPresetBool(true);
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 60;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 60;
                    }
                })

                //Start Auto
                .splineTo(new Vector2d(75.5, 0),0)
                .addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })
                .setReversed(true)
                .splineTo(new Vector2d(68, 3), Math.toRadians(110))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.grab();
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })
                .setReversed(false)
                .splineTo(new Vector2d(75.5, -43.5), Math.toRadians(-90))
                .splineTo(new Vector2d(75.7 , -58.5), Math.toRadians(-90))
                .splineTo(new Vector2d(84.5  ,-75.5 ), Math.toRadians(-85))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                //.splineTo(new Vector2d(12, -45), Math.toRadians(-90)).setReversed(true)

                .strafeRight(5)
                .waitSeconds(1)
                .lineToLinearHeading(new Pose2d(70.7, -43.5, Math.toRadians(310)))
                .addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    brace.unbrace();
                });

    }


    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(74, 0, 0), Math.toRadians(90));
    }
    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(75.5, -25.5, 0), Math.toRadians(90));
    }
    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(65.5, -50.5 , 0), Math.toRadians(90));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}