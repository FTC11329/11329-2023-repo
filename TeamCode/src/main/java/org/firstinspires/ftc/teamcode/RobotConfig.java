package org.firstinspires.ftc.teamcode;

public class RobotConfig {

    //store the config variables, if u don't get this u suck so bad hii allen
    //seriously though this is just a place we can store a bunch of variables that are easy to call
    public static class Drivetrain {

        public static String frontLeftMotorName = "frontLeft";
        public static String frontRightMotorName = "frontRight";
        public static String backLeftMotorName = "backLeft";
        public static String backRightMotorName = "backRight";
    }

    public static class Presets {
        //High
        public static int SlidesHigh = -414;
        public static int Arm1High = 1;

        //High Reverse
        public static int SlidesHighRev = -853;
        public static int Arm1HighRev = 1;

        //Medium
        public static int SlidesMed = 0;
        public static int Arm1Med = 1;

        //Medium Reverse
        public static int SlidesMedRev = -56;
        public static int Arm1MedRev = 1;

        //Low
        public static int SlidesLow = -1314;
        public static int Arm1Low = 1;

        //Pick up
        public static int SlidesPickup = 0;
        public static int Arm1Pickup = 1;
    }

    public static class Slides {

        public static String rightSlideMotor = "rightSlideMotor";
        public static String leftSlideMotor = "leftSlideMotor";
    }

    public static class Claw {

        public static int clawMinRange = 0;
        public static int clawMaxRange = 1;
        public static double handSpeed = 0.0055;
        public static String clawServo = "clawServo";
        public static String handServo1 = "handServo1";
        public static String handServo2 = "handServo2";
        public static double closePos = 0.3;
        public static double openPos = 0;
    }

    public static class Arm {
        public static double armSpeed = 0.007;
        public static String armServo1 = "armServo1";
        public static String armServo2 = "armServo2";
    }
}