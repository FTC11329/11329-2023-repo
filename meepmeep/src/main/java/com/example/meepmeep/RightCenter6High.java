package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class RightCenter6High implements MeepMeepOpMode {
    static Pose2d initialPosition = new Pose2d(-63.5, 11.5, 0);

    static TrajectorySequence generateTrajectorySequence(DriveShim drive) {

        return drive.trajectorySequenceBuilder(initialPosition)
                .splineTo(new Vector2d(35.5+initialPosition.getX(), 0+initialPosition.getY()), initialPosition.getHeading())
                .splineTo(new Vector2d(50.2+initialPosition.getX(), -58.5+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)


                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -76+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -76+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -76+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -76+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)

                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -76+initialPosition.getY()), Math.toRadians(-90)+initialPosition.getHeading())
                .setReversed(true)
                .splineTo(new Vector2d(60 + initialPosition.getX(), -40+initialPosition.getY()), 1.09+initialPosition.getHeading())
                .setReversed(false)



                //Park Left
                .splineToLinearHeading(new Pose2d(51+initialPosition.getX(), -45 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))

                //Park Center
                //.splineToLinearHeading(new Pose2d(50.5+initialPosition.getX(), -22 + initialPosition.getY(),  Math.toRadians(180)), Math.toRadians(90))

                //Park Right
                //.splineToLinearHeading(new Pose2d(50.7 + initialPosition.getX(), 0 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
                .build();
    }

}
