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

@Autonomous(name = "Left Auto All High", group = "Competition")
public class LeftAutoAllHigh extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

    Pose2d placeLocationHigh = new Pose2d(43.75, -24.3, Math.toRadians(60));
    Pose2d placeLocationMed = new Pose2d(48.25, -2.75, Math.toRadians(50));
    Pose2d intermediatePos = new Pose2d(51.9, -6.8, Math.toRadians(90));
    Vector2d pickupLocation = new Vector2d(46, 30.25);

    Vector2d placeLocationOffset = new Vector2d(-0.45, 0.45);

    int slidesDrop = 300;

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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev - 300);
                    claw.setPos(RobotConfig.Presets.WristPickup);
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

                .addTemporalMarkerOffset(0, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev - 100);
                })
                .splineTo(new Vector2d(54.5, 5), Math.toRadians(0))
                .resetConstraints()
                //Go to pole and let go
                .addTemporalMarkerOffset(0.9, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRev + 500);
                })
                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() - 1.25, placeLocationMed.getY() - 1.3, Math.toRadians(50)))

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.15, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //Go to pickup a cone
                .addTemporalMarkerOffset(0.5, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .splineTo(new Vector2d(pickupLocation.getX(), pickupLocation.getY()), Math.toRadians(90))

                .addTemporalMarkerOffset(-0.2, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.15)

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.35, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(pickupLocation.getX(), pickupLocation.getY(), Math.toRadians(90)), Math.toRadians(90))

                .addTemporalMarkerOffset(-0.2, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.15)

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.35, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + (151 * 2));
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(pickupLocation.getX(), pickupLocation.getY(), Math.toRadians(90)), Math.toRadians(90))

                .addTemporalMarkerOffset(-0.2, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.15)

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.35, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + (151 * 3));
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(pickupLocation.getX(), pickupLocation.getY(), Math.toRadians(90)), Math.toRadians(90))

                .addTemporalMarkerOffset(-0.2, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.15)

                //Going to High
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.35, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + (151 * 4));
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                });
    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup-151*3);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.07)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0, () -> {
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

                .splineToLinearHeading(new Pose2d(50.5, 28, Math.toRadians(90)), Math.toRadians(90))
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .resetConstraints();

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup-151*3);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .addTemporalMarkerOffset(0, () -> {
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
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup-151*3);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                //Go to pickup a cone
                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.2)

                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedAuto + 50);
                    arm.toPosition(RobotConfig.Presets.Arm1MedAuto);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.6, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedAuto - 50);
                    arm.toPosition(RobotConfig.Presets.Arm1MedAuto - 30);
                })

                .lineToLinearHeading(new Pose2d(intermediatePos.getX(), intermediatePos.getY(), intermediatePos.getHeading()))
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX(), placeLocationHigh.getY(),placeLocationHigh.getHeading()),placeLocationHigh.getHeading())

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

                .lineToLinearHeading(new Pose2d(48, -17, Math.toRadians(180)));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}