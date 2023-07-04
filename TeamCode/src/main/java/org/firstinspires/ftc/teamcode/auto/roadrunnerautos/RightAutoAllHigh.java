package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Brace;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "RIGHT Auto All High (Untested)", group = "Competition")
public class RightAutoAllHigh extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    Pose2d pickupPosition = new Pose2d(50.2, -31, Math.toRadians(270));
    Pose2d highLocation = new Pose2d(58, 3.5, 4.23);
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
        trajectorySequenceBuilder
                //Setup
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
                //Move through intermediate positions
                .splineTo(new Vector2d(40 , -1), 0)
                .splineTo(new Vector2d(50.2, -10 ), Math.toRadians(270))
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPlacingHigh);
                    arm.toPosition(RobotConfig.Presets.Arm1HighAuto);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto);
                    brace.brace();
                })
                //Travel to preload place
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })


                //goto pickup 1st cone------------------------------------------------------------------------------
                .splineTo(new Vector2d(pickupPosition.getX(), pickupPosition.getY() ), pickupPosition.getHeading())
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 1st cone
                .addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })




                //goto pickup 2nd cone------------------------------------------------------------------------------
                .splineTo(new Vector2d(pickupPosition.getX(), pickupPosition.getY() ), pickupPosition.getHeading())
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 2nd cone
                .addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+151);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })

                //goto pickup 3rd cone------------------------------------------------------------------------------
                .splineTo(new Vector2d(pickupPosition.getX(), pickupPosition.getY() ), pickupPosition.getHeading())
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 3rd cone
                .addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+302);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })



                //goto pickup 4th cone------------------------------------------------------------------------------
                .splineTo(new Vector2d(pickupPosition.getX(), pickupPosition.getY() ), pickupPosition.getHeading())
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 4th cone
                .addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+453);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                })

                //goto pickup 5th cone------------------------------------------------------------------------------
                .splineTo(new Vector2d(pickupPosition.getX(), pickupPosition.getY() ), pickupPosition.getHeading())
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                //goto place 5th cone
                .addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })
                .lineToLinearHeading(new Pose2d(highLocation.getX(), highLocation.getY(), highLocation.getHeading()))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.08, () -> {
                    claw.ungrab();
                });





    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(51, 40 , Math.toRadians(180)), Math.toRadians(90));
    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(50.5, 20 ,  Math.toRadians(180)), Math.toRadians(90));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .splineToLinearHeading(new Pose2d(50.7 , -5 , Math.toRadians(180)), Math.toRadians(90));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
