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
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;
@Disabled
@Autonomous(name = "Left Auto High", group = "Competition")
public class  LeftAutoHigh extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    Pose2d placeLocationHigh = new Pose2d(52.75, 1.25, Math.toRadians(315));
    Pose2d placeLocationMed = new Pose2d(48.25, -2.75, Math.toRadians(50));
    Vector2d pickupLocation = new Vector2d(47, 30.25);

    Vector2d placeLocationOffset = new Vector2d(-0.45, 0.45);

    int slidesDrop = 300;

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
                    Claw.isAtPreset = true;
                })

                //Puts the arm in placing position
                .addTemporalMarkerOffset(0, () -> {
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
                .addTemporalMarkerOffset(0.9, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto + 500);
                })
                .splineToSplineHeading(new Pose2d(placeLocationHigh.getX() , placeLocationHigh.getY() , placeLocationHigh.getHeading()), placeLocationHigh.getHeading())

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.15, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })
                .waitSeconds(0.2)

                //ANOTHER CONE !!!!!!!!!!!!!!!
                //Go to pickup a cone
                .addTemporalMarkerOffset(0.5 , () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .addTemporalMarkerOffset(1.5,() -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .lineToLinearHeading(new Pose2d(48, 17, Math.toRadians(0)))
                .splineTo(new Vector2d(pickupLocation.getX(), pickupLocation.getY()), Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX(), placeLocationMed.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 1 * placeLocationOffset.getX(),placeLocationMed.getY() + 1 * placeLocationOffset.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 302);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 2 * placeLocationOffset.getX(),placeLocationMed.getY() + 2 * placeLocationOffset.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + 300);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop + 453);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })

                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 3 * placeLocationOffset.getX(),placeLocationMed.getY() + 3 * placeLocationOffset.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .addTemporalMarkerOffset(0.07, () -> {
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
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 4 * placeLocationOffset.getX(),placeLocationMed.getY() + 4 * placeLocationOffset.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
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

                .lineToLinearHeading(new Pose2d(50.5, 28, Math.toRadians(90)))
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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
                })

                //Moves off the stack
                .addTemporalMarkerOffset(0.05, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.2)

                //Going to Medium
                .addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .addTemporalMarkerOffset(0.5, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(new Pose2d(placeLocationMed.getX() + 4 * placeLocationOffset.getX(),placeLocationMed.getY() + 4 * placeLocationOffset.getY(), placeLocationMed.getHeading()))

                .addTemporalMarkerOffset(-0.25, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto + slidesDrop);
                })

                //Ungrabs
                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
                })
                //Go to pickup a cone
                .splineTo(pickupLocation, Math.toRadians(90))

                .addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                    Claw.isAtPreset = true;
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

                .lineToLinearHeading(new Pose2d(46.75, -18.5, Math.toRadians(138)))

                .waitSeconds(0.05)

                .addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                    Claw.isAtPreset = false;
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