package org.firstinspires.ftc.teamcode.commands;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Slides;

public class TeleopDrive implements DiInterfaces.ITickable {
    @DiContainer.Inject()
    Telemetry telemetry;
    @DiContainer.Inject()
    Drivetrain drivetrain;
    @DiContainer.Inject()
    Slides slides;
    @DiContainer.Inject(id = "gamepad1")
    public Gamepad gamepad1;

    @Override
    public void onTick() {
        double vertical = -gamepad1.left_stick_y;
        double horizontal = -gamepad1.left_stick_x;
        double rotational = gamepad1.right_stick_x;
        double upPower = gamepad1.left_trigger;
        double downPower = gamepad1.right_trigger;
        //doubles reading for probably more accuracy or something because java idk
        slides.moveSlides(upPower - downPower);
        drivetrain.MecanumDrive(vertical, horizontal, rotational, 0.7);
    }
}
//Teagan was defenetly here. ya totally shure allen
/*
russian jets at N5.32234791 W2.3138492 CHEAP
192.168.4.382.930.171
russian jets for $5? satisfactory!
somebody once told me that the world was gonna roll me and i ain't the charpest tool in the shed and she was looking kinda dumb with a finger and a thumb in the shape of an l on her forehead and the years start coming and they don't stop coming and fed to the rures and you hit the ground ruunning. didnt make sense not to live for fun, your brain gets smart Iff you see this
 */