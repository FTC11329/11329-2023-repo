package org.firstinspires.ftc.teamcode.auto.roadrunnerautos;




import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.roadrunner.RoadRunnerAutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Brace;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Slides;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "Right Center Fast GoldCone", group = "Competition")
public class RightFastGoldCone extends RoadRunnerAutoBase {
    Arm arm;
    Claw claw;
    Slides slides;
    Brace brace;

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
                //Setup--
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                    brace.unbrace();
                    claw.setPresetBool(true);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                })
//                .addTemporalMarker(0.75, () ->{
//                    //arm.toPosition(RobotConfig.Presets.Arm1Pickup+150);
//                })
                .addTemporalMarker(2, () ->{
                    //arm.toPosition(RobotConfig.Presets.Arm1Pickup  );
                    //claw.setPos(RobotConfig.Wrist.startingPosition);
                    //claw.grab();
                })
                .addTemporalMarker(2, () ->{
                    //arm.toPosition(RobotConfig.Presets.Arm1Pickup  );
                    //claw.setPos(RobotConfig.Wrist.startingPosition);
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    //claw.grab();
                })

                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 60;
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 60;
                    }
                })

                .splineTo(new Vector2d(40.5, -6), 0)
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
                .splineTo(new Vector2d(77, -20.5), Math.toRadians(-90))
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

                .splineTo(new Vector2d(83.0 , -40.5), Math.toRadians(-90))
                //.waitSeconds(10)
                .splineTo(new Vector2d(90.5  ,-47.5 ), Math.toRadians(-90))
                .splineTo(new Vector2d(95.5  ,-57.0 ), Math.toRadians(-70))//was 100.5 -57.5
                //.waitSeconds(5)
                //.waitSeconds(20)
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    claw.ungrab();

                })
                .addTemporalMarkerOffset(1, () -> {
                    claw.grab();

                })
                .addTemporalMarkerOffset(3, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);

                })
                .waitSeconds(5)
                .lineTo(new Vector2d(78, -55))
                .waitSeconds(10.3)
                .addTemporalMarkerOffset(0, () -> {
                    slides.setTargetPosition(RobotConfig.Presets.SlidesHighRev);
                    arm.toPosition(RobotConfig.Presets.Arm1HighRev);
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    brace.brace();
                })
                //.waitSeconds(1)
                .lineToLinearHeading(new Pose2d(72.7 , -43.5, Math.toRadians(310)))

                .addTemporalMarkerOffset(2, () -> {
                    claw.ungrab();
                })
                .waitSeconds(3);
//                .addTemporalMarkerOffset(3, () -> {
//                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
//                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
//                    brace.unbrace();
//                });


    }


    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    brace.unbrace();
                })
                .splineToLinearHeading(new Pose2d(72, 0, 0), Math.toRadians(90));
    }
    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    brace.unbrace();
                })
                .splineToLinearHeading(new Pose2d(75.5, -25.5, 0), Math.toRadians(90));
    }
    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickup);
                    arm.toPosition(RobotConfig.Presets.Arm1Pickup);
                    brace.unbrace();
                })
                .splineToLinearHeading(new Pose2d(70.5, -47.5 , 0), Math.toRadians(90));
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}