package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.utilities.LEDEffect;

import java.util.ArrayList;
import java.util.List;

public class RobotConfig {

    //store the config variables, if u don't get this u suck so bad hii allen
    //seriously though this is just a place we can store a bunch of variables that are easy to call
    public static class Drivetrain {

        public static String frontLeftMotorName = "frontLeft";
        public static String frontRightMotorName = "frontRight";
        public static String backLeftMotorName = "backLeft";
        public static String backRightMotorName = "backRight";
        public static double slowSpeed = 0.375;
        public static double fastSpeed = 0.75;//0.5
    }
    public static class Brace{
        public static String braceName = "LineBreak";
    }
    public static class LED {
        public static double MaxLightPower = 0.8;
        public static String LEDName = "LED";
        public static LEDEffect defaultLEDEffect = LEDEffect.BREATHING;

        // note: all powers are from 0-1, with normalization to MaxLightPower occurring at the end
        public static double CONSTANT_POWER = 1;

        // in seconds
        public static double BREATH_LENGTH = 2;
        public static class Custom{
            public static double iterationSPeed = 3; //cycle through 2 values per second
            public static double[] FLASH = {0.1, 0.2, 0.3, 0.1, 0.8};
            public static double[] ICEMORSE = {0.1, 0.1, 1, 0.1, 1,.1, .1, .1, 1, .1, 1, 1, 1, 1, .1, .1, .1, 1 ,1, 1 ,1 ,.1 ,.1, 1,.1 ,1, .1, .1, .1, .1 };//ICERobotics in morse:..-.-...-.--- -...--- - ..-.-...
        }
    }

    public static class Presets {

        //Claw placing
        public static double WristPlacing = 0.250;
        public static double WristPlacingHigh = 0.420;

        //High
        public static int SlidesHigh = -1220;
        public static int Arm1High = 386;

        //High Auto
        public static int SlidesHighAuto = -1150;
        public static int Arm1HighAuto = 330;

        //High From Reverse Pickup
        public static int SlidesHighFromRev = -2075;//-1900;
        public static int Arm1HighFromRev = 260;

        //High From Reverse Pickup Auto
        public static int SlidesHighFromRevAuto = -1950;
        public static int Arm1HighFromRevAuto = 260;

        //High Reverse
        public static int SlidesHighRev = -1600;
        public static int Arm1HighRev = 610;

        //Medium
        public static int SlidesMed = 0;
        public static int Arm1Med = 370;

        //Medium Auto
        public static int SlidesMedAuto = -200;
        public static int Arm1MedAuto = 400;


        //Medium From Reverse Pickup
        public static int SlidesMedFromRev = -450;
        public static int Arm1MedFromRev = 300;

        //Medium Reverse
        public static int SlidesMedRev = -347;
        public static int Arm1MedRev = 610;

        //Medium Reverse Auto
        public static int SlidesMedRevAuto = -900;
        public static int Arm1MedRevAuto = 640;

        //Low
        public static int SlidesLow = -1740;
        public static int Arm1Low = 25;

        //Low From Reverse Pickup
        public static int SlidesLowFromRev = 0;
        public static int Arm1LowFromRev = 230;

        //Pick up
        public static int SlidesPickup = 0;
        public static int Arm1Pickup = 0;
        public static double WristPickup = 0.3;

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
        public static int SlidesDrive = -970;
        public static int Arm1Drive = -970;

    }

    public static class Slides {

        public static String rightSlideMotor = "rightSlideMotor";
        public static String leftSlideMotor = "leftSlideMotor";
        public static String leftLimitSwitch = "leftSlideLimitSwitch";
        public static String rightLimitSwitch = "rightSlideLimitSwitch";
        public static String bracePlateServo = "bracePlate";
        public static int maxSlidePosition = -2200;
        public static int minSlidePosition = 5;

        public static double manualSlidePower = 85;//50;
        public static double automaticSlidePower = 1;// 0 to 1 power where 1 is max
        public static double kp = 0.0002;
        public static double ki = 0.0000;
        public static double kd = 0.0000;
        public static double kf = 0.00;
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

        public static double maxConeDistance = 8; //CM
        public static int maxAutoGrabHeight = -200;

        public static boolean autoGrab = true;
        public static boolean autoRelease = true;
    }

    public static class Arm {
        public static double armSpeed = 8;
        public static double armPowerSlow = 0.2;
        public static double armPowerFast = 0.4;
        public static String arm = "arm";

        public static int maxArmPosition = 890;
        public static int minArmPosition = 0;

        public static double kp = 0.0050;
        public static double ki = 0.0000;
        public static double kd = 0.0005;
        public static double kf = 0.2000;

        public static double gearRatio = 13.2/19.7;
        // d was too small                                 <--Joke haha funny
    }

    public static class Wrist {
        public static double startingPosition = 0.68;
    }
}