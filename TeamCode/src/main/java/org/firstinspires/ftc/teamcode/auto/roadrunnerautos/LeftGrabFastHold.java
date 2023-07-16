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

@Autonomous(name = "Left Auto | Gold Cone Hold", group = "Competition")
public class LeftGrabFastHold extends RoadRunnerAutoBase {
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
                //Setup
                .addDisplacementMarker(() -> {
                    claw.grab(); //Grabs preload
                    brace.unbrace();
                    claw.setPresetBool(true);

                })
                .setConstraints(new TrajectoryVelocityConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 70;//70
                    }
                }, new TrajectoryAccelerationConstraint() {
                    @Override
                    public double get(double v, @NonNull Pose2d pose2d, @NonNull Pose2d pose2d1, @NonNull Pose2d pose2d2) {
                        return 65;//65
                    }
                })
                .addDisplacementMarker(() -> {
                    //claw.setPos(RobotConfig.Presets.WristPickup);

                })
                .splineTo(new Vector2d(40  , 8.5), 0)
                .addDisplacementMarker(() -> {
                    claw.ungrab();
                })
//                .addDisplacementMarker(2, () -> {
//                    claw.setPos(RobotConfig.Wrist.startingPosition);
//                })
                .splineTo(new Vector2d(65, 8.5), 0)
                .addTemporalMarkerOffset(0, () -> {
                    claw.setPos(RobotConfig.Presets.WristPickup);
                    claw.ungrab();
                })
                .splineTo(new Vector2d(79, 32.5), Math.toRadians(95))


                .addTemporalMarkerOffset(0.05, () -> {
                    claw.grab();
                })
                .waitSeconds(0.05)
                .addTemporalMarkerOffset(0.2, () -> {//delay was 0.1
                    //claw.setPos(RobotConfig.Wrist.startingPosition);
                    slides.setTargetPosition(RobotConfig.Presets.SlidesPickupTop);
                })
                //.strafeLeft(10)
                .waitSeconds(0.01)
                .lineTo(new Vector2d(70, 24))
                .setReversed(true)
                //.splineTo(new Vector2d( 75, 10 ), Math.toRadians(80))


                .waitSeconds(1);





    }

    @Override
    public void buildParkLeft(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .splineToLinearHeading(new Pose2d(76  , 10, Math.toRadians(90)), Math.toRadians(250));



    }

    @Override
    public void buildParkCenter(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .splineToLinearHeading(new Pose2d(78 , -14 , Math.toRadians(0)), Math.toRadians(90))
        ;

    }

    @Override
    public void buildParkRight(TrajectorySequenceBuilder trajectorySequenceBuilder) {
        trajectorySequenceBuilder
                .addTemporalMarkerOffset(0.1, () -> {
                    claw.setPos(RobotConfig.Wrist.startingPosition);
                })
                .splineToLinearHeading(new Pose2d(78 , -30 , Math.toRadians(0)), Math.toRadians(90));

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}

//PARK LEFT
//.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , 40 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
//PARK RIGHT
//.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , -3 + initialPosition.getY(), Math.toRadians(270)), Math.toRadians(250))
//PARK CENTER
//.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , 20 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))


/*
.splineTo(new Vector2d(40 + initialPosition.getX() , -1 + initialPosition.getY()), 0)
                .splineTo(new Vector2d(55+initialPosition.getX(), -1 + initialPosition.getY()), 0)
                .splineTo(new Vector2d(87+initialPosition.getX(), -29 + initialPosition.getY()), Math.toRadians(270))
                .strafeRight(10)
                .setReversed(true)
                .splineTo(new Vector2d( 75+ initialPosition.getX(), -10 + initialPosition.getY()), Math.toRadians(90))
                .splineTo(new Vector2d(68+ initialPosition.getX(),0+ initialPosition.getY()), Math.toRadians(120))
 */