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

@Autonomous(name = "Right Auto High", group = "Competition")
public class RightAutoHigh extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    Pose2d placeLocation = new Pose2d(59, 0.5, Math.toRadians(45));
    Pose2d pickupLocation = new Pose2d(50.5, -28.25, Math.toRadians(90));

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
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 50;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 45;
                    }
                })

                //Go to 8, -4 without turning
                .lineTo(new Vector2d(17, -4))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1HighAuto);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighAuto);
                })
                .splineTo(new Vector2d(55, -5), Math.toRadians(45))
                .resetConstraints()
                //Go to pole and let go
                .lineToLinearHeading(new Pose2d(54.5, -2.75, Math.toRadians(45)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);

                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    claw.ungrab();
                })
                .waitSeconds(0.2)

                //ANOTHER CONE !!!!!!!!!!!!!!!

                //Go to pickup a cone
                .lineToLinearHeading(pickupLocation)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighFromRevAuto);
                })
                .waitSeconds(0.3)

                //Going to High
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                    arm.toPosition(RobotConfig.Presets.Arm1HighFromRevAuto);
                })

                .lineToLinearHeading(placeLocation)

                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })
                .UNSTABLE_addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })

                //Other 4 Cones Go Here





                //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1);

    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupRev);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                //Go to pickup a cone
                .lineToLinearHeading(pickupLocation)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                .waitSeconds(0.2)

                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })

                .lineToLinearHeading(new Pose2d(53.5, 17, Math.toRadians(135)))

                .waitSeconds(0.05)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })

                .waitSeconds(0.1)

                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })

                .lineToLinearHeading(new Pose2d(51, 18.5, Math.toRadians(90)));

    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder

                //ANOTHER CONE !!!!!!!!!!!!!!!
                .waitSeconds(0.05)
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                //Go to pickup a cone
                .lineToLinearHeading(pickupLocation)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighFromRevAuto);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {;
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(placeLocation)

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
                .lineToLinearHeading(new Pose2d(49, -3, Math.toRadians(90)));
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

                //Go to pickup a cone
                .lineToLinearHeading(pickupLocation)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })

                //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHigh - 300);
                })
                .waitSeconds(0.4)

                //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0.6, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesMedRevAuto);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
                    arm.toPosition(RobotConfig.Presets.Arm1MedRevAuto);
                })

                .lineToLinearHeading(placeLocation)

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

                .lineToLinearHeading(new Pose2d(52.5, -30, Math.toRadians(90)));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}