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

@Autonomous(name = "Right Auto Traversal", group = "Competition")
public class RightAutoTraversal extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    Pose2d placeLocationHigh = new Pose2d(46.5, 27, Math.toRadians(315));
    Pose2d placeLocationMed  = new Pose2d(45, 1, Math.toRadians(310));
    Pose2d placeLocationLow  = new Pose2d(44, -6, Math.toRadians(270));

    Pose2d intermediatePosition1 = new Pose2d(53,-8, Math.toRadians(270));
    Pose2d intermediatePosition2 = new Pose2d(55.5, 17.5, Math.toRadians(315));
    Pose2d intermediatePosition3 = new Pose2d(54.5, 17.5, Math.toRadians(270));

    Vector2d pickupLocation = new Vector2d(54, -30);

    //Auto path: Low near stack, Medium, High speed stack * 3


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
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                    brace.unbrace();
                    claw.setPresetBool(true);
                })

                //Puts the arm in placing position
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Low);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesLow);
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 43;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 45;
                    }
                })

                //A LOW CONE DROP !!!!!!!!!!!!!!!!!!
                .lineToLinearHeading(placeLocationLow)

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                //A MEDIUM CONE PICKUP !!!!!!!!!!!!!!!
                //Go to pickup a cone
                .lineTo(new Vector2d(intermediatePosition1.getX(), intermediatePosition1.getY()))

                .addTemporalMarkerOffset(0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .lineTo(new Vector2d(pickupLocation.getX(), pickupLocation.getY()))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev);
                    brace.brace();
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })


                .lineToLinearHeading(new Pose2d(placeLocationMed.getX(), placeLocationMed.getY(), placeLocationMed.getHeading()))

                //PLACES MED CONE
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER HIGH CONE PICKUP !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })

                //HIGH CONE PICKUP !!!!!!!!!!!!!!!!!!!!
                .lineToLinearHeading(intermediatePosition1)
                .lineTo(pickupLocation)

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesLow);
                })
                .waitSeconds(0.2)

                .addTemporalMarkerOffset(0.4, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })
                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(placeLocationHigh)

                //PLACES LOW CONE
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER FAR HIGH CONE PICKUP !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 302);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })

                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(pickupLocation)

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to HIGH
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    brace.brace();
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(placeLocationHigh)

                .waitSeconds(0.2)

                //PLACES FAR HIGH CONE
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.15, () -> {
                    claw.grab();
                })
                //ANOTHER HIGH CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.1)
                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTopRev + 453);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);

                })
                .addTemporalMarkerOffset(0.4, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })

                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(pickupLocation)

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();

                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(placeLocationHigh)

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })


                .waitSeconds(0.1);

    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(pickupLocation)

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.2)


                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighFromRevAuto + 50);
                    arm.toPosition(RobotConfig.Presets.Arm1HighFromRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                .lineToLinearHeading(new Pose2d(44.33, 22, Math.toRadians(120)))

                .waitSeconds(0.05)

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.25)

                .addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .lineToLinearHeading(new Pose2d(51.5, 18.5, Math.toRadians(90)));

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(new Pose2d(pickupLocation.getX(), pickupLocation.getY(), Math.toRadians(270)))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighFromRevAuto);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighFromRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(), placeLocationHigh.getHeading()))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(49, -4, Math.toRadians(90)));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder


                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(new Pose2d(pickupLocation.getX(), pickupLocation.getY(), Math.toRadians(90)))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighFromRevAuto);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighFromRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(), placeLocationHigh.getHeading()))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .lineToLinearHeading(new Pose2d(54, -32, Math.toRadians(90)));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}