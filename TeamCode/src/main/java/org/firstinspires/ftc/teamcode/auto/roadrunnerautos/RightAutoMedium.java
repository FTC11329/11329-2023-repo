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

@Autonomous(name = "Right Auto Medium", group = "Competition")
public class RightAutoMedium extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    Pose2d placeLocationMed  = new Pose2d(43.5, 2.5, Math.toRadians(310));
    Pose2d intermediatePosition3 = new Pose2d(48, 15, Math.toRadians(270));
    Vector2d pickupLocation = new Vector2d(51.75, -30);
    Pose2d parkLeft = new Pose2d(47.5, 18, Math.toRadians(0));
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
                })

                //Puts the arm in placing position
                .addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 57;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 52;
                    }
                })

                //Go to 8, -4 without turning
                //.lineTo(new Vector2d(12, -10))

                .addTemporalMarkerOffset(0, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev);
                    brace.brace();
                })
                .splineTo(new Vector2d(52, -10), Math.toRadians(0))
                .resetConstraints()
                //Go to pole and let go
                .lineToLinearHeading(new Pose2d(placeLocationMed.getX()+3, placeLocationMed.getY()+4, Math.toRadians(310)))
                //comp day allen stared at this for three minutes and didn't change anything
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                //MED CONE 2--------------------------------------------------------------------
                //A MEDIUM CONE PICKUP !!!!!!!!!!!!!!!
                //Go to pickup a cone


                .addTemporalMarkerOffset(0.1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
//                .lineToLinearHeading(intermediatePosition1)
//                .lineTo(pickupLocation)
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.1)

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
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                //---------------------------------------------------------------
                //MED CONE 3------------------------------------------------------
                //A MEDIUM CONE PICKUP !!!!!!!!!!!!!!!
                //Go to pickup a cone

                .addTemporalMarkerOffset(0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+302);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
//                .lineToLinearHeading(intermediatePosition1)
//                .lineTo(pickupLocation)
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.1)

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
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+302);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
//                .lineToLinearHeading(intermediatePosition1)
//                .lineTo(pickupLocation)
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.1)

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
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })
                //MID CONE 4------------------------------------
                //A MEDIUM CONE PICKUP !!!!!!!!!!!!!!!
                //Go to pickup a cone

                .addTemporalMarkerOffset(0.1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop+453);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTop);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
//                .lineToLinearHeading(intermediatePosition1)
//                .lineTo(pickupLocation)
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.1)

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
                });

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
//                .lineToLinearHeading(intermediatePosition1)
//                .lineTo(new Vector2d(pickupLocation.getX() + pickupOffset.getX(), pickupLocation.getY() + pickupOffset.getY()))
                .splineTo(pickupLocation, Math.toRadians(270))
                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })
                .lineToLinearHeading(placeLocationMed)
                //.lineToLinearHeading(intermediatePosition2)
                //.lineToLinearHeading(new Pose2d(placeLocationHigh.getX() + placeOffset.getX(), placeLocationHigh.getY() + placeOffset.getX(), placeLocationHigh.getHeading() + placeOffset.getHeading() ))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.05)

                .addTemporalMarkerOffset(0.1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    brace.unbrace();
                })
//                .waitSeconds(0.05)

                //.splineTo(new Vector2d(intermediatePosition3.getX(), intermediatePosition3.getY()), Math.toRadians(270))
                //splineTo(new Vector2d(parkLeft.getX(), parkLeft.getY()), Math.toRadians(270));
                //.splineToLinearHeading(intermediatePosition3, Math.toRadians(270))
                .splineToLinearHeading(parkLeft, Math.toRadians(270));
//                .lineToLinearHeading(intermediatePosition3)
//                .lineToLinearHeading(parkCenter);

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.02, () -> {
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
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() , placeLocationMed.getY(), placeLocationMed.getHeading()))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                })


                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    brace.unbrace();
                })
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(49, -6, Math.toRadians(90)));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder


                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(270))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.02, () -> {
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
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX(), placeLocationMed.getY(), placeLocationMed.getHeading()))

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                .addTemporalMarkerOffset(0.2, () -> {
                    claw.ungrab();
                    brace.unbrace();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0, () -> {
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