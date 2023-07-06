package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class FastGrab {
    static Pose2d initialPosition = new Pose2d(-63.5, -32.0, 0);

    Pose2d placeLocationHighContested = new Pose2d(58, -3.5, 1.95);//4.53
    Pose2d placeLocationMed  = new Pose2d(43.5, -2.5, Math.toRadians(50));
    Pose2d placeLocationLow  = new Pose2d(27.5, 4.5, 0.68);

    Pose2d intermediatePosition1 = new Pose2d(53,6, Math.toRadians(90));
    Pose2d intermediatePosition2 = new Pose2d(50, -18.5, Math.toRadians(45));
    Pose2d intermediatePosition3 = new Pose2d(48, -15, Math.toRadians(90));

    Pose2d parkLeft = new Pose2d(47.5, -20, Math.toRadians(180));
    Pose2d parkCenter = new Pose2d(47, 4 ,Math.toRadians(90));
    Pose2d parkRight = new Pose2d(53, -8, Math.toRadians(90));

    //not tuned yet
    Pose2d pickupOffset = new Pose2d(-4,0,Math.toRadians(0));
    Pose2d placeOffset = new Pose2d(0.5,-1,Math.toRadians(0));


    Vector2d pickupLocation = new Vector2d(50.2, 31);

    static TrajectorySequence generateTrajectorySequence(DriveShim drive) {

        return drive.trajectorySequenceBuilder(initialPosition)
                .splineTo(new Vector2d(40 + initialPosition.getX() , -1 + initialPosition.getY()), 0)
                .splineTo(new Vector2d(55+initialPosition.getX(), -1 + initialPosition.getY()), 0)
                .splineTo(new Vector2d(87+initialPosition.getX(), -29 + initialPosition.getY()), Math.toRadians(270))
                .strafeRight(10)
                .setReversed(true)
                .splineTo(new Vector2d( 75+ initialPosition.getX(), -10 + initialPosition.getY()), Math.toRadians(90))
                .splineTo(new Vector2d(68+ initialPosition.getX(),0+ initialPosition.getY()), Math.toRadians(120))
                .setReversed(false)
                //PARK LEFT
                //.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , 40 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
                //PARK RIGHT
                //.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , -3 + initialPosition.getY(), Math.toRadians(270)), Math.toRadians(250))
                //PARK CENTER
                //.splineToLinearHeading(new Pose2d(76 + initialPosition.getX() , 20 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
                .build();
    }
}

/*
.splineTo(new Vector2d(40 + initialPosition.getX() , -1 + initialPosition.getY()), 0)
                //Preload
                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -10 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                //CONE 1
                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))
                //CONE 2
                .splineTo(new Vector2d(50.2 + initialPosition.getX(), -31 + initialPosition.getY()), Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(58 + initialPosition.getX(), 3.5 + initialPosition.getY(), 4.23))

                //CONE 3
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
 */
