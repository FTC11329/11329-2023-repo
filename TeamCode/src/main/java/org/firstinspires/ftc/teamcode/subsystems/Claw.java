package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

public class Claw implements DiInterfaces.IDisposable, DiInterfaces.ITickable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id = "clawServo")
    Servo closeClaw;
    @DiContainer.Inject(id = "wristServo1")
    public Servo handWave1;
    @DiContainer.Inject(id = "wristServo2")
    public Servo handWave2;
    @DiContainer.Inject(id = "colorSensor")
    public RevColorSensorV3 colorSensor;
    @DiContainer.Inject()
    public RobotSide side;
    @DiContainer.Inject()
    public Brace brace;
    @DiContainer.Inject()
    public Slides slides;

    @DiContainer.Inject()
    Telemetry telemetry;
    private boolean grabbing = true;
    private boolean grabbingDebounce = false;
    static public boolean isAtPreset = false;
    private boolean controlBrace = true;

    private static ElapsedTime myStopwatch = new ElapsedTime();

    double power = 0;
    double targetPosition = 0;
    int slidesTemp;


    public enum ConeColor {
        RED,
        BLUE,
        NONE
    }

    @Override
    public void onInitialize() {
        setPos(RobotConfig.Wrist.startingPosition);
        handWave2.setPosition(1.0 - targetPosition);
        handWave1.setPosition(targetPosition);
    }

    @Override
    public void onTick() {
        targetPosition += RobotConfig.Claw.wristSpeed * power;
        targetPosition = Math.min(Math.max(targetPosition, 0), 1);

        handWave2.setPosition(1.0 - targetPosition);
        handWave1.setPosition(targetPosition);

        if (!isAtPreset && RobotConfig.Claw.autoGrab && ((side == RobotSide.Red && getConeColor() == ConeColor.RED) || ((side == RobotSide.Blue && getConeColor() == ConeColor.BLUE)))) {
            grab();
        }
        if(brace.activated && brace.atPole) {
            ungrab();
        }
//        if(brace.activated && brace.atPole){
//            if(controlBrace) {
//                myStopwatch.reset();
//                slidesTemp = slides.getTargetPosition();
//                slides.setTargetPosition(slidesTemp + 200);
//                controlBrace = false;
//            }
//        } else{
//            controlBrace = true;
//        }
//        if(myStopwatch.time() >= 0.1 && !controlBrace){
//            ungrab();
//        }
//        if(myStopwatch.time() >= 0.2 && !controlBrace){
//           slides.setTargetPosition(slidesTemp);
//        }
    }

    public void setWristPower(double wristPower) {
        power = wristPower;
    }
    

    public void setPos(double pos) {
        targetPosition = pos;
        power = 0;
    }

    public void toggle() {
        if (grabbing && !grabbingDebounce) {
            grab();
        } else if (!grabbing && !grabbingDebounce) {
            ungrab();
        }
        grabbingDebounce = true;
    }

    public void resetToggle() {
        grabbingDebounce = false;
    }

    public void ungrab() {
        grabbing = true;
        closeClaw.setPosition(RobotConfig.Claw.closePos);
    }

    public void grab() {
        grabbing = false;
        closeClaw.setPosition(RobotConfig.Claw.openPos);
    }

    public boolean isConePresent() {
        return colorSensor.getDistance(DistanceUnit.CM) <= RobotConfig.Claw.maxConeDistance;
    }

    public ConeColor getConeColor() {
        if (!isConePresent()) return ConeColor.NONE;

        NormalizedRGBA rgba = colorSensor.getNormalizedColors();

        double RtoG = Math.abs(rgba.red - rgba.green);
        double GtoB = Math.abs(rgba.green - rgba.blue);
        return (RtoG > GtoB) ? ConeColor.RED : ConeColor.BLUE;

//        ^These lines do the same thing v
//
//        if (rgba.red > rgba.green && rgba.red > rgba.blue  && isConePresent()){
//            return ConeColor.RED;
//        } else if (rgba.blue > rgba.red && rgba.blue > rgba.green && isConePresent()) {
//            return ConeColor.BLUE;
//        } else {
//            return ConeColor.NONE;
//        }
    }

    public void displayToTelemetry() {
        telemetry.addData("Hand1 Position", handWave1.getPosition());
        telemetry.addData("Hand2 Position", handWave2.getPosition());
        telemetry.addData("Cone Present", isConePresent());
        //telemetry.addData("Cone Color", getConeColor());
    }

    public boolean getPresetBool() {
        return isAtPreset;
    }

    public void setPresetBool(boolean isAtPreset) {
        Claw.isAtPreset = isAtPreset;
    }

    @Override
    public void onDispose() {

    }
}
