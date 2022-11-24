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
        public static int SlidesHigh = -724;
        public static int Arm1High = 364;

        //High Reverse
        public static int SlidesHighRev = -1111;
        public static int Arm1HighRev = 601;

        //Medium
        public static int SlidesMed = 0;
        public static int Arm1Med = 344;

        //Medium Reverse
        public static int SlidesMedRev = -257;
        public static int Arm1MedRev = 600;

        //Low
        public static int SlidesLow = -1314;
        public static int Arm1Low = 0;

        //Pick up
        public static int SlidesPickup = 0;
        public static int Arm1Pickup = 0;

        //Pick up Reverse
        public static int SlidesPickupRev = 0;
        public static int Arm1PickupRev = 890;
    }

    public static class Slides {

        public static String rightSlideMotor = "rightSlideMotor";
        public static String leftSlideMotor = "leftSlideMotor";

        public static int maxSlidePosition = -1600;
        public static int minSlidePosition = 0;

        public static double slidePower = 30;
    }

    public static class Claw {

        public static int clawMinRange = 0;
        public static int clawMaxRange = 1;
        public static double wristSpeed = 0.04;
        public static String clawServo = "clawServo";
        public static String colorSensor = "colorSensor";
        public static String handServo1 = "handServo1";
        public static String handServo2 = "handServo2";
        public static double closePos = 0.3;
        public static double openPos = 0;

        public static double maxConeDistance = 5;
        public static int maxAutoGrabHeight = -200;
    }

    public static class Arm {
        public static double armSpeed = 8;
        public static double armPower = 0.25;
        public static String arm = "arm";

        public static int maxArmPosition = 890;
        public static int minArmPosition = 0;
    }
}