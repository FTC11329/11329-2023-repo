package org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;

public class RelativeTrajectorySequenceBuilder extends TrajectorySequenceBuilder {
    private Pose2d startPose;

    public RelativeTrajectorySequenceBuilder(Pose2d startPose, Double startTangent, TrajectoryVelocityConstraint baseVelConstraint, TrajectoryAccelerationConstraint baseAccelConstraint, double baseTurnConstraintMaxAngVel, double baseTurnConstraintMaxAngAccel) {
        super(startPose, startTangent, baseVelConstraint, baseAccelConstraint, baseTurnConstraintMaxAngVel, baseTurnConstraintMaxAngAccel);
        this.startPose = startPose;
    }

    public TrajectorySequenceBuilder relativeLineTo(Vector2d endPosition) {
        return lineTo(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY() + endPosition.getY()));
    }


    public TrajectorySequenceBuilder relativeLineTo(Vector2d endPosition, TrajectoryVelocityConstraint velConstraint, TrajectoryAccelerationConstraint accelConstraint) {
        return lineTo(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY() + endPosition.getY()), velConstraint, accelConstraint);
    }

    public TrajectorySequenceBuilder relativeLineToConstantHeading(Vector2d endPosition) {
        return lineToConstantHeading(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY() + endPosition.getY()));
    }


    public TrajectorySequenceBuilder relativeLineToSplineHeading(Pose2d endPose) {
        return lineToSplineHeading(new Pose2d(this.startPose.getX() + endPose.getX(), this.startPose.getY() + endPose.getY(), this.startPose.getHeading() + endPose.getHeading()));
    }

    public TrajectorySequenceBuilder relativeStrafeTo(Vector2d endPosition) {
        return strafeTo(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY() + endPosition.getY()));
    }

    public TrajectorySequenceBuilder relativeSplineTo(Vector2d endPosition, double endHeading) {
        return splineTo(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY()), endHeading);
    }

    public TrajectorySequenceBuilder relativeSplineToConstantHeading(Vector2d endPosition, double endHeading) {
        return splineToConstantHeading(new Vector2d(this.startPose.getX() + endPosition.getX(), this.startPose.getY() + endPosition.getY()), endHeading);
    }

    public TrajectorySequenceBuilder relativeSplineToLinearHeading(Pose2d endPose, double endHeading) {
        return splineToLinearHeading(new Pose2d(this.startPose.getX() + endPose.getX(), this.startPose.getY() + endPose.getY(), this.startPose.getHeading() + endPose.getHeading()), endHeading);
    }

    public TrajectorySequenceBuilder relativeSplineToSplineHeading(Pose2d endPose, double endHeading) {
        return splineToSplineHeading(new Pose2d(this.startPose.getX() + endPose.getX(), this.startPose.getY() + endPose.getY(), this.startPose.getHeading() + endPose.getHeading()), endHeading);
    }
}