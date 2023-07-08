package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class CenterStartNoPreloadToHigh implements MeepMeepOpMode {
    static Pose2d initialPosition = new Pose2d(-18, -6, Math.toRadians(0));

    static TrajectorySequence generateTrajectorySequence(DriveShim drive) {

       return drive.trajectorySequenceBuilder(initialPosition)


               .splineTo(new Vector2d(42+initialPosition.getX(), -7.5 + initialPosition.getY()), 0)

               .splineTo(new Vector2d(79+initialPosition.getX(), -19.5+ initialPosition.getY()), Math.toRadians(-90))

               .splineTo(new Vector2d(83.0+initialPosition.getX() , -40.5+ initialPosition.getY()), Math.toRadians(-90))
               //.waitSeconds(10)
               .splineTo(new Vector2d(83.0+initialPosition.getX()  ,-47.5+ initialPosition.getY() ), Math.toRadians(-90))
               .splineTo(new Vector2d(83.0 +initialPosition.getX() ,-57.0 + initialPosition.getY()), Math.toRadians(-90))//was 100.5 -57.5

                .build();
    }

}
