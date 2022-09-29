package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.RobotSide;

@TeleOp(name="Teleop Blue", group="Teleop")
public class TeleopBlue extends TeleopBase {
    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }

}