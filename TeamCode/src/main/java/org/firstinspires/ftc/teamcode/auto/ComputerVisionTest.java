package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.AutoBase;
import org.firstinspires.ftc.teamcode.subsystems.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Autonomous(name = "Test April tag Detection Algorithm", group = "test")
public class ComputerVisionTest extends AutoBase {

    ComputerVision computerVision;

    Telemetry telemetry;

    int lastSeenAprilTag;

    @Override
    public void InstallLower() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        computerVision = (ComputerVision) Container.resolve(ComputerVision.class);
        telemetry = (Telemetry) Container.resolve(Telemetry.class);
    }

    @Override
    public void Run() {

        telemetry.addLine("aaah");
    }

    @Override
    public void loop() {
        if (computerVision.getAprilTagID() != -1) {
            lastSeenAprilTag = computerVision.getAprilTagID();
        }
        telemetry.addData("Id", lastSeenAprilTag);
        telemetry.update();

    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
