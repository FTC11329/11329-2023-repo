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
        public static double fastSpeed = 0.55;//0.5
    }

    public static class Presets {

        //Led Power
        public static double LightPower = 0.8;
        public static String LEDName = "LED";


        //Claw placing
        public static double WristPlacing = 0.250;

        //High
        public static int SlidesHigh = -905;
        public static int Arm1High = 386;

        //High Auto
        public static int SlidesHighAuto = -905;
        public static int Arm1HighAuto = 386;

        //High From Reverse Pickup
        public static int SlidesHighFromRev = -2075;//-1900;
        public static int Arm1HighFromRev = 260;

        //High From Reverse Pickup Auto
        public static int SlidesHighFromRevAuto = -2075;
        public static int Arm1HighFromRevAuto = 260;

        //High Reverse
        public static int SlidesHighRev = -1800;
        public static int Arm1HighRev = 610;

        //Medium
        public static int SlidesMed = -100;
        public static int Arm1Med = 400;

        //Medium From Reverse Pickup
        public static int SlidesMedFromRev = -450;
        public static int Arm1MedFromRev = 300;

        //Medium Reverse
        public static int SlidesMedRev = -715;
        public static int Arm1MedRev = 600;

        //Medium Reverse Auto
        public static int SlidesMedRevAuto = -665;
        public static int Arm1MedRevAuto = 620;

        //Low
        public static int SlidesLow = -1740;
        public static int Arm1Low = 25;

        //Low From Reverse Pickup
        public static int SlidesLowFromRev = 0;
        public static int Arm1LowFromRev = 230;

        //Pick up
        public static int SlidesPickup = 0;
        public static int Arm1Pickup = 0;
        public static double WristPickup = 0.300;

        //Pick up Reverse
        public static int SlidesPickupRev = 0;
        public static int Arm1PickupRev = 890;
        public static double WristPickupRev = 0.964;

        //Pick up top cone
        public static int SlidesPickupTop = -616;
        public static int Arm1PickupTop = 2;


        //Pick up top cone reverse
        public static int SlidesPickupTopRev = -616;
        public static int Arm1PickupTopRev = 890;

        //Pickup Fallen Cone
        public static int SlidesPickupFall = -345;
        public static int Arm1PickupFall = 50;
        public static double WristPickupFall = 0;

        //Drive Position
        public static int DriveSlides = -1444;
    }

    public static class Slides {

        public static String rightSlideMotor = "rightSlideMotor";
        public static String leftSlideMotor  = "leftSlideMotor";
        public static String leftLimitSwitch = "leftSlideLimitSwitch";
        public static String rightLimitSwitch = "rightSlideLimitSwitch";
        public static int maxSlidePosition = -2200;
        public static int minSlidePosition = 0;

        public static double slidePower = 75;//50;
    }

    public static class Claw {

        public static int clawMinRange = 0;
        public static int clawMaxRange = 1;
        public static double wristSpeed = 0.02;
        public static String clawServo = "clawServo";
//        public static String colorSensor = "colorSensor";
        public static String handServo1 = "wristServo1";
        public static String handServo2 = "wristServo2";
        public static double closePos = 0.3;
        public static double openPos = 0.066;

        public static double maxConeDistance = 8; //CM
        public static int maxAutoGrabHeight = -200;
    }

    public static class Arm {
        public static double armSpeed = 8;
        public static double armPowerSlow = 0.2;
        public static double armPowerFast = 0.4;
        public static String arm = "arm";

        public static int maxArmPosition = 890;
        public static int minArmPosition = 0;

        public static double kp = 0.005;//0.005
        public static double ki = 0;//0
        // d was too small                                 <--Joke haha funny
        public static double kd = 0.0005;//0.0005
        public static double kf = 0.2;//0.2
    }

    public static class Wrist {
        public static double startingPosition = 0.68;
    }
}