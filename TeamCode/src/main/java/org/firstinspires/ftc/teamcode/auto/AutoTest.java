package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.fizzyapple12.javadi.DiContainer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.AutoBase;
import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;

import java.lang.reflect.InvocationTargetException;

@Disabled
@Autonomous(name = "Auto Test")
public class AutoTest extends AutoBase {
    ComputerVision computerVision;

    GlacierDrive drive;

    Telemetry telemetry;

    int lastSeenAprilTag;


    Trajectory trajectoryForward;
    Trajectory trajectoryBackward;

    int DISTANCE = 10;

    @Override
    public void InstallLower() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        computerVision = (ComputerVision) Container.resolve(ComputerVision.class);
        telemetry = (Telemetry) Container.resolve(Telemetry.class);
        drive = (GlacierDrive) Container.resolve(GlacierDrive.class);
    }

    @Override
    public void Run() {
        if (computerVision.getAprilTagID() != -1) {
            lastSeenAprilTag = computerVision.getAprilTagID();
        }

        telemetry.addData("Id", lastSeenAprilTag);
        telemetry.update();

        TrajectorySequence trajectory = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(DISTANCE)
                .build();

        drive.followTrajectorySequence(trajectory);

        telemetry.addLine("aaah");
    }

    @Override
    public void loop() {
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
