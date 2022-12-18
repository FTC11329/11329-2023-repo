package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;
import java.math.MathContext;

@Autonomous(name = "Right Auto Medium", group = "Competition")
public class RightAutoMedium extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
    }

    @Override
    public void Build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.grab();
        trajectorySequenceBuilder.addDisplacementMarker(() -> {
                                            //Grabs preload
                    claw.grab();
                })
                                            //Go to 8, -4 without turning
                .lineTo(new Vector2d(8, -4))

                .lineTo(new Vector2d(52,-4))
                                            //Puts the arm in placing position
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })
                                            //Go to pole and let go
                .lineToLinearHeading(new Pose2d(48,1,Math.toRadians(310)))
                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })






                .waitSeconds(0.5)
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                                            //Go to pickup a cone

                .lineToLinearHeading(new Pose2d(54, -18, Math.toRadians(270)))
                .lineTo(new Vector2d(54,-25))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                                            //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.4)
                                            //Moves to pole
                .lineToLinearHeading(new Pose2d(51, -4,Math.toRadians(310)))
                                            //Going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })

                .lineToLinearHeading(new Pose2d(52.5,-5,Math.toRadians(310)))
                .lineTo(new Vector2d(48,1.5))
                                            //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                })


                                            //Another cone



                .UNSTABLE_addTemporalMarkerOffset(0.7, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTop + 151);
                    arm.toPosition(5);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
                .lineToLinearHeading(new Pose2d(54, -5, Math.toRadians(270)))
                .waitSeconds(0.2)

                                            //Go to pickup a cone
                .lineTo(new Vector2d(54,-25))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.grab();
                })
                                            //Moves off the stack
                .UNSTABLE_addTemporalMarkerOffset(0.4, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh - 250);
                })
                .waitSeconds(0.7)
                                            //Moves to pole
                .lineToLinearHeading(new Pose2d(51, -4, Math.toRadians(310)))
                                            //Finish going to Medium
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesMedRev);
                    arm.toPosition(RobotConfig.Presets.Arm1MedRev);
                    claw.setPos(RobotConfig.Presets.WristPlacing);
                })
                .waitSeconds(0.2)

                .lineTo(new Vector2d(52,-4))
                .lineTo(new Vector2d(46.5,2.5))
                                            //Ungrabs
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    claw.ungrab();
                });

    }

    @Override
    public void BuildParkOne(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(3, () -> {
            slides.toPosition(RobotConfig.Presets.SlidesPickup);
            arm.toPosition(RobotConfig.Presets.Arm1Pickup);
            claw.setPos(RobotConfig.Wrist.startingPosition);
        })
                .lineTo(new Vector2d(52,2.5))
                .lineToLinearHeading(new Pose2d(52,20,Math.toRadians(270)));
    }

    @Override
    public void BuildParkTwo(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                }).lineToLinearHeading(new Pose2d(52,-3,Math.toRadians(270)));
    }

    @Override
    public void BuildParkThree(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder.UNSTABLE_addTemporalMarkerOffset(0.8, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                }).lineToLinearHeading(new Pose2d(52,-27, Math.toRadians(0)))
                .lineTo(new Vector2d(50.5,-27));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
