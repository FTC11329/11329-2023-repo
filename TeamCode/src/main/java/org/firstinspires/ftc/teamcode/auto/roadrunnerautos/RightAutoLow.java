package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;

import android.drm.DrmStore;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.profile.VelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "Right Auto Low", group = "Competition")
public class RightAutoLow extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    Pose2d placeLocation = new Pose2d(45.5, -1, Math.toRadians(310));

    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
    }

    @Override
    public void Build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.grab();
        trajectorySequenceBuilder
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                })

                //Go to 8, -4 without turning
                .lineTo(new Vector2d(17, -4))

                //Puts the arm in placing position
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

//
//                .setConstraints(new TrajectoryVelocityConstraint() {
//                    @Override
//                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
//                        return 35;
//                    }
//                }, new TrajectoryAccelerationConstraint() {
//                    @Override
//                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
//                        return 32;
//                    }
//                })

                //Go to pole and let go
                .splineToSplineHeading(placeLocation, Math.toRadians(20))
                .resetConstraints()

                .waitSeconds(0.7)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.4)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(51.5, -29), Math.toRadians(270))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(placeLocation)
                .waitSeconds(0.3)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.4)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(51.5, -29), Math.toRadians(270))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(placeLocation)
                .waitSeconds(0.3)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })


                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.4)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop + 302);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(51.5, -29), Math.toRadians(270))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(placeLocation)
                .waitSeconds(0.3)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })


                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.4)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop + 453);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(51.5, -29), Math.toRadians(270))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(placeLocation)
                .waitSeconds(0.3)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

        ;

    }

    @Override
    public void BuildParkOne(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(3, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .lineTo(new Vector2d(52, 2.5))
                .lineToLinearHeading(new Pose2d(52, 20, Math.toRadians(270)));
    }

    @Override
    public void BuildParkTwo(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
            slides.toPosition(RobotConfig.Presets.SlidesPickup);
            arm.toPosition(RobotConfig.Presets.Arm1Pickup);
            claw.setPos(RobotConfig.Wrist.startingPosition);
        }).lineToLinearHeading(new Pose2d(52, -3, Math.toRadians(270)));
    }

    @Override
    public void BuildParkThree(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                }).lineToLinearHeading(new Pose2d(52, -27, Math.toRadians(0)))
                .lineTo(new Vector2d(50.5, -27));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
