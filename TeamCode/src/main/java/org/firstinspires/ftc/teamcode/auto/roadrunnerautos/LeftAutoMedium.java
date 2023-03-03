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

@Autonomous(name = "Left Auto Medium", group = "Competition")
public class LeftAutoMedium extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    Pose2d placeLocation = new Pose2d(47.75, -0.25, Math.toRadians(50));
    Vector2d pickupLocation = new Vector2d(47.25, 30.0);
    double pickupOffsetX = 0.2;

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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto - 300);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
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

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto - 100);
                })
                .splineTo(new Vector2d(54.5, 5), Math.toRadians(0))
                .resetConstraints()
                //Go to pole and let go
                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1.25, placeLocation.getY() - 1.3, Math.toRadians(-310)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                //ANOTHER CONE !!!!!!!!!!!!!!!
                //Go to pickup a cone
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX) -0.5, pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() + 1.25, placeLocation.getY() - 0.5, placeLocation.getHeading()))


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
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 2), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1, placeLocation.getY() + 0.5, placeLocation.getHeading()))

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

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 3), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1.45, placeLocation.getY() + 0.25, placeLocation.getHeading()))

                // Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                // ANOTHER CONE !!!!!!!!!!!!!!!
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
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 4), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1.7, placeLocation.getY() + 0.7, placeLocation.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                });
    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    claw.ungrab();
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 5), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1.35, placeLocation.getY() + 0.7, placeLocation.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

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
                    claw.ungrab();
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 5), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
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

                .lineToLinearHeading(new Pose2d(placeLocation.getX() - 1.7, placeLocation.getY() + 0.7, placeLocation.getHeading()))

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .lineToLinearHeading(new Pose2d(49, 7, Math.toRadians(270)));
    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    claw.ungrab();
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .splineTo(new Vector2d(pickupLocation.getX() + (pickupOffsetX * 5), pickupLocation.getY()), Math.toRadians(90))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.02, () -> {
                    claw.grab();
                })


                .waitSeconds(0.2)

                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMed - 150);
                    arm.toPosition(RobotConfig.Presets.Arm1Med);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .lineToLinearHeading(new Pose2d(47.25, -19.25, Math.toRadians(138)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMed);
                })

                .waitSeconds(0.25)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.15)

                .UNSTABLE_addTemporalMarkerOffset(0.75, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .lineToLinearHeading(new Pose2d(48, -17, Math.toRadians(180)));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
