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

@Autonomous(name = "Left Center 6 High", group = "Competition")
public class LeftCenter6High extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    Pose2d pickupPosition = new Pose2d(50.2, 31, Math.toRadians(90));
    Pose2d highLocation = new Pose2d(58, -3.5, 2.05);
    Pose2d intermediate1 = new Pose2d();

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
                //Setup
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                    brace.unbrace();
                    claw.setPresetBool(true);
                    //claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 60;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 65;
                    }
                })
                .splineToLinearHeading(new Pose2d(53.2, 6, Math.toRadians(90)), 0)
                //.splineTo(new Vector2d(35.5, 0), 0)
                .splineTo(new Vector2d(52.2, 55.5), Math.toRadians(90))//was 50.2
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    brace.brace();
                })
                .setReversed(true)
                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 57;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 54;
                    }
                })
                .splineTo(new Vector2d(64, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })


                //REPEATED---------------------
                .splineTo(new Vector2d(49, 75), Math.toRadians(85))//was 90
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .setReversed(true)
                .splineTo(new Vector2d(66, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })

                //REPEATED---------------------
                .splineTo(new Vector2d(49, 75), Math.toRadians(85))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .setReversed(true)
                .splineTo(new Vector2d(66, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 302);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //REPEATED---------------------
                .splineTo(new Vector2d(49, 75), Math.toRadians(85))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .setReversed(true)
                .splineTo(new Vector2d(66, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 453);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //REPEATED---------------------
                .splineTo(new Vector2d(49, 75), Math.toRadians(80))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .setReversed(true)
                .splineTo(new Vector2d(66, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //REPEATED---------------------
                .splineTo(new Vector2d(49, 75), Math.toRadians(80))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .setReversed(true)
                .splineTo(new Vector2d(66, 50), 5.14)
                .setReversed(false)
                .addTemporalMarkerOffset(0.15, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    //claw.grab();
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })
                .addTemporalMarkerOffset(0.5, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 70;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 68;
                    }
                });
    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(50.7, 46, Math.toRadians(180)), Math.toRadians(90));
    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(50.5, 20, Math.toRadians(180)), Math.toRadians(90));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                .splineToLinearHeading(new Pose2d(56, 4, Math.toRadians(90)), Math.toRadians(90));//was 51
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
