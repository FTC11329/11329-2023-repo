package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.RobotSide;

@TeleOp(name="Teleop Red", group="Teleop")
public class TeleopRed extends TeleopBase {
    @Override
    public RobotSide GetSide() {
        return RobotSide.Red;
    }
}
