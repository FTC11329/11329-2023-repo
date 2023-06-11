package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

public class Pose2dUtilities {
    public static Vector2d extractVector2d(Pose2d input) {
        return new Vector2d(input.getX(), input.getY());
    }

    public static double extractHeading(Pose2d input) {
        return input.getHeading();
    }
}
