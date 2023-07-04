package com.example.meepmeep;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepMain {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot1 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.125)
                .setDimensions(13, 13)
                .followTrajectorySequence(CenterStartWithPreloadToHighGold::generateTrajectorySequence
                );
        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.125)
                .setDimensions(13, 13)
                .followTrajectorySequence(
                        CenterStartNoPreloadToHigh::generateTrajectorySequence
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot1)
                .addEntity(myBot2)
                .start();
    }
}


// TODO: What is this?
/*
//--------------6 High Corrected Park
                                .splineTo(new Vector2d(40 + initialPosition.getX() , 0 + initialPosition.getY()), 0)
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
                                //.splineToLinearHeading(new Pose2d(55.7 + initialPosition.getX(), -5 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
                                //.waitSeconds(1)
                                //Park Center
                                //.splineToLinearHeading(new Pose2d(55.5+initialPosition.getX(), 20 + initialPosition.getY(),  Math.toRadians(180)), Math.toRadians(90))
                                //.waitSeconds(1)
                                //Park Right
                                .splineToLinearHeading(new Pose2d(50+initialPosition.getX(), 40 + initialPosition.getY(), Math.toRadians(180)), Math.toRadians(90))
                                .waitSeconds(1)
                                .build()
 */
