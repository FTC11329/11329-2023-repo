package org.firstinspires.ftc.teamcode.roadrunner;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.auto.kennan.April;
import org.firstinspires.ftc.teamcode.auto.kennan.ParkLocation;
import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.utilities.OpModeBase;

import java.lang.reflect.InvocationTargetException;

public abstract class RoadRunnerAutoBase extends OpModeBase {
    private final Pose2d red = new Pose2d(0, 0);
    private final Pose2d blue = new Pose2d(0, 10);
    protected GlacierDrive glacierDrive;
    protected April april;
    protected ParkLocation marker;
    protected Telemetry telemetry;
    TrajectorySequence builtTrajectorySequence;
    Thread roadrunnerThread;
    TrajectorySequenceBuilder trajectorySequenceBuilder;

    public abstract void ResolveSubsystems() throws InvocationTargetException, IllegalAccessException, InstantiationException;

    public abstract void build(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder);

    public abstract void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder);

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

        trajectorySequenceBuilder = glacierDrive.trajectorySequenceBuilder(new Pose2d(0, 0));
        build(trajectorySequenceBuilder);

        builtTrajectorySequence = trajectorySequenceBuilder.build();
    }

    @Override
    public void init_loop() {
        if (april.getAprilTag() != ParkLocation.UNKNOWN) {
            marker = april.getAprilTag();
            telemetry.addData("April Tag", marker);
            telemetry.update();
        }
    }

    //the start function is called at the beginning of the program ie. when the play button is pressed and only runs ONCE
    @Override
    public void start() {
        super.start();

        TrajectorySequenceBuilder endTrajectorySequenceBuilder = glacierDrive.trajectorySequenceBuilder(builtTrajectorySequence.end());
        TrajectorySequence buildEndTrajectorySequence;

        switch (marker) {
            case CENTER:
                buildParkCenter(endTrajectorySequenceBuilder);
                break;
            case RIGHT:
                buildParkRight(endTrajectorySequenceBuilder);
                break;
            default:
                buildParkLeft(endTrajectorySequenceBuilder);
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
