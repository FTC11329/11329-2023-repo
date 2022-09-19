package org.firstinspires.ftc.teamcode.commands;
import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class TeleOpDrive implements DiInterfaces.ITickable{
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject()
    Drivetrain drivetrain;
    @DiContainer.Inject(id="gamepad1")
    public Gamepad gamepad1;
    @Override
    public void onTick(){
        double vertical = -drivetrain.powerCurve(gamepad1.left_stick_y);
        double horizontal = -drivetrain.powerCurve(gamepad1.left_stick_x);
        double rotational = drivetrain.powerCurve(gamepad1.right_stick_x);

        drivetrain.MecanumDrive(vertical, horizontal, rotational, 0.7);
    }
}
