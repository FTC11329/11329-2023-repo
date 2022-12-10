package org.firstinspires.ftc.teamcode.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.auto.kennan.April;
import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.OpModeBase;

import java.lang.reflect.InvocationTargetException;

public abstract class RoadRunnerAutoBase extends OpModeBase {
    public abstract void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException;

    public abstract void Build(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void BuildParkOne(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void BuildParkTwo(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void BuildParkThree(TrajectorySequenceBuilder trajectorySequenceBuilder);

    protected GlacierDrive glacierDrive;

    protected April april;
    protected int marker;
    protected Telemetry telemetry;

    TrajectorySequence builtTrajectorySequence;

    Thread roadrunnerThread;

    @Override
    public void InstallLower() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        glacierDrive = new GlacierDrive(hardwareMap);
        april = new April(hardwareMap);

        Container.bindInstance(glacierDrive);
        Container.bindInstance(april);

        telemetry = (Telemetry) Container.resolve(Telemetry.class);
    }

    @Override
    public void init() {
        super.init();

        try {
            ResolveSubsystems();
        } catch (Exception e) {
            telemetry.log().add("Failed to resolve all subsystems in auto!");
            telemetry.log().add(e.toString());
        }

        TrajectorySequenceBuilder trajectorySequenceBuilder = glacierDrive.trajectorySequenceBuilder(new Pose2d(0, 0));

        Build(trajectorySequenceBuilder);

        builtTrajectorySequence = trajectorySequenceBuilder.build();
    }

    @Override
    public void init_loop() {
        if (april.getAprilTag() != -1) {
            marker = april.getAprilTag();
        }
    }

    //the start function is called at the beginning of the program ie. when the play button is pressed and only runs ONCE
    @Override
    public void start() {
        super.start();

        TrajectorySequenceBuilder endTrajectorySequenceBuilder = glacierDrive.trajectorySequenceBuilder(builtTrajectorySequence.end());
        TrajectorySequence buildEndTrajectorySequence;

        switch (marker) {
            case 1:
                BuildParkTwo(endTrajectorySequenceBuilder);
                break;
            case 2:
                BuildParkThree(endTrajectorySequenceBuilder);
                break;
            default:
                // TODO: not this
                BuildParkThree(endTrajectorySequenceBuilder);
                break;
        }

        buildEndTrajectorySequence = endTrajectorySequenceBuilder.build();

        roadrunnerThread = new Thread(() -> {
            glacierDrive.followTrajectorySequence(builtTrajectorySequence);
            glacierDrive.followTrajectorySequence(buildEndTrajectorySequence);
        });
        roadrunnerThread.start();
    }

    @Override
    public void stop() {
        super.stop();

        try {
            roadrunnerThread.interrupt();
            roadrunnerThread.join();
        } catch (Exception e) {
        }
    }
}
