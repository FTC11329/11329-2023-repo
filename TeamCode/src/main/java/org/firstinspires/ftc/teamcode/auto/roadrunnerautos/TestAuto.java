package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.auto.kennan.April;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.auto.kennan.April;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "Test Road Runner Autos", group = "test")
public class TestAuto extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;

    private April april;
    private int marker;
    @Override
    public void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        arm = (Arm) Container.resolve(Arm.class);
        claw = (Claw) Container.resolve(Claw.class);
        slides = (Slides) Container.resolve(Slides.class);
    }

    @Override
    public void Build(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        claw.ungrab();
        april = new April(hardwareMap);
        marker = april.getAprilTag();
        trajectorySequenceBuilder
                .addDisplacementMarker(()-> {claw.ungrab();})
                //.strafeRight(1)
                .splineToLinearHeading(new Pose2d(8,-4, Math.toRadians(0)), Math.toRadians(0))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh);
                    arm.toPosition(RobotConfig.Presets.Arm1High);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })

                .splineToLinearHeading(new Pose2d(63,-4, Math.toRadians(90)), Math.toRadians(0))
                .lineTo(new Vector2d(63,-2))
                //.lineToLinearHeading(new Pose2d(62, 4 ,Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, ()-> {claw.grab();})
                .UNSTABLE_addTemporalMarkerOffset(0.5, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesPickupTopRev);
                    arm.toPosition(RobotConfig.Presets.Arm1PickupTopRev);
                    claw.setPos(RobotConfig.Presets.WristPickupTopRev);
                })
                .lineTo(new Vector2d(51,-4))
                .lineTo(new Vector2d(51,-18))
                .lineTo(new Vector2d(51,-25))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {claw.ungrab();})
                .UNSTABLE_addTemporalMarkerOffset(1, () -> {slides.toPosition(RobotConfig.Presets.SlidesHigh-250);})
                .waitSeconds(1)
                .lineTo(new Vector2d(51,-4))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    slides.toPosition(RobotConfig.Presets.SlidesHigh-1050);
                    arm.toPosition(RobotConfig.Presets.Arm1High-140);
                    claw.setPos(RobotConfig.Presets.WristPickupRev);
                })
                .waitSeconds(1)
                .lineTo(new Vector2d(63,-1))
                .UNSTABLE_addTemporalMarkerOffset(2, ()-> {claw.grab();});

                //.splineTo(new Vector2d(0, 0), Math.toRadians(180));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
