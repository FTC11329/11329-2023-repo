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
        
        slides.moveSlides(upPower - downPower);
        drivetrain.MecanumDrive(vertical, horizontal, rotational, 0.7);
    }
}
//teagan was here
/*
russian jets at 5.32234791 2.3138492 CHEAP
russian jets for $5? satisfactory!
 */