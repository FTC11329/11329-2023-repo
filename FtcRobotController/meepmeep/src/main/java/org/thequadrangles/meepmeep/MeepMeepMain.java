package org.thequadrangles.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Vector;

public class MeepMeepMain {
    private static final Vector2d CONE_PICKUP = new Vector2d(56, 12.5);
    private static final Vector2d DEPOSIT_CONE = new Vector2d(28, 8);
    private static final Vector2d PARK = new Vector2d(36, 36);

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueLight())
                .setDimensions(13, 13)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(36, 60, Math.toRadians(-90)))
                                .forward(35)
                                .splineTo(DEPOSIT_CONE, Math.toRadians(-120))
                                .waitSeconds(1)
                                .setReversed(true)
                                .splineTo(CONE_PICKUP, Math.toRadians(0))
                                .setReversed(false)
                                .splineTo(DEPOSIT_CONE, Math.toRadians(-120))
                                .setReversed(true)
                                .splineTo(PARK, Math.toRadians(90))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(false) // Don't worry i hate it too, its just that the official field
                                    // image rn sucks for dark mode
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

}
