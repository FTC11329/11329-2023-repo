package org.firstinspires.ftc.teamcode.auto.kennan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.kennan.April;
import org.firstinspires.ftc.teamcode.roadrunner.drive.GlacierDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Scrimmage Auto")
public class GiveProgrammersMoreTime extends LinearOpMode {
    private GlacierDrive drive;
    private April april;
    private int marker;

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

        if (april.getAprilTag() == 0) {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeLeft(55).forward(40).build();
        } else if (april.getAprilTag() == 1) {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeRight(15).forward(40).build();
        } else {
            trajectorySequence = drive.trajectorySequenceBuilder(drive.getPoseEstimate()).forward(5).strafeRight(75).forward(40).build();
        }

        drive.followTrajectorySequence(trajectorySequence);
    }
}
