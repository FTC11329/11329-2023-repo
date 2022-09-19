package org.firstinspires.ftc.teamcode.Auto.drive.blue;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.AutoBase;
import org.firstinspires.ftc.teamcode.Auto.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.RobotSide;
@Autonomous(name="testBlueAuto", group = "test")
public class TestAuto extends AutoBase {
    public static double DISTANCE = 30;
    @Override
    public void Run() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory trajectoryForward = drive.trajectoryBuilder(new Pose2d())
                .forward(DISTANCE)
                .build();

        Trajectory trajectoryBackward = drive.trajectoryBuilder(trajectoryForward.end())
                .back(DISTANCE)
                .build();
        while(true){
            drive.followTrajectory(trajectoryForward);
            drive.followTrajectory(trajectoryBackward);
        }
    }

    @Override
    public RobotSide GetSide() {
        return RobotSide.Blue;
    }
}
