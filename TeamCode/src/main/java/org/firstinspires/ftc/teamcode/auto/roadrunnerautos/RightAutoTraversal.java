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

    Pose2d placeLocationHigh = new Pose2d(45, 29.5, Math.toRadians(315));
    Pose2d placeLocationMed  = new Pose2d(43.5, 2.5, Math.toRadians(310));
    Pose2d placeLocationLow  = new Pose2d(44, -6, Math.toRadians(270));

    Pose2d intermediatePosition1 = new Pose2d(53,-8, Math.toRadians(270));
    Pose2d intermediatePosition2 = new Pose2d(56, 19.5, Math.toRadians(315));
    Pose2d intermediatePosition3 = new Pose2d(48, 15, Math.toRadians(270));

    Pose2d parkLeft = new Pose2d(47.5, 18, Math.toRadians(0));
    Pose2d parkCenter = new Pose2d(55, -5 ,Math.toRadians(270));
    Pose2d parkRight = new Pose2d(54, -28, Math.toRadians(270));

    //not tuned yet
    Pose2d pickupOffset = new Pose2d(-4,0,Math.toRadians(0));
    Pose2d placeOffset = new Pose2d(0.5,1,Math.toRadians(0));


    Vector2d pickupLocation = new Vector2d(53.5, -30);

    //Auto path: Low near stack, Medium, High speed stack, Medium * 2


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

                .lineTo(pickupLocation)

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
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })

                .lineToLinearHeading(placeLocationMed)

                //PLACES MED CONE
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //HIGH CONE PICKUP 1 !!!!!!!!!!!!!!!
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
                .splineToLinearHeading(intermediatePosition1, intermediatePosition1.getHeading())
                .splineToLinearHeading(new Pose2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY(), intermediatePosition1.getHeading() + pickupOffset.getHeading()), intermediatePosition1.getHeading())
                .addTemporalMarkerOffset(-0.05, () -> {
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
                .splineToLinearHeading(intermediatePosition1, Math.toRadians(90) + 0.0001)
                .splineToLinearHeading(placeLocationHigh, Math.toRadians(135))

                //DROPS HIGH CONE 1!!!!!!!!!!!!!!!!
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //HIGH CONE PICKUP 2!!!!!!!!!!!!!!!
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
                .lineToLinearHeading(intermediatePosition1)
                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))
//                .splineTo(pickupLocation, intermediatePosition3.getHeading())
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
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })

                .lineToLinearHeading(intermediatePosition3)
                .lineToLinearHeading(intermediatePosition2)

                .lineToLinearHeading(placeLocationHigh)

                .waitSeconds(0.2)

                //PLACES HIGH CONE 2 !!!!!!!!!!
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.15, () -> {
                    claw.grab();
                })
                //HIGH CONE PICKUP 3 !!!!!!!!!!!!!!!
                .waitSeconds(0.1)
                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 453);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.4, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })

                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))
//                .splineTo(pickupLocation, intermediatePosition3.getHeading())
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
                    brace.brace();
                })
                .lineToLinearHeading(intermediatePosition3)
                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX() + placeOffset.getX(), placeLocationHigh.getY() + placeOffset.getX(), placeLocationHigh.getHeading() + placeOffset.getHeading() ))

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
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })
                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.2)


                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + 50);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })

                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX() + placeOffset.getX(), placeLocationHigh.getY() + placeOffset.getX(), placeLocationHigh.getHeading() + placeOffset.getHeading() ))

                .waitSeconds(0.05)

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.25)

                .addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    brace.unbrace();
                })

                .lineToLinearHeading(parkLeft);

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })
                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })

                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX() + placeOffset.getX(), placeLocationHigh.getY() + placeOffset.getX(), placeLocationHigh.getHeading() + placeOffset.getHeading() ))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    brace.unbrace();
                })
                .waitSeconds(0.1)

                .lineToLinearHeading(intermediatePosition3)
                .lineToLinearHeading(parkCenter);
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })
                //Go to pickup a cone
                .lineToLinearHeading(intermediatePosition3)
                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })

                .lineToLinearHeading(intermediatePosition2)
                .lineToLinearHeading(new Pose2d(placeLocationHigh.getX() + placeOffset.getX(), placeLocationHigh.getY() + placeOffset.getX(), placeLocationHigh.getHeading() + placeOffset.getHeading() ))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0.3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    brace.unbrace();
                })

                .lineToLinearHeading(intermediatePosition3)
                .lineToLinearHeading(parkRight);
//                .splineToLinearHeading(parkRight, parkRight.getHeading());

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}