package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.firstinspires.ftc.teamcode.commands.TeleopDrive;
import org.firstinspires.ftc.teamcode.teleop.TeleopBlue;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

public class LED implements DiInterfaces.IInitializable, DiInterfaces.IDisposable, DiInterfaces.ITickable {
    @DiContainer.Inject(id = "LED")
    public DcMotorEx LED;


    @Override
    public void onInitialize() {
        LED.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        LED.setDirection(DcMotorSimple.Direction.REVERSE);
        LED.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        LED.setPower(RobotConfig.Presets.LightPower);
    }
    @Override
    public void onTick() {
        LED.setPower(RobotConfig.Presets.LightPower);
    }
    @Override
    public void onDispose() {
        LED.setPower(RobotConfig.Presets.LightPower);
    }

}
