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
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "Left Auto High", group = "Competition")
public class LeftAutoHigh extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    Pose2d placeLocationMed = new Pose2d(47.75, -0.25, Math.toRadians(50));
    Pose2d placeLocationHigh = new Pose2d(52.5, 1, Math.toRadians(45));
    Vector2d pickupLocation = new Vector2d(47.25, 30.25);

    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
    }

    @Override
    public void build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.grab();
        trajectorySequenceBuilder
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                })

                //Puts the arm in placing position
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPlacingHigh);
                    arm.toPosition(RobotConfig.Presets.Arm1HighAuto);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto);

                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 55;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 52;
                    }
                })

                //Go to 8, -4 without turning
                .lineTo(new Vector2d(17, 4))

                .resetConstraints()

                //Go to HIGH pole and let go
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto + 500);
                })
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX() , placeLocationHigh.getY() , placeLocationHigh.getHeading()), placeLocationHigh.getHeading())

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                //ANOTHER CONE !!!!!!!!!!!!!!!
                //Go to pickup a cone
                .UNSTABLE_addTemporalMarkerOffset(0.5 , () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                })
                .lineToLinearHeading(new Pose2d(50, 17, Math.toRadians(0)))
                .splineTo(new Vector2d(pickupLocation.getX(), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 1.75, placeLocationMed.getY() - 0.75, placeLocationMed.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(placeLocationMed)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 302);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(placeLocationMed)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 453);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() - 1.8, placeLocationMed.getY() + 0.8, placeLocationMed.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })


                .waitSeconds(0.1);

    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() - 2.5, placeLocationMed.getY() + 1, placeLocationMed.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 55;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 52;
                    }
                })

                .lineToLinearHeading(new Pose2d(50.5, 28, Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .resetConstraints();

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() - 2.5, placeLocationMed.getY() - 1, placeLocationMed.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(49, 7, Math.toRadians(270)));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.2)

                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedAuto + 50);
                    arm.toPosition(RobotConfig.Presets.Arm1MedAuto);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedAuto - 50);
                    arm.toPosition(RobotConfig.Presets.Arm1MedAuto - 30);
                })

                .lineToLinearHeading(new Pose2d(46.5, 16.75, Math.toRadians(224)))

                .waitSeconds(0.05)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.25)

                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .lineToLinearHeading(new Pose2d(48, -17, Math.toRadians(180)));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}