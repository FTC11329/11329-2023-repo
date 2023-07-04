package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

import org.jetbrains.annotations.NotNull;

public interface MeepMeepOpMode {
    Pose2d initialPosition = new Pose2d();

    @NotNull
    static TrajectorySequence generateTrajectorySequence(DriveShim drive) {
        return drive.trajectorySequenceBuilder(initialPosition).build();
    }
}
