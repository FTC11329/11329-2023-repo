package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class AllConesHighNormalStart {
    static Pose2d initialPosition = new Pose2d(-63.5, -32.0, 0);

    static TrajectorySequence generateTrajectorySequence(DriveShim drive) {

        return drive.trajectorySequenceBuilder(initialPosition)
                .splineTo(new Vector2d(40 + initialPosition.getX() , -1 + initialPosition.getY()), 0)
                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -10 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))


                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                //Park Left
                //.splineToLinearHeading(new Pose2d(51+initialPosition.getX(), 40 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))

                //Park Center
                //.splineToLinearHeading(new Pose2d(50.5+initialPosition.getX(), 20 + initialPosition.getY(),  Math.toRadians(180)), Math.toRadians(90))

                //Park Right
                .splineToLinearHeading(new Pose2d(50.7 + initialPosition.getX(), -5 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))

                .waitSeconds(1)
                .build();
    }
}


