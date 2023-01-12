package org.firstinspires.ftc.teamcode.auto.kennan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Disabled
@Autonomous(name = "Scrimmage Auto")
public class GiveProgrammersMoreTime extends LinearOpMode {
    private GlacierDrive drive;
    private April april;
    private ParkLocation marker;

    private TrajectorySequence trajectorySequence;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new GlacierDrive(hardwareMap);
        april = new April(hardwareMap);

        while (!isStarted()) {
            marker = april.getAprilTag();
            telemetry.addData("April Tag", april.getAprilTag());
            telemetry.update();
        }

        if (april.getAprilTag() == ParkLocation.LEFT) {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeLeft(25).forward(20).build();
        } else if (april.getAprilTag() == ParkLocation.CENTER) {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeRight(10).forward(20).build();
        } else if (april.getAprilTag() == ParkLocation.RIGHT) {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeRight(40).forward(20).build();
        }

        drive.followTrajectorySequence(trajectorySequence);
    }
}
