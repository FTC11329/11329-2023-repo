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
        RoadRunnerBotEntity myBot3 = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.125)
                .setDimensions(13, 13)
                .followTrajectorySequence(
                        AllConesHighNormalStart::generateTrajectorySequence
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot1)
                .addEntity(myBot2)
                .addEntity(myBot3)
                .start();
    }
}
