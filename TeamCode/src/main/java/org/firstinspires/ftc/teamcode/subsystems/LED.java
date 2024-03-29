package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.utilities.LEDEffect;

public class LED implements DiInterfaces.IInitializable, DiInterfaces.IDisposable, DiInterfaces.ITickable {
    @DiContainer.Inject(id = "LED")
    public DcMotorEx LED;
    private LEDEffect effect;
    private ElapsedTime elapsedTime;

    @Override
    public void onInitialize() {
        elapsedTime = new ElapsedTime();
        elapsedTime.reset();

        LED.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        LED.setDirection(DcMotorSimple.Direction.REVERSE);
        LED.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        setEffect(RobotConfig.LED.defaultLEDEffect);
        onTick();
    }

    public void setEffect(LEDEffect ledEffect) {
        effect = ledEffect;

        elapsedTime.reset();
    }

    // note: bad things happen if this returns more than 1
    public static double getPower(double seconds, LEDEffect ledEffect) {
        double power = 0;

        if (ledEffect == LEDEffect.PULSE) {
            double sinInput = (Math.PI * seconds) / 2;

            power = Math.abs((Math.sin(sinInput)));
        } else if (ledEffect == LEDEffect.BREATHING) {
            power = (Math.sin(Math.PI * seconds) / 2) + 0.5;
        } else if (ledEffect == LEDEffect.CONSTANT) {
            power = RobotConfig.LED.CONSTANT_POWER;
        } else if (ledEffect == LEDEffect.MORSE) {
            power = RobotConfig.LED.Custom.ICEMORSE[((int) (seconds * 2)) % RobotConfig.LED.Custom.ICEMORSE.length];
        } else if (ledEffect == LEDEffect.FLASH) {
            power = RobotConfig.LED.Custom.FLASH[((int) (seconds * 2)) % RobotConfig.LED.Custom.FLASH.length];
        }

        return Range.clip(power, 0, 1);
    }

    @Override
    public void onTick() {
        LED.setPower(getPower(elapsedTime.seconds(), effect) * RobotConfig.LED.MaxLightPower);
    }

    @Override
    public void onDispose() {
        LED.setPower(0);
    }
}
