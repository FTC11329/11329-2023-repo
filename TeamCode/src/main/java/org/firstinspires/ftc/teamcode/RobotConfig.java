package org.firstinspires.ftc.teamcode;

public class RobotConfig {

    //store the config variables, if u don't get this u suck so bad hii allen
    //seriously though this is just a place we can store a bunch of variables that are easy to call
    public static class Drivetrain {

        public static String frontLeftMotorName = "frontLeft";
        public static String frontRightMotorName = "frontRight";
        public static String backLeftMotorName = "backLeft";
        public static String backRightMotorName = "backRight";
        public static double slowSpeed = 0.3;
        public static double fastSpeed = 0.5;//0.5
    }

    public static class Presets {
        //High
        public static int SlidesHigh = -905;
        public static int Arm1High = 386;

        //High Reverse
        public static int SlidesHighRev = -1582;
        public static int Arm1HighRev = 628;

        //Medium
        public static int SlidesMed = 0;
        public static int Arm1Med = 355;

        //Medium Reverse
        public static int SlidesMedRev = -500;
        public static int Arm1MedRev = 609;

        //Low
        public static int SlidesLow = -1740;
        public static int Arm1Low = 25;

        //Pick up
        public static int SlidesPickup = 0;
        public static int Arm1Pickup = 0;
        public static double WristPickup = 0.345;

        //Pick up Reverse
        public static int SlidesPickupRev = 0;
        public static int Arm1PickupRev = 890;
        public static double WristPickupRev = 1;

        //Pick up top cone
        public static int SlidesPickupTopRev = -616;
        public static int Arm1PickupTopRev = 890;
        public static double WristPickupTopRev = 1;
    }

    public static class Slides {

        public static String rightSlideMotor = "rightSlideMotor";
        public static String leftSlideMotor = "leftSlideMotor";
        public static String limitSwitch = "armLimitSwitch";
        public static int maxSlidePosition = -2200;
        public static int minSlidePosition = 0;

        public static double slidePower = 50;//30;
    }

    public static class Claw {

        public static int clawMinRange = 0;
        public static int clawMaxRange = 1;
        public static double wristSpeed = 0.02;
        public static String clawServo = "clawServo";
        public static String colorSensor = "colorSensor";
        public static String handServo1 = "wristServo1";
        public static String handServo2 = "wristServo2";
        public static double closePos = 0.3;
        public static double openPos = 0.066;

        public static double maxConeDistance = 5;
        public static int maxAutoGrabHeight = -200;
    }

    public static class Arm {
        public static double armSpeed = 8;
        public static double armPowerSlow = 0.2;
        public static double armPowerFast = 0.4;
        public static String arm = "arm";

        public static int maxArmPosition = 890;
        public static int minArmPosition = 0;
    }

    public static class Wrist {
        public static double startingPosition = 0.68;
    }
}